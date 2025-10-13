package excsi.rekindledarcane.client.renderer.entity;

import excsi.rekindledarcane.client.AssetLib;
import excsi.rekindledarcane.common.entity.projectile.HookChainProjectile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class HookChainRenderer extends Render {

    @Override
    public void doRender(Entity entity, double x, double y, double z, float par8, float partialTicks) {
        HookChainProjectile hook = (HookChainProjectile) entity;
        Tessellator t = Tessellator.instance;
        renderManager.renderEngine.bindTexture(AssetLib.hookChainTexture);

        EntityPlayer player = hook.getThrower();
        if(player == null)
            return;

        double hX = hook.prevPosX + (hook.posX - hook.prevPosX) * partialTicks - EntityFX.interpPosX;
        double hY = hook.prevPosY + 0.125 + (hook.posY - hook.prevPosY) * partialTicks - EntityFX.interpPosY;
        double hZ = hook.prevPosZ + (hook.posZ - hook.prevPosZ) * partialTicks - EntityFX.interpPosZ;

        double offset = Minecraft.getMinecraft().thePlayer == player ? 0.25 : - 1.5;
        double pX = player.prevPosX + (player.posX - player.prevPosX) * partialTicks - EntityFX.interpPosX;
        double pY = player.prevPosY - offset + (player.posY - player.prevPosY) * partialTicks - EntityFX.interpPosY;
        double pZ = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks - EntityFX.interpPosZ;

        double dx = hX - pX;
        double dy = hY - pY;
        double dz = hZ - pZ;

        double len = Math.sqrt(dx*dx + dy*dy + dz*dz);
        dx /= len;
        dz /= len;

        double sx = dz, sy = 0, sz = -dx;
        double sLen = Math.sqrt(sx*sx + sy*sy + sz*sz);
        sx = sx / sLen * 0.25; //0.25 chain width
        sy = sy / sLen * 0.25;
        sz = sz / sLen * 0.25;

        double tilingFactor = len / 1; //0.5 or 1 looks the most optimal
        GL11.glDisable(GL11.GL_CULL_FACE);
        t.startDrawingQuads();

        //vertical side
        t.addVertexWithUV(pX, pY - 0.25, pZ, 0, tilingFactor);
        t.addVertexWithUV(hX, hY - 0.25, hZ, 0, 0);
        t.addVertexWithUV(hX, hY + 0.25, hZ, 1, 0);
        t.addVertexWithUV(pX, pY + 0.25, pZ, 1, tilingFactor);

        //horizontal side
        t.addVertexWithUV(pX + sx, pY + sy, pZ + sz, 0, tilingFactor);
        t.addVertexWithUV(pX - sx, pY - sy, pZ - sz, 1, tilingFactor);
        t.addVertexWithUV(hX - sx, hY - sy, hZ - sz, 1, 0);
        t.addVertexWithUV(hX + sx, hY + sy, hZ + sz, 0, 0);

        t.draw();
        GL11.glEnable(GL11.GL_CULL_FACE);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return null;
    }
}
