package excsi.rekindledarcane.client.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import excsi.rekindledarcane.client.AssetLib;
import excsi.rekindledarcane.client.TestCommand;
import excsi.rekindledarcane.client.gui.SkillCategorySelectionScreen;
import excsi.rekindledarcane.common.data.player.PlayerDataManager;
import excsi.rekindledarcane.common.data.skill.templates.BasicSkillData;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.TextureStitchEvent;

public class ClientEventHandler {

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        if (TestCommand.display && Minecraft.getMinecraft().thePlayer.ticksExisted > TestCommand.time) {
            Minecraft.getMinecraft().displayGuiScreen(new SkillCategorySelectionScreen());
            TestCommand.display = false;
        }
    }

    @SubscribeEvent
    public void stitchTexturesEvent(TextureStitchEvent.Pre event) {
        AssetLib.stitchSkillTextures(event);
    }

    @SubscribeEvent
    public void onOverlay(RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.ALL)
            return;
        int x = event.resolution.getScaledWidth() / 2;
        int y = event.resolution.getScaledHeight() / 2;
        PlayerDataManager.getPlayerData(Minecraft.getMinecraft().thePlayer).getSkillDataTracker().tickingData.forEach(data -> {
            if (data instanceof BasicSkillData) {
                int time = ((BasicSkillData) data).getSkillCooldown();
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(String.valueOf(time), x + 10, y, 0xFFFFFF);
            }
        });
    }
}
