package excsi.rekindledarcane.client.gui.widgets;

import excsi.rekindledarcane.api.skill.ISkillCategory;
import excsi.rekindledarcane.client.AssetLib;
import excsi.rekindledarcane.client.gui.SkillCategorySelectionScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.Tessellator;

public class SkillCategoryWidget extends Widget {

    public ISkillCategory category;

    public double startAngle, stopAngle;

    public SkillCategorySelectionScreen parentScreen;

    public SkillCategoryWidget(int id, int x, int y, int width, int height, ISkillCategory category, double startAngle, double stopAngle, SkillCategorySelectionScreen screen) {
        super(id, x, y, width, height, null);
        this.category = category;
        this.startAngle = Math.toDegrees(startAngle);
        this.stopAngle = Math.toDegrees(stopAngle);
        this.parentScreen = screen;
    }

    @Override
    public void drawButton(Minecraft minecraft, int p_146112_2_, int p_146112_3_) {
        int x = parentScreen.width / 2;
        int y = parentScreen.height / 2;
        int rgb = category.getPrimaryColor().getRGB();
        int alphaHigh = category.getPrimaryColor().getAlpha() + 80;
        int alphaLow = category.getPrimaryColor().getAlpha() - 110;
        int radiusExpand = this == parentScreen.currentlySelected ? 15 : 0;
        Tessellator tes = Tessellator.instance;
        for (double i = startAngle; i <= stopAngle; i++) {
            double radians = Math.toRadians(i);
            tes.setColorRGBA_I(rgb, alphaHigh);
            tes.addVertex(x + Math.sin(radians) * 80, y - Math.cos(radians) * 80, 100);
            tes.setColorRGBA_I(rgb, alphaLow);
            tes.addVertex(x + Math.sin(radians) * (120 + radiusExpand), y - Math.cos(radians) * (120 + radiusExpand), 100);
        }
    }

    public void onSelect() {
        parentScreen.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(AssetLib.menuDing, 1.0F));
    }
}
