package excsi.rekindledarcane.client.renderer.item;

import excsi.rekindledarcane.client.AssetLib;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class GreatswordRenderer implements IItemRenderer {

    @Override
    public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
        return helper != IItemRenderer.ItemRendererHelper.BLOCK_3D;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        Minecraft mc = Minecraft.getMinecraft();
        GL11.glPushMatrix();
        switch (type) {
            default: break;
            case INVENTORY: {
                GL11.glTranslatef(0f, -0.8f, 0f);
                GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
                //GL11.glRotatef(45F, 0.0F, 0.0F, -1.0F);
                GL11.glScalef(1.5f,1.5f,1.5f);
                break;
            }
            case EQUIPPED_FIRST_PERSON: {
                GL11.glTranslatef(1f, -0.65f, 0.5f);
                GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(3f,3f,3f);
                break;
            }
            case EQUIPPED: {
                GL11.glTranslatef(0.5f, 0.0f, 0.5f);
                GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(15F, 0F, 0F, 1F);
                GL11.glScalef(2.5f, 2.5f, 2.5f);
                break;
            }
        }
        mc.renderEngine.bindTexture(AssetLib.greatswordTexture);
//        AssetLib.greatswordModel.renderAllExcept("blade");
//        if(data.length >= 2 && data[1] instanceof EntityPlayer) {
//            EntityPlayer player = (EntityPlayer) data[1];
//            if(player.isUsingItem())
//                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,240f,240f);
//        }
//        AssetLib.greatswordModel.renderPart("blade");
        AssetLib.greatswordModel.renderAll();
        GL11.glPopMatrix();
    }
}
