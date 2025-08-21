package excsi.rekindledarcane.client;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import excsi.rekindledarcane.client.event.ClientEventHandler;
import excsi.rekindledarcane.common.CommonProxy;
import excsi.rekindledarcane.common.util.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        FMLCommonHandler.instance().bus().register(new ClientEventHandler());
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        Minecraft.getMinecraft()
                .getTextureManager()
                .loadTextureMap(AssetLib.skillIconTextureAtlas,
                        new TextureMap(Config.skillIconsTextureAtlasID,"textures/skills"));
    }

    @Override
    public void serverStarting(FMLServerStartingEvent event) {
        super.serverStarting(event);
        //client only commands
        ClientCommandHandler.instance.registerCommand(new TestCommand());
    }
}
