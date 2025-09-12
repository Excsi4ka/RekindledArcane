package excsi.rekindledarcane.client.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import excsi.rekindledarcane.api.skill.SkillType;
import excsi.rekindledarcane.client.AssetLib;
import excsi.rekindledarcane.client.ClientProxy;
import excsi.rekindledarcane.client.gui.SkillCategorySelectionScreen;
import excsi.rekindledarcane.client.gui.SkillSelectionScreen;
import excsi.rekindledarcane.client.util.BlendMode;
import excsi.rekindledarcane.client.util.StateRenderHelper;
import excsi.rekindledarcane.common.data.player.PlayerData;
import excsi.rekindledarcane.common.data.player.PlayerDataManager;
import excsi.rekindledarcane.common.registry.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.TextureStitchEvent;
import org.lwjgl.opengl.GL11;

public class ClientEventHandler {

    public static int currentlySelected = 0;

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        if (ClientProxy.activateAbilityKey.isPressed()) {
            //PacketManager.sendToServer(new ClientPacketKeyPress(0));
            Minecraft.getMinecraft().displayGuiScreen(new SkillCategorySelectionScreen());
        }
        if (ClientProxy.abilityChooseScreen.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new SkillSelectionScreen());
        }
        if(ClientProxy.switchLeft.isPressed()) {
            currentlySelected = currentlySelected - 1 < 0 ? 4 : currentlySelected - 1;
        }
        if(ClientProxy.switchRight.isPressed()) {
            currentlySelected = currentlySelected + 1 > 4 ? 0 : currentlySelected + 1;
        }
    }

    @SubscribeEvent
    public void onOverlay(RenderGameOverlayEvent.Post event) {
        if(event.type != ElementType.HOTBAR)
            return;
        int x = event.resolution.getScaledWidth() / 2 - 200;
        int y = event.resolution.getScaledHeight() - 25;

        Minecraft.getMinecraft().getTextureManager().bindTexture(AssetLib.skillIconTextureAtlas);
        PlayerData data = PlayerDataManager.getPlayerData(Minecraft.getMinecraft().thePlayer);
        Tessellator tes = Tessellator.instance;
        tes.startDrawingQuads();

        //StateRenderHelper.enableBlend();
        //StateRenderHelper.blendMode(BlendMode.DEFAULT);
        //GL11.glColor4f(1f,1f,1f,1f);
        for (int i = 0; i < 5; i++) {
            if(currentlySelected == i) {
                StateRenderHelper.batchDrawIcon(tes, 4 + 21 * i, y - 1, 22, 22, 0, SkillType.ABILITY.frameIcon);
                continue;
            }
            StateRenderHelper.batchDrawIcon(tes, 5 + 21 * i, y, 20, 20, 0, SkillType.PASSIVE.frameIcon);
        }
        tes.draw();
        //StateRenderHelper.restoreStates();
    }

    public static void renderCallback(Entity entity, ModelBiped modelBiped) {
        if(!(entity instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) entity;
        if(player.isUsingItem()) {
            EnumAction action = player.getHeldItem().getItemUseAction();
            if(action == ItemRegistry.raiseSword) {
                float partialTicks = Minecraft.getMinecraft().timer.renderPartialTicks;
                int time = player.getItemInUseDuration();
                float raising = Math.min(1f, (time + partialTicks)/ 8f);
                modelBiped.bipedRightArm.rotateAngleX = -1.75f * raising ;
                modelBiped.bipedRightArm.rotateAngleY = -0.5f * raising;
                modelBiped.bipedLeftArm.rotateAngleX = -1.75f * raising;
                modelBiped.bipedLeftArm.rotateAngleY = 0.5f * raising;
            }
        }
    }

    @SubscribeEvent
    public void stitchTexturesEvent(TextureStitchEvent.Pre event) {
        AssetLib.stitchSkillTextures(event);
    }
}
