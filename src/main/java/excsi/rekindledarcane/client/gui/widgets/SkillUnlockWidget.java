package excsi.rekindledarcane.client.gui.widgets;

import excsi.rekindledarcane.api.skill.ISkill;
import excsi.rekindledarcane.api.skill.Point;
import excsi.rekindledarcane.client.gui.SkillTreeScreen;
import excsi.rekindledarcane.client.util.StateRenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

public class SkillUnlockWidget extends Widget {

    public final ISkill skill;

    public final SkillTreeScreen parentScreen;

    public boolean unlocked;

    public int connectionX, connectionY;

    public SkillUnlockWidget(int id, int x, int y, int width, int height, ISkill skill, SkillTreeScreen parentScreen, boolean unlocked) {
        super(id, x, y, width, height, null);
        this.skill = skill;
        this.parentScreen = parentScreen;
        this.skill.addDescription(descriptionTooltip);
        this.unlocked = unlocked;
        if(skill.getPreRequisite() != null) {
            Point connectionPoint = skill.getPreRequisite().getPosition();
            connectionX = parentScreen.width / 2 + connectionPoint.x + 11;
            connectionY = connectionPoint.y + 11;
        }
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        Tessellator tessellator = Tessellator.instance;
        if(!unlocked)
            tessellator.setColorOpaque_F(0.3f,0.3f,0.3f);
        else
            tessellator.setColorOpaque_F(1f,1f,1f);
        StateRenderHelper.batchDrawIcon(tessellator, xPosition + 3, yPosition + 3, 16, 16, 10, skill.getIcon());
        StateRenderHelper.batchDrawIcon(tessellator, xPosition, yPosition, width, height, 10, skill.getSkillType().frameIcon);
        if(isMouseOver(mouseX, mouseY)) {
            parentScreen.currentHoveringWidget = this;
        }
    }

    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
    }

    public void handleOffset(int offsetX, int offsetY) {
        xPosition += offsetX;
        connectionX += offsetX;
        yPosition += offsetY;
        connectionY += offsetY;
    }

    public void drawConnectionLine(Tessellator tes, int alpha) {
        if(skill.getPreRequisite() == null)
            return;
        if(unlocked)
            tes.setColorRGBA(0, 170, 0, alpha);
        else
            tes.setColorRGBA(194, 197, 204, 100);
        tes.addVertex(xPosition + 11, yPosition + 11, 0);
        tes.addVertex(connectionX, connectionY, 0);
    }
}
