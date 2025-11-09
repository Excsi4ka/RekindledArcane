package excsi.rekindledarcane.client.gui.widgets;

import excsi.rekindledarcane.api.skill.ISkillCategory;
import excsi.rekindledarcane.client.AssetLib;
import excsi.rekindledarcane.client.gui.SkillCategorySelectionScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.Tessellator;

public class SkillCategoryWidget extends Widget {

    public ISkillCategory category;

    public int startAngle, stopAngle;

    public SkillCategorySelectionScreen parentScreen;

    public SkillCategoryWidget(int id, int x, int y, double startAngle, double stopAngle, ISkillCategory category, SkillCategorySelectionScreen screen) {
        super(id, x, y, 40, 40);
        this.category = category;
        this.startAngle = (int) Math.round(startAngle * 180 / Math.PI);
        this.stopAngle = (int) Math.round(stopAngle * 180 / Math.PI);
        this.parentScreen = screen;
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
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

//    public void drawWedge() {
//        int x = parentScreen.width / 2;
//        int y = parentScreen.height / 2;
//        int angleDiff = 2;
//        Tessellator tes = Tessellator.instance;
//        tes.setColorRGBA(207, 164, 108, 244);
//        double radians = Math.toRadians(startAngle + angleDiff);
//        tes.addVertex(x + Math.sin(radians) * 80, y - Math.cos(radians) * 80, 100);
//        radians = Math.toRadians(startAngle);
//        tes.setColorRGBA(207, 164, 108, 20);
//        tes.addVertex(x + Math.sin(radians) * 160, y - Math.cos(radians) * 160, 100);
//        radians = Math.toRadians(startAngle - angleDiff);
//                tes.setColorRGBA(207, 164, 108, 244);
//        tes.addVertex(x + Math.sin(radians) * 80, y - Math.cos(radians) * 80, 100);
//    }

    public void onSelect() {
        parentScreen.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(AssetLib.menuDing, 1.0F));
    }
}
