package excsi.rekindledarcane.client.gui;

import excsi.rekindledarcane.api.RekindledArcaneAPI;
import excsi.rekindledarcane.api.skill.ISkillCategory;
import excsi.rekindledarcane.client.AssetLib;
import excsi.rekindledarcane.client.gui.widgets.SkillCategoryWidget;
import excsi.rekindledarcane.client.util.BlendMode;
import excsi.rekindledarcane.client.util.StateRenderHelper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class SkillCategorySelectionScreen extends GuiScreen {

    public int lastMouseX, lastMouseY;

    public List<SkillCategoryWidget> radialMenuComponents = new ArrayList<>();

    public SkillCategoryWidget currentlySelected;

    @Override
    public void initGui() {
        radialMenuComponents.clear();
        int x = width / 2;
        int y = height / 2;
        int radius = 80;
        int categoryAmount = RekindledArcaneAPI.getAllCategories().size();
        int index = 0;
        for(ISkillCategory category : RekindledArcaneAPI.getAllCategories()) {
            double theta = Math.PI / categoryAmount;
            double centerTheta = theta * 2 * index;
            int pointX = (int) (x + radius * Math.sin(centerTheta));
            int pointY = (int) (y - radius * Math.cos(centerTheta));
            radialMenuComponents.add(new SkillCategoryWidget(index++, pointX, pointY, 40, 40, category,
                    centerTheta - theta, centerTheta + theta, this));
        }
        lastMouseX = x;
        lastMouseY = y;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        mc.getTextureManager().bindTexture(AssetLib.backgroundTex);
        StateRenderHelper.enableBlend();
        StateRenderHelper.blendMode(BlendMode.DEFAULT);
        StateRenderHelper.drawFullSizeTexturedRectangle(0, 0, width, height, zLevel);
        StateRenderHelper.disableTexture2D();
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Tessellator tes = Tessellator.instance;
        tes.startDrawing(GL11.GL_TRIANGLE_STRIP);
        radialMenuComponents.forEach(component -> component.drawButton(mc, mouseX, mouseY));
        tes.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glEnable(GL11.GL_CULL_FACE);
        StateRenderHelper.restoreStates();

        updateMouseMovement(mouseX, mouseY);

        if(currentlySelected != null) {
            String name = currentlySelected.category.getNameID();
            int offset = mc.fontRenderer.getStringWidth(name) / 2;
            mc.fontRenderer.drawStringWithShadow(currentlySelected.category.getNameID(), width / 2 - offset, height / 2 - 3, 0xFFFFFF);
        }
        lastMouseX = mouseX;
        lastMouseY = mouseY;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int clickType) {
        if(clickType == 0 && currentlySelected != null)
            mc.displayGuiScreen(new SkillTreeScreen(currentlySelected.category, this));
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public SkillCategoryWidget getRadialMenuWedge(double angle) {
        for (SkillCategoryWidget component : radialMenuComponents) {
            if(angle >= component.startAngle && angle <= component.stopAngle)
                return component;
        }
        return radialMenuComponents.get(0);
    }

    public void updateMouseMovement(int mouseX, int mouseY) {
        if(mouseX == lastMouseX && mouseY == lastMouseY)
            return;
        int dX = mouseX - width / 2;
        int dY = height / 2 - mouseY;
        double angle = Math.toDegrees(Math.atan2(dX, dY));
        if(angle < 0) angle+=360;
        SkillCategoryWidget widget = getRadialMenuWedge(angle);
        if(!widget.equals(currentlySelected)) {
            widget.onSelect();
            currentlySelected = widget;
        }
    }
}
