package excsi.rekindledarcane.client.gui;

import excsi.rekindledarcane.api.skill.ISkill;
import excsi.rekindledarcane.api.skill.ISkillCategory;
import excsi.rekindledarcane.api.skill.Point;
import excsi.rekindledarcane.client.gui.widgets.Widget;
import excsi.rekindledarcane.client.gui.widgets.SkillUnlockWidget;
import excsi.rekindledarcane.client.util.SkillIconTextureManager;
import excsi.rekindledarcane.client.util.StateRenderHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public class SkillTreeScreen extends GuiScreen {

    public static ResourceLocation backgroundTex = new ResourceLocation("rekindledarcane","textures/gui/background.png");

    public GuiScreen parentScreen;

    public ISkillCategory category;

    public Widget currentHoveringWidget;

    public int lastMouseX, lastMouseY;

    public SkillTreeScreen(ISkillCategory category, GuiScreen parentScreen) {
        this.category = category;
        this.parentScreen = parentScreen;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui() {
        int x = width / 2;
        int id = 0;
        for(ISkill skill : category.getAllSkills()) {
            Point point = skill.getPosition();
            buttonList.add(new SkillUnlockWidget(id++, x + point.x, point.y, 32, 32, skill, this));
        }
        lastMouseX = Mouse.getEventX() * this.width / this.mc.displayWidth;
        lastMouseY = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        mc.getTextureManager().bindTexture(backgroundTex);
        StateRenderHelper.drawFullSizeTexturedRectangle(0,0,width,height,zLevel);
        mc.getTextureManager().bindTexture(SkillIconTextureManager.skillIconTextureAtlas);
        currentHoveringWidget = null;
        super.drawScreen(mouseX, mouseY, partialTicks);

        if(currentHoveringWidget != null) {
            func_146283_a(currentHoveringWidget.getDescriptionTooltip(), mouseX, mouseY);
        }

        //moving widgets in drawScreen because otherwise it doesn't update fast enough and looks jagged
        updateMouseMovement(mouseX, mouseY);
        lastMouseX = mouseX;
        lastMouseY = mouseY;
    }

    @SuppressWarnings("unchecked")
    public void updateMouseMovement(int mouseX, int mouseY) {
        if (!Mouse.isButtonDown(0))
            return;
        int offsetX = mouseX - lastMouseX;
        int offsetY = mouseY - lastMouseY;
        if (offsetX == 0 && offsetY == 0)
            return;
        buttonList.forEach(buttonObj -> {
            GuiButton button = (GuiButton) buttonObj;
            button.xPosition += offsetX;
            button.yPosition += offsetY;
        });
    }

    @Override
    protected void keyTyped(char character, int index) {
        if(index == 1) {
            mc.displayGuiScreen(parentScreen);
        }
    }
}
