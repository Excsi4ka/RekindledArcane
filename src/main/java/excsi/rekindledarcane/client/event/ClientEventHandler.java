package excsi.rekindledarcane.client.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.api.RekindledArcaneAPI;
import excsi.rekindledarcane.api.skill.IActiveAbilitySkill;
import excsi.rekindledarcane.api.skill.SkillType;
import excsi.rekindledarcane.client.AssetLib;
import excsi.rekindledarcane.client.ClientProxy;
import excsi.rekindledarcane.client.gui.RadialMenuEquipScreen;
import excsi.rekindledarcane.client.gui.SkillCategorySelectionScreen;
import excsi.rekindledarcane.client.util.BlendMode;
import excsi.rekindledarcane.client.util.ClientSkillManager;
import excsi.rekindledarcane.client.util.RenderHelperWrapper;
import excsi.rekindledarcane.client.util.ScreenShakeManager;
import excsi.rekindledarcane.common.data.player.PlayerData;
import excsi.rekindledarcane.common.data.player.PlayerDataManager;
import excsi.rekindledarcane.common.network.PacketManager;
import excsi.rekindledarcane.common.network.client.ClientPacketActivateAbility;
import excsi.rekindledarcane.common.network.client.ClientPacketSetActiveSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.TextureStitchEvent;
import org.lwjgl.opengl.GL11;

public class ClientEventHandler {

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        if (ClientProxy.skillTreeOpen.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new SkillCategorySelectionScreen());
        }
        if (ClientProxy.activateAbilityKey.isPressed()) {
            PacketManager.sendToServer(new ClientPacketActivateAbility());
        }
        if (ClientProxy.switchLeft.isPressed()) {
            PlayerData data = PlayerDataManager.getPlayerData(Minecraft.getMinecraft().thePlayer);
            int slot = data.getCurrentActiveSlot() - 1 < 0 ? data.getActiveSlotCount() - 1 : data.getCurrentActiveSlot() - 1;
            PacketManager.sendToServer(new ClientPacketSetActiveSlot(slot));
        }
        if (ClientProxy.switchRight.isPressed()) {
            PlayerData data = PlayerDataManager.getPlayerData(Minecraft.getMinecraft().thePlayer);
            int slot = data.getCurrentActiveSlot() + 1 > data.getActiveSlotCount() - 1 ? 0 : data.getCurrentActiveSlot() + 1;
            PacketManager.sendToServer(new ClientPacketSetActiveSlot(slot));
        }
        if (ClientProxy.openRadialMenu.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new RadialMenuEquipScreen());
        }
    }

    @SubscribeEvent
    public void onOverlay(RenderGameOverlayEvent.Post event) {
        if (event.type != ElementType.HOTBAR)
            return;
        int x = event.resolution.getScaledWidth() / 2 - 130;
        int y = event.resolution.getScaledHeight() - 22;

        Minecraft.getMinecraft().getTextureManager().bindTexture(AssetLib.skillIconTextureAtlas);
        PlayerData data = PlayerDataManager.getPlayerData(Minecraft.getMinecraft().thePlayer);
        Tessellator tes = Tessellator.instance;
        tes.startDrawingQuads();

        RenderHelperWrapper.enableBlend();
        RenderHelperWrapper.blendMode(BlendMode.DEFAULT);
        GL11.glColor4f(1f, 1f, 1f, 1f);
        IActiveAbilitySkill ability = data.getEquippedActiveSkills().get(data.getCurrentActiveSlot());
        if (ability != null) {
            RenderHelperWrapper.batchDrawIcon(tes, x + 3, y + 3, 16, 16, 0, ability.getIcon());
        }
        RenderHelperWrapper.batchDrawIcon(tes, x, y, 22, 22, 0, SkillType.PASSIVE.getFrameIcon());
        tes.draw();
        RenderHelperWrapper.restoreStates();
//        if (ClientSkillManager.INSTANCE.isClientCasting()) {
//            int centerX = event.resolution.getScaledWidth() / 2;
//            int centerY = event.resolution.getScaledHeight() / 2;
//            int time = ClientSkillManager.INSTANCE.getClientRemainingCastTime();
//            String message = time / 20d + " seconds";
//            int offset = Minecraft.getMinecraft().fontRenderer.getStringWidth(message) / 2;
//            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(message, centerX - offset, centerY - 10, 0xFFFFFF);
//        }
    }

    public static float handleExtendedReach(float currentReachDistance) {
        return (float) (currentReachDistance + Minecraft.getMinecraft().thePlayer.getEntityAttribute(RekindledArcaneAPI.REACH_DISTANCE).getAttributeValue());
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START)
            return;
        if (event.side == Side.SERVER)
            return;
        ClientSkillManager.INSTANCE.tick(event.player);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START)
            return;
        ScreenShakeManager.INSTANCE.tick();
    }

    @SubscribeEvent
    public void stitchTexturesEvent(TextureStitchEvent.Pre event) {
        AssetLib.stitchSkillTextures(event);
    }
}
