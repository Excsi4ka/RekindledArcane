package excsi.rekindledarcane.client.gui;

import excsi.rekindledarcane.api.skill.IActiveAbilitySkill;
import excsi.rekindledarcane.client.AssetLib;
import excsi.rekindledarcane.client.gui.widgets.AbilitySlotWidget;
import excsi.rekindledarcane.client.gui.widgets.EquippedSlotWidget;
import excsi.rekindledarcane.common.data.player.PlayerData;
import excsi.rekindledarcane.common.data.player.PlayerDataManager;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

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
        drawDefaultBackground();
        int y = height / 2 - 56;
        int x = width / 2 - 135;

        mc.getTextureManager().bindTexture(AssetLib.skillSelectorGui);
        drawTexturedModalRect(x,y,0,0,210,112);

//        for (int i = playerData.getActiveSlotCount(); i < 5; i++) {
//            drawTexturedModalRect(x + 16,y + 15 + 31 * i,0, 166, 20, 20);
//        }
        //drawTexturedModalRect(x + 142, y + 30,86,172,170,84);
        func_147046_a(x + 65, y + 105, 30, x - mouseX + 65, y - mouseY + 45, mc.thePlayer);

//        mc.getTextureManager().bindTexture(AssetLib.skillIconTextureAtlas);
//        Tessellator tessellator = Tessellator.instance;
//        tessellator.startDrawingQuads();
//
//        RenderHelperWrapper.scissorStart(width / 2 + 10, height / 2 - 68, 84, 138, scaleFactor);
//        skillPoolButtons.forEach(button -> {
//            if(button != draggingWidget)
//                button.drawButton(mc, mouseX, mouseY);
//        });
//        RenderHelperWrapper.scissorEnd();
//
//        equippedSkillsButtons.forEach(button -> button.drawButton(mc, mouseX, mouseY));
//        if(draggingWidget != null) {
//            draggingWidget.drawButton(mc, mouseX, mouseY);
//        }
//        tessellator.draw();
    }

    public static void func_147046_a(int p_147046_0_, int p_147046_1_, int p_147046_2_, float p_147046_3_, float p_147046_4_, EntityLivingBase p_147046_5_)
    {
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)p_147046_0_, (float)p_147046_1_, 50.0F);
        GL11.glScalef((float)(-p_147046_2_), (float)p_147046_2_, (float)p_147046_2_);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        float f2 = p_147046_5_.renderYawOffset;
        float f3 = p_147046_5_.rotationYaw;
        float f4 = p_147046_5_.rotationPitch;
        float f5 = p_147046_5_.prevRotationYawHead;
        float f6 = p_147046_5_.rotationYawHead;
        GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-((float)Math.atan((double)(p_147046_4_ / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        p_147046_5_.renderYawOffset = (float)Math.atan((double)(p_147046_3_ / 40.0F)) * 20.0F;
        p_147046_5_.rotationYaw = (float)Math.atan((double)(p_147046_3_ / 40.0F)) * 40.0F;
        p_147046_5_.rotationPitch = -((float)Math.atan((double)(p_147046_4_ / 40.0F))) * 20.0F;
        p_147046_5_.rotationYawHead = p_147046_5_.rotationYaw;
        p_147046_5_.prevRotationYawHead = p_147046_5_.rotationYaw;
        GL11.glTranslatef(0.0F, p_147046_5_.yOffset, 0.0F);
        RenderManager.instance.playerViewY = 180.0F;
        RenderManager.instance.renderEntityWithPosYaw(p_147046_5_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        p_147046_5_.renderYawOffset = f2;
        p_147046_5_.rotationYaw = f3;
        p_147046_5_.rotationPitch = f4;
        p_147046_5_.prevRotationYawHead = f5;
        p_147046_5_.rotationYawHead = f6;
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
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

    public enum SlotPosition {

        ONE(0,0),
        TWO(0,0),
        THREE(0,0),
        FOUR(0,0),
        FIVE(0,0);

        public final int x;

        public final int y;

        SlotPosition(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
