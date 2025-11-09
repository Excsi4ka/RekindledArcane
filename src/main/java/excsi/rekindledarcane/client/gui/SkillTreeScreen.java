package excsi.rekindledarcane.client.gui;

import excsi.rekindledarcane.api.skill.IActiveAbilitySkill;
import excsi.rekindledarcane.api.skill.ISkill;
import excsi.rekindledarcane.api.skill.ISkillCategory;
import excsi.rekindledarcane.api.misc.Point;
import excsi.rekindledarcane.api.skill.IToggleSwitch;
import excsi.rekindledarcane.client.AssetLib;
import excsi.rekindledarcane.client.gui.widgets.QuickEquipWidget;
import excsi.rekindledarcane.client.gui.widgets.SkillUnlockWidget;
import excsi.rekindledarcane.client.util.BlendMode;
import excsi.rekindledarcane.client.util.RenderHelperWrapper;
import excsi.rekindledarcane.common.data.player.PlayerData;
import excsi.rekindledarcane.common.data.player.PlayerDataManager;
import excsi.rekindledarcane.common.network.PacketManager;
import excsi.rekindledarcane.common.network.client.ClientPacketSkillToggle;
import excsi.rekindledarcane.common.network.client.ClientPacketUnlockSkill;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SkillTreeScreen extends GuiScreen {

    private final GuiScreen parentScreen;

    private final ISkillCategory category;

    private final List<QuickEquipWidget> equipSlots;

    private SkillUnlockWidget currentHoveringWidget, equipmentCandidate;

    public final Map<ISkill, SkillUnlockWidget> skillWidgets;

    private PlayerData playerData;

    private QuickEquipWidget hoveringSlot;

    public int lastMouseX, lastMouseY;

    public SkillTreeScreen(ISkillCategory category, GuiScreen parentScreen) {
        this.category = category;
        this.parentScreen = parentScreen;
        this.skillWidgets = new HashMap<>();
        this.equipSlots = new ArrayList<>();
    }

    @Override
    public void initGui() {
        skillWidgets.clear();
        playerData = PlayerDataManager.getPlayerData(mc.thePlayer);
        Set<ISkill> unlockedSkills = playerData.getUnlockedSkillForCategory(category);
        int x = width / 2;
        for (ISkill skill : category.getAllSkills()) {
            boolean isUnlocked = unlockedSkills.contains(skill);
            Point point = skill.getPosition();
            skillWidgets.put(skill, new SkillUnlockWidget(x + point.getX(), point.getY(), skill, this, isUnlocked));
        }
        closeEquipmentMenu();
        lastMouseX = Mouse.getEventX() * width / mc.displayWidth;
        lastMouseY = height - Mouse.getEventY() * height / mc.displayHeight - 1;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Tessellator tes = Tessellator.instance;
        mc.getTextureManager().bindTexture(AssetLib.backgroundTex);
        RenderHelperWrapper.drawFullSizeTexturedRectangle(0, 0, width, height, zLevel);
        mc.getTextureManager().bindTexture(AssetLib.skillIconTextureAtlas);
        currentHoveringWidget = null;
        hoveringSlot = null;

        GL11.glLineWidth(6f);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        RenderHelperWrapper.enableBlend();
        RenderHelperWrapper.blendMode(BlendMode.DEFAULT);
        RenderHelperWrapper.disableTexture2D();
        tes.startDrawing(GL11.GL_LINES);
        //int alpha = (int) (Math.sin(mc.thePlayer.ticksExisted * 0.1) * 70 + 170);
        skillWidgets.values().forEach(widget -> widget.drawConnectionLine(tes, 150));
        tes.draw();
        RenderHelperWrapper.restoreStates();
        GL11.glLineWidth(1f);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);

        tes.startDrawingQuads();
        skillWidgets.values().forEach(widget -> widget.drawButton(mc, mouseX, mouseY));
        tes.draw();

        mc.fontRenderer.drawStringWithShadow("SkillPoints: " + playerData.getSkillPoints(),
                2, 3, 0xFFFFFF);

        if (equipmentMenuToggled()) {
            drawEquipmentMenu(mouseX, mouseY);
            return;
        }

        //render tooltips last because they mess with colors
        if (currentHoveringWidget != null) {
            func_146283_a(currentHoveringWidget.getDescriptionTooltip(), mouseX, mouseY);
        }

        //moving widgets in drawScreen because otherwise it doesn't update fast enough and looks jagged
        updateMouseMovement(mouseX, mouseY);
        lastMouseX = mouseX;
        lastMouseY = mouseY;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        if (currentHoveringWidget != null) {
            if (button == 0 && !currentHoveringWidget.isUnlocked()) {
                ISkill skill = currentHoveringWidget.getSkill();
                currentHoveringWidget.func_146113_a(mc.getSoundHandler());
                PacketManager.sendToServer(new ClientPacketUnlockSkill(skill.getSkillCategory(), skill));
            }
            if (button == 0 && currentHoveringWidget.isUnlocked() && isShiftKeyDown() && currentHoveringWidget.getSkill() instanceof IToggleSwitch) {
                ISkill skill = currentHoveringWidget.getSkill();
                PacketManager.sendToServer(new ClientPacketSkillToggle(skill.getSkillCategory(), skill));
            }
            if (button == 1 && currentHoveringWidget.getSkill() instanceof IActiveAbilitySkill && currentHoveringWidget.isUnlocked()) {
                closeEquipmentMenu();
                createEquipSlots(currentHoveringWidget);
            }
        }
        if (equipmentMenuToggled() && hoveringSlot != null && button == 0) {
            hoveringSlot.func_146113_a(mc.getSoundHandler());
            hoveringSlot.onEquip((IActiveAbilitySkill) equipmentCandidate.getSkill());
            lastMouseX = mouseX;
            lastMouseY = mouseY;
            closeEquipmentMenu();
        }
    }

    @Override
    protected void keyTyped(char character, int index) {
        if (index != Keyboard.KEY_ESCAPE)
            return;
        if (equipmentMenuToggled()) {
            closeEquipmentMenu();
        } else {
            mc.displayGuiScreen(parentScreen);
        }
    }

    public void createEquipSlots(SkillUnlockWidget widget) {
        equipmentCandidate = widget;
        for (int i = 0; i < 8; i++) {
            boolean unlocked = i < playerData.getActiveSlotCount();
            double theta = Math.PI / 8;
            double centerTheta = theta * 2 * i;
            int radius = 45;
            int pointX = (int) (widget.xPosition + radius * Math.sin(centerTheta));
            int pointY = (int) (widget.yPosition - radius * Math.cos(centerTheta));
            IActiveAbilitySkill ability = playerData.getEquippedActiveSkills().get(i);
            equipSlots.add(new QuickEquipWidget(i, pointX, pointY, ability, !unlocked, this));
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public boolean equipmentMenuToggled() {
        return equipmentCandidate != null;
    }

    public void closeEquipmentMenu() {
        equipmentCandidate = null;
        equipSlots.clear();
    }

    public void setHoveringSlot(QuickEquipWidget hoveringSlot) {
        this.hoveringSlot = hoveringSlot;
    }

    public void setCurrentHoveringWidget(SkillUnlockWidget currentHoveringWidget) {
        this.currentHoveringWidget = currentHoveringWidget;
    }

    public void drawEquipmentMenu(int mouseX, int mouseY) {
        drawDefaultBackground();
        mc.getTextureManager().bindTexture(AssetLib.skillIconTextureAtlas);

        Tessellator tes = Tessellator.instance;
        tes.startDrawingQuads();
        tes.setColorOpaque_F(1f, 1f, 1f);
        equipmentCandidate.drawButton(mc, mouseX, mouseY);
        equipSlots.forEach(widget -> widget.drawButton(mc, mouseX, mouseY));
        tes.draw();

        if (equipmentCandidate.isMouseOver(mouseX, mouseY)) {
            func_146283_a(equipmentCandidate.getDescriptionTooltip(), mouseX, mouseY);
        }
        if (hoveringSlot != null) {
            func_146283_a(hoveringSlot.getDescriptionTooltip(), mouseX, mouseY);
        }
    }

    public void updateMouseMovement(int mouseX, int mouseY) {
        if (!Mouse.isButtonDown(0))
            return;
        int offsetX = mouseX - lastMouseX;
        int offsetY = mouseY - lastMouseY;
        if (offsetX == 0 && offsetY == 0)
            return;
        skillWidgets.values().forEach(button -> button.handleOffset(offsetX, offsetY));
    }
}
