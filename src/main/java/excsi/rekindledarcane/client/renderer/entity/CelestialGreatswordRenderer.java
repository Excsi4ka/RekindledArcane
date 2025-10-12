package excsi.rekindledarcane.client.renderer.entity;

import excsi.rekindledarcane.common.registry.RekindledArcaneItems;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;

public class CelestialGreatswordRenderer extends Render {

    public static final ItemStack stack = new ItemStack(RekindledArcaneItems.forsakenGreatsword);

    @Override
    public void doRender(Entity entity, double x, double y, double z, float par8, float partialTicks) {
        GL11.glPushMatrix();
        GL11.glTranslated(x - 2, y + 4, z + 2);
        GL11.glRotated(180, 0, 0, 1);
        GL11.glScaled(4, 4, 4);
        IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(stack, IItemRenderer.ItemRenderType.ENTITY);
        ForgeHooksClient.renderEquippedItem(IItemRenderer.ItemRenderType.ENTITY, customRenderer, null, null, stack);
        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return null;
    }
}
