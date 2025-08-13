package excsi.rekindledarcane.client.gui.widgets;

import excsi.rekindledarcane.api.skill.ISkill;
import excsi.rekindledarcane.client.gui.SkillTreeScreen;
import excsi.rekindledarcane.client.util.StateRenderHelper;
import net.minecraft.client.Minecraft;

public class SkillUnlockWidget extends Widget {

    public final ISkill skill;

    public final SkillTreeScreen parentScreen;

    public SkillUnlockWidget(int id, int x, int y, int width, int height, ISkill skill, SkillTreeScreen parentScreen) {
        super(id, x, y, width, height, null);
        this.skill = skill;
        this.parentScreen = parentScreen;
        this.skill.addDescription(descriptionTooltip);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        StateRenderHelper.drawIcon(xPosition + 8, yPosition + 8, 16, 16, 0, skill.getIcon());
        StateRenderHelper.drawIcon(xPosition, yPosition, width, height, 0, skill.getSkillType().frameIcon);
        if(isMouseOver(mouseX, mouseY)) {
            parentScreen.currentHoveringWidget = this;
        }
    }
}
