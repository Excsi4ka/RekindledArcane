package excsi.rekindledarcane.client.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import excsi.rekindledarcane.client.AssetLib;
import excsi.rekindledarcane.client.ClientProxy;
import excsi.rekindledarcane.client.gui.SkillCategorySelectionScreen;
import excsi.rekindledarcane.common.registry.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraftforge.client.event.TextureStitchEvent;

public class ClientEventHandler {

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        if (ClientProxy.activateAbilityKey.isPressed()) {
            //PacketManager.sendToServer(new ClientPacketKeyPress(0));
            Minecraft.getMinecraft().displayGuiScreen(new SkillCategorySelectionScreen());
        }
    }

    public static void renderCallback(EntityLivingBase entity, ModelBiped modelBiped) {
        if(!(entity instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) entity;
        if(player.isUsingItem()) {
            EnumAction action = player.getHeldItem().getItemUseAction();
            if(action == ItemRegistry.raiseSword) {
//                modelBiped.bipedRightArm.rotateAngleZ = -0.5f;
//                modelBiped.bipedRightArm.rotateAngleY = 3f;
//                modelBiped.bipedRightArm.rotateAngleX = 3f;
                //modelBiped.bipedLeftArm.rotateAngleY = 0.6f;
                //modelBiped.bipedLeftArm.rotateAngleX = 4.5f;

                float mult = (float) Math.sin(entity.ticksExisted) / 3;
                modelBiped.bipedRightArm.rotateAngleX = 3f;
                modelBiped.bipedRightArm.rotateAngleZ = mult;
                modelBiped.bipedLeftArm.rotateAngleX = 3f;
                modelBiped.bipedLeftArm.rotateAngleZ = -mult;
            }
        }

    }

    @SubscribeEvent
    public void stitchTexturesEvent(TextureStitchEvent.Pre event) {
        AssetLib.stitchSkillTextures(event);
    }

}
