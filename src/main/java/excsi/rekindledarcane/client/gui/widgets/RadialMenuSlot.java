package excsi.rekindledarcane.client.gui.widgets;

import excsi.rekindledarcane.api.skill.IActiveAbilitySkill;
import excsi.rekindledarcane.client.AssetLib;
import excsi.rekindledarcane.client.util.RenderHelperWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;

public class RadialMenuSlot extends Widget {

    public final IActiveAbilitySkill skill;

    public int startAngle, stopAngle;

    public RadialMenuSlot(int id, int x, int y, double startAngle, double stopAngle, IActiveAbilitySkill skill) {
        super(id, x, y, 33, 33);
        this.skill = skill;
        this.startAngle = (int) Math.round(startAngle * 180 / Math.PI);
        this.stopAngle = (int) Math.round(stopAngle * 180 / Math.PI);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        Tessellator tes = Tessellator.instance;
        RenderHelperWrapper.batchDrawIcon(tes, xPosition, yPosition, 30, 30, 0, AssetLib.slot);
        if (skill != null) {
            RenderHelperWrapper.batchDrawIcon(tes, xPosition + 3, yPosition + 3, 24, 24, 0, skill.getIcon());
        }
    }
}
