package excsi.rekindledarcane.client.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.api.skill.IActiveAbilitySkill;
import excsi.rekindledarcane.api.skill.SkillType;
import excsi.rekindledarcane.client.AssetLib;
import excsi.rekindledarcane.client.ClientProxy;
import excsi.rekindledarcane.client.gui.SkillCategorySelectionScreen;
import excsi.rekindledarcane.client.gui.SkillSelectionScreen;
import excsi.rekindledarcane.client.util.BlendMode;
import excsi.rekindledarcane.client.util.ClientSkillCastingManager;
import excsi.rekindledarcane.client.util.StateRenderHelper;
import excsi.rekindledarcane.common.data.player.PlayerData;
import excsi.rekindledarcane.common.data.player.PlayerDataManager;
import excsi.rekindledarcane.common.network.PacketManager;
import excsi.rekindledarcane.common.network.client.ClientPacketActivateAbility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.TextureStitchEvent;
import org.lwjgl.opengl.GL11;

public class ClientEventHandler {

    public static int currentlySelected = 0;

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        if (ClientProxy.temp.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new SkillCategorySelectionScreen());
        }
        if(ClientProxy.activateAbilityKey.isPressed()) {
            PacketManager.sendToServer(new ClientPacketActivateAbility(currentlySelected));
        }
        if (ClientProxy.abilityChooseScreen.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new SkillSelectionScreen());
        }
        if(ClientProxy.switchLeft.isPressed()) {
            PlayerData data = PlayerDataManager.getPlayerData(Minecraft.getMinecraft().thePlayer);
            currentlySelected = currentlySelected - 1 < 0 ? data.getActiveSlotCount() - 1 : currentlySelected - 1;
        }
        if(ClientProxy.switchRight.isPressed()) {
            PlayerData data = PlayerDataManager.getPlayerData(Minecraft.getMinecraft().thePlayer);
            currentlySelected = currentlySelected + 1 > data.getActiveSlotCount() - 1 ? 0 : currentlySelected + 1;
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

        StateRenderHelper.enableBlend();
        StateRenderHelper.blendMode(BlendMode.DEFAULT);
        GL11.glColor4f(1f,1f,1f,1f);
        for (int i = 0; i < data.getActiveSlotCount(); i++) {
            IActiveAbilitySkill ability = data.getEquippedActiveSkills().get(i);
            if(ability != null) {
                StateRenderHelper.batchDrawIcon(tes,5 + 21 * i, y, 20, 20, 0, ability.getIcon());
            }
            if(currentlySelected == i) {
                StateRenderHelper.batchDrawIcon(tes, 4 + 21 * i, y - 1, 22, 22, 0, SkillType.ABILITY.frameIcon);
                continue;
            }
            StateRenderHelper.batchDrawIcon(tes, 5 + 21 * i, y, 20, 20, 0, SkillType.PASSIVE.frameIcon);
        }
        tes.draw();
        StateRenderHelper.restoreStates();
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.phase == TickEvent.Phase.START)
            return;
        if(event.side == Side.SERVER)
            return;
        ClientSkillCastingManager.INSTANCE.tick(event.player);
    }

    @SubscribeEvent
    public void stitchTexturesEvent(TextureStitchEvent.Pre event) {
        AssetLib.stitchSkillTextures(event);
    }
}
