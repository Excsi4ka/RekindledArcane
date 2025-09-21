package excsi.rekindledarcane.client.gui;

import excsi.rekindledarcane.api.skill.IActiveAbilitySkill;
import excsi.rekindledarcane.client.AssetLib;
import excsi.rekindledarcane.client.gui.widgets.AbilitySlotWidget;
import excsi.rekindledarcane.client.gui.widgets.EquippedSlotWidget;
import excsi.rekindledarcane.client.util.StateRenderHelper;
import excsi.rekindledarcane.common.data.player.PlayerData;
import excsi.rekindledarcane.common.data.player.PlayerDataManager;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;

import java.util.ArrayList;
import java.util.List;

public class SkillSelectionScreen extends GuiScreen {

    public PlayerData playerData;

    public List<AbilitySlotWidget> skillPoolButtons = new ArrayList<>();

    public List<EquippedSlotWidget> equippedSkillsButtons = new ArrayList<>();

    public AbilitySlotWidget draggingWidget = null;

    public int scaleFactor;

    @Override
    public void initGui() {
        playerData = PlayerDataManager.getPlayerData(mc.thePlayer);
        scaleFactor = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight).getScaleFactor();
        setupSlots();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        int y = height / 2 - 83;
        int x = width / 2 - 92;

        mc.getTextureManager().bindTexture(AssetLib.skillSelectorGui);
        drawTexturedModalRect(x,y,0,0,194,166);

        for (int i = playerData.getActiveSlotCount(); i < 5; i++) {
            drawTexturedModalRect(x + 16,y + 15 + 31 * i,0, 166, 20, 20);
        }

        mc.getTextureManager().bindTexture(AssetLib.skillIconTextureAtlas);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();

        StateRenderHelper.scissorStart(width / 2 + 10, height / 2 - 68, 84, 138, scaleFactor);
        skillPoolButtons.forEach(button -> {
            if(button != draggingWidget)
                button.drawButton(mc, mouseX, mouseY);
        });
        StateRenderHelper.scissorEnd();

        equippedSkillsButtons.forEach(button -> button.drawButton(mc, mouseX, mouseY));
        if(draggingWidget != null) {
            draggingWidget.drawButton(mc, mouseX, mouseY);
        }
        tessellator.draw();
    }

    public void setupSlots() {
        equippedSkillsButtons.clear();
        skillPoolButtons.clear();
        int y = height / 2 - 68;
        int x = width / 2 + 10;
        int id = 0, row = 0, column = 0;
        List<IActiveAbilitySkill> skills = playerData.getAllActiveSkills();
        skills.removeAll(playerData.getEquippedActiveSkills());
        for (IActiveAbilitySkill ability : skills) {
            skillPoolButtons.add(new AbilitySlotWidget(id++, x + column++ * 27, y + 28 * row, 24, 24, ability));
            if(column == 3) {
                column = 0;
                row++;
            }
        }
        y = height / 2 - 73;
        x = width / 2 - 82;
        for (int i = 0; i < 5; i++) {
            boolean unlocked = i < playerData.getActiveSlotCount();
            IActiveAbilitySkill ability = playerData.getEquippedActiveSkills().get(i);
            equippedSkillsButtons.add(new EquippedSlotWidget(i, x, y + 31 * i, 22, 22, ability, !unlocked));
        }
    }

    public AbilitySlotWidget getSelectedSkill(int mouseX, int mouseY) {
        for (AbilitySlotWidget button : skillPoolButtons) {
            if(button.isMouseOver(mouseX, mouseY)) {
                return button;
            }
        }
        return null;
    }

    public EquippedSlotWidget getEquipmentSlot(int mouseX, int mouseY) {
        for (EquippedSlotWidget button : equippedSkillsButtons) {
            if(button.isMouseOver(mouseX, mouseY)) {
                return button;
            }
        }
        return null;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        if(button != 0)
            return;
        EquippedSlotWidget widget = getEquipmentSlot(mouseX, mouseY);
        if(widget != null && isShiftKeyDown() && widget.ability != null) {
            widget.onUnEquip();
            return;
        }
        AbilitySlotWidget slot = getSelectedSkill(mouseX, mouseY);
        if(slot != null) {
            draggingWidget = slot;
            draggingWidget.setDragging(true);
        }
    }

    @Override
    protected void mouseMovedOrUp(int mouseX, int mouseY, int button) {
        if (draggingWidget != null && button == 0) {
            EquippedSlotWidget slot = getEquipmentSlot(mouseX, mouseY);
            if(slot != null) {
                slot.onEquip(draggingWidget.ability);
            }
            draggingWidget.setDragging(false);
            draggingWidget = null;
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
