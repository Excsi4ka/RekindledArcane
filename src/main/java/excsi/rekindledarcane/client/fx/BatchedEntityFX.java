package excsi.rekindledarcane.client.fx;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

import java.awt.*;

//todo get rid of entityFX entirely and make a standalone basic class for particles
public class BatchedEntityFX extends EntityFX {

    public float resizeRate = 1f;

    public BatchedEntityFX(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public BatchedEntityFX(World world, double x, double y, double z, double dx, double dy, double dz) {
        super(world, x, y, z, dx, dy, dz);
    }

    public BatchedEntityFX setScale(float scale) {
        particleScale = scale;
        return this;
    }

    public BatchedEntityFX setMotion(double dx, double dy, double dz) {
        setVelocity(dx, dy, dz);
        return this;
    }

    public BatchedEntityFX setColor(int r, int g, int b) {
        setRBGColorF(r / 255f, g / 255f, b / 255f);
        return this;
    }

    public BatchedEntityFX setColor(Color color) {
        setRBGColorF(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
        return this;
    }

    public BatchedEntityFX setResizeRate(float rate) {
        resizeRate = rate;
        return this;
    }

    public BatchedEntityFX setMaxAge(int maxAge) {
        particleMaxAge = maxAge;
        return this;
    }

    @Override
    public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
        float var12 = 0.1F * this.particleScale;
        float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)f - interpPosX);
        float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)f - interpPosY);
        float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)f - interpPosZ);
        tessellator.setColorRGBA_F(particleRed, particleGreen, particleBlue, 0.125f);
        tessellator.addVertexWithUV((var13 - f1 * var12 - f4 * var12), (var14 - f2 * var12), (var15 - f3 * var12 - f5 * var12),1,1);
        tessellator.addVertexWithUV((var13 - f1 * var12 + f4 * var12), (var14 + f2 * var12), (var15 - f3 * var12 + f5 * var12),1,0);
        tessellator.addVertexWithUV((var13 + f1 * var12 + f4 * var12), (var14 + f2 * var12), (var15 + f3 * var12 + f5 * var12),0,0);
        tessellator.addVertexWithUV((var13 + f1 * var12 - f4 * var12), (var14 - f2 * var12), (var15 + f3 * var12 - f5 * var12),0,1);
    }

    @Override
    public void onUpdate() {
        this.particleScale *= resizeRate;
        this.prevPosX = posX;
        this.prevPosY = posY;
        this.prevPosZ = posZ;
        if (particleAge++ >= particleMaxAge) {
            setDead();
        }
        moveEntity(this.motionX, this.motionY, this.motionZ);
    }
}
