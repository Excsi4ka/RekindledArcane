package excsi.rekindledarcane.client.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import excsi.rekindledarcane.client.util.SkillIconTextureManager;
import excsi.rekindledarcane.client.TestCommand;
import excsi.rekindledarcane.client.gui.SkillCategorySelectionScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.TextureStitchEvent;

public class ClientEventHandler {

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        if(TestCommand.display && Minecraft.getMinecraft().thePlayer.ticksExisted > TestCommand.time) {
            Minecraft.getMinecraft().displayGuiScreen(new SkillCategorySelectionScreen());
            TestCommand.display = false;
        }
    }

    @SubscribeEvent
    public void stitchTexturesEvent(TextureStitchEvent.Pre event) {
        SkillIconTextureManager.stitchSkillTextures(event);
    }
}
