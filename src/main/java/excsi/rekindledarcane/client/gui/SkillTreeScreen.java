package excsi.rekindledarcane.client.gui;

import excsi.rekindledarcane.api.skill.ISkill;
import excsi.rekindledarcane.api.skill.ISkillCategory;
import excsi.rekindledarcane.api.skill.Point;
import excsi.rekindledarcane.client.AssetLib;
import excsi.rekindledarcane.client.gui.widgets.SkillUnlockWidget;
import excsi.rekindledarcane.client.util.BlendMode;
import excsi.rekindledarcane.client.util.RenderHelperWrapper;
import excsi.rekindledarcane.common.data.player.PlayerData;
import excsi.rekindledarcane.common.data.player.PlayerDataManager;
import excsi.rekindledarcane.common.network.PacketManager;
import excsi.rekindledarcane.common.network.client.ClientPacketUnlockSkill;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SkillTreeScreen extends GuiScreen {

    public GuiScreen parentScreen;

    public ISkillCategory category;

    public SkillUnlockWidget currentHoveringWidget;

    public Map<ISkill, SkillUnlockWidget> skillWidgets;

    public PlayerData playerData;

    public int lastMouseX, lastMouseY;

    public SkillTreeScreen(ISkillCategory category, GuiScreen parentScreen) {
        this.category = category;
        this.parentScreen = parentScreen;
        this.skillWidgets = new HashMap<>();
    }

    @Override
    public void initGui() {
        skillWidgets.clear();
        int x = width / 2;
        int id = 0;
        playerData = PlayerDataManager.getPlayerData(mc.thePlayer);
        Set<ISkill> unlockedSkills = playerData.getUnlockedSkillForCategory(category);
        for (ISkill skill : category.getAllSkills()) {
            boolean isUnlocked = unlockedSkills.contains(skill);
            Point point = skill.getPosition();
            skillWidgets.put(skill, new SkillUnlockWidget(id++, x + point.x, point.y, 22, 22,
                    skill, this, isUnlocked));
        }
        lastMouseX = Mouse.getEventX() * width / mc.displayWidth;
        lastMouseY = height - Mouse.getEventY() * height / mc.displayHeight - 1;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Tessellator tes = Tessellator.instance;
        mc.getTextureManager().bindTexture(AssetLib.backgroundTex);
        RenderHelperWrapper.drawFullSizeTexturedRectangle(0,0,width,height,zLevel);
        mc.getTextureManager().bindTexture(AssetLib.skillIconTextureAtlas);
        currentHoveringWidget = null;

        tes.startDrawingQuads();
        skillWidgets.values().forEach(widget -> widget.drawButton(mc, mouseX, mouseY));
        tes.draw();

        GL11.glLineWidth(6f);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        RenderHelperWrapper.enableBlend();
        RenderHelperWrapper.blendMode(BlendMode.DEFAULT);
        RenderHelperWrapper.disableTexture2D();
        tes.startDrawing(GL11.GL_LINES);
        int alpha = (int) (Math.sin(mc.thePlayer.ticksExisted * 0.1) * 70 + 170);
        skillWidgets.values().forEach(widget -> widget.drawConnectionLine(tes, alpha));
        tes.draw();
        RenderHelperWrapper.restoreStates();
        GL11.glLineWidth(1f);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);

        mc.fontRenderer.drawStringWithShadow("SkillPoints: " + playerData.getSkillPoints(),
                2, 3, 0xFFFFFF);

        //render tooltips last because they mess with colors
        if(currentHoveringWidget != null) {
            func_146283_a(currentHoveringWidget.getDescriptionTooltip(), mouseX, mouseY);
        }

        //moving widgets in drawScreen because otherwise it doesn't update fast enough and looks jagged
        updateMouseMovement(mouseX, mouseY);
        lastMouseX = mouseX;
        lastMouseY = mouseY;
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

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        if(button != 0)
            return;
        if(currentHoveringWidget == null)
            return;
        ISkill skill = currentHoveringWidget.skill;
        currentHoveringWidget.func_146113_a(mc.getSoundHandler());
        PacketManager.sendToServer(new ClientPacketUnlockSkill(skill.getSkillCategory(), skill));
    }

    @Override
    protected void keyTyped(char character, int index) {
        if(index == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(parentScreen);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
