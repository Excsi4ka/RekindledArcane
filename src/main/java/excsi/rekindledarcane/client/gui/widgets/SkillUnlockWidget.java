package excsi.rekindledarcane.client.gui.widgets;

import excsi.rekindledarcane.api.skill.ISkill;
import excsi.rekindledarcane.api.misc.Point;
import excsi.rekindledarcane.api.skill.IToggleSwitch;
import excsi.rekindledarcane.client.gui.SkillTreeScreen;
import excsi.rekindledarcane.client.util.RenderHelperWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;

public class SkillUnlockWidget extends Widget {

    private final ISkill skill;

    public final SkillTreeScreen parentScreen;

    private boolean unlocked;

    public int connectionX, connectionY;

    public SkillUnlockWidget(int x, int y, ISkill skill, SkillTreeScreen parentScreen, boolean unlocked) {
        super(0, x, y, 22, 22);
        this.skill = skill;
        this.parentScreen = parentScreen;
        this.skill.addDescription(descriptionTooltip); //maybe shouldn't store descriptions for all skills upon screen init;
        this.unlocked = unlocked;
        if(skill.getPreRequisite() != null) {
            Point connectionPoint = skill.getPreRequisite().getPosition();
            connectionX = parentScreen.width / 2 + connectionPoint.getX() + 11;
            connectionY = connectionPoint.getY() + 11;
        }
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        Tessellator tessellator = Tessellator.instance;
        if(!unlocked)
            tessellator.setColorOpaque_F(0.3f,0.3f,0.3f);
        else
            tessellator.setColorOpaque_F(1f,1f,1f);
        RenderHelperWrapper.batchDrawIcon(tessellator, xPosition + 3, yPosition + 3, 16, 16, 0, skill.getIcon());
        RenderHelperWrapper.batchDrawIcon(tessellator, xPosition, yPosition, width, height, 0, skill.getSkillType().getFrameIcon());
        if(isMouseOver(mouseX, mouseY) && !parentScreen.equipmentMenuToggled()) {
            parentScreen.setCurrentHoveringWidget(this);
        }
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public ISkill getSkill() {
        return skill;
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
