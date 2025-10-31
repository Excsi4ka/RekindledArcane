package excsi.rekindledarcane.client.fx;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class BeamFX extends BatchedEntityFX {

    public BeamFX(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    public void renderParticle(Tessellator tessellator, float partialTicks, float f1, float f2, float f3, float f4, float f5) {
        float scale = 0.025F * this.particleScale;
        float x = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double) partialTicks - interpPosX);
        float y = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double) partialTicks - interpPosY);
        float z = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTicks - interpPosZ);
        tessellator.setColorRGBA_F(particleRed, particleGreen, particleBlue, (float) (0.7f + Math.sin(particleAge / 10d) / 3f));
        tessellator.addVertexWithUV(x + scale, y, z, 1, 1);
        tessellator.addVertexWithUV(x + scale, y + scale * 15, z, 1, 0);
        tessellator.addVertexWithUV(x - scale, y + scale * 15, z , 0, 0);
        tessellator.addVertexWithUV(x - scale, y, z, 0, 1);

        tessellator.addVertexWithUV(x, y, z + scale, 1, 1);
        tessellator.addVertexWithUV(x, y + scale * 15, z + scale, 1, 0);
        tessellator.addVertexWithUV(x, y + scale * 15, z - scale, 0, 0);
        tessellator.addVertexWithUV(x, y, z - scale, 0, 1);

        tessellator.addVertexWithUV(x + scale, y, z + scale, 1, 1);
        tessellator.addVertexWithUV(x + scale, y + scale * 15, z + scale, 1, 0);
        tessellator.addVertexWithUV(x - scale, y + scale * 15, z - scale, 0, 0);
        tessellator.addVertexWithUV(x - scale, y, z - scale, 0, 1);

        tessellator.addVertexWithUV(x + scale, y, z - scale, 1, 1);
        tessellator.addVertexWithUV(x + scale, y + scale * 15, z - scale, 1, 0);
        tessellator.addVertexWithUV(x - scale, y + scale * 15, z + scale, 0, 0);
        tessellator.addVertexWithUV(x - scale, y, z + scale, 0, 1);
    }
}
