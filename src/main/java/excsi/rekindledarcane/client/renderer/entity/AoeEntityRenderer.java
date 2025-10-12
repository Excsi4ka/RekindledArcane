package excsi.rekindledarcane.client.renderer.entity;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class AoeEntityRenderer extends Render {

    @Override
    public void doRender(Entity entity, double x, double y, double z, float par8, float partialTicks) {
//        AoeEntity aoeEntity = (AoeEntity) entity;
//        Tessellator tessellator = Tessellator.instance;
//        GL11.glPushMatrix();
//        GL11.glTranslated(x, y, z);
//        GL11.glShadeModel(GL11.GL_SMOOTH);
//        GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
//        GL11.glDepthMask(false);
//        GL11.glColor4f(1f,1f,1f,1f);
//        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,240f,240f);
//        StateRenderHelper.enableBlend();
//        StateRenderHelper.blendMode(BlendMode.DEFAULT);
//        StateRenderHelper.disableTexture2D();
//
//        double variance = Math.sin(entity.ticksExisted / 8d) * 0.15;
//        int r = (aoeEntity.rgba >> 16) & 0xFF;
//        int g = (aoeEntity.rgba >> 8) & 0xFF;
//        int b = (aoeEntity.rgba) & 0xFF;
//        int a = (aoeEntity.rgba >> 24) & 0xFF;
//        int radius = 4;
//        tessellator.startDrawing(GL11.GL_QUAD_STRIP);
////        tessellator.addVertex(1,0,0);
////        tessellator.addVertex(1,1,0);
////        tessellator.addVertex(0,0,0);
////        tessellator.addVertex(0,1,0);
////        tessellator.addVertex(-1,0,0);
////        tessellator.addVertex(-1,1,0);
//        for (int i = 0; i <= 360; i++) {
//            float rad = (i - 180) / 180F * (float) Math.PI;
//            tessellator.setColorRGBA(r,g,b,a);
//            tessellator.addVertex(MathHelper.sin(rad) * radius, 0, MathHelper.cos(rad) * radius);
//            tessellator.setColorRGBA(r,g,b,a-190);
//            tessellator.addVertex(MathHelper.sin(rad) * radius, 0.8 + variance, MathHelper.cos(rad) * radius);
//        }
//        for (int i = 0; i <= 360; i++) {
//            float rad = (i - 90) / 180F * (float) Math.PI;
//            tessellator.setColorRGBA(r,g,b,a);
//            tessellator.addVertex(MathHelper.cos(rad) * radius, 0, MathHelper.sin(rad) * radius);
//            tessellator.setColorRGBA(r,g,b,a-190);
//            tessellator.addVertex(MathHelper.cos(rad) * radius, 0.8 + variance, MathHelper.sin(rad) * radius);
//        }
//        tessellator.draw();
//        StateRenderHelper.restoreStates();
//        GL11.glShadeModel(GL11.GL_FLAT);
//        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
//        GL11.glDepthMask(true);
//        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return AbstractClientPlayer.locationStevePng;
    }
}
