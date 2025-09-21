package excsi.rekindledarcane.client.gui.widgets;

import excsi.rekindledarcane.api.skill.IActiveAbilitySkill;
import excsi.rekindledarcane.client.util.StateRenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;

public class AbilitySlotWidget extends Widget {

    public IActiveAbilitySkill ability;

    private boolean dragging;

    public AbilitySlotWidget(int id, int x, int y, int width, int height, IActiveAbilitySkill ability) {
        super(id, x, y, width, height);
        this.ability = ability;
        this.dragging = false;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if(dragging) {
            StateRenderHelper.batchDrawIcon(Tessellator.instance, mouseX - 12, mouseY - 12, width, height, 0, ability.getIcon());
            return;
        }
        StateRenderHelper.batchDrawIcon(Tessellator.instance, xPosition, yPosition, width, height, 0, ability.getIcon());
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }
}
