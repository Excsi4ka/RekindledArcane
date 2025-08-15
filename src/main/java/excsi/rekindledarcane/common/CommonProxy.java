package excsi.rekindledarcane.common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import excsi.rekindledarcane.common.event.RekindledArcaneEvents;
import excsi.rekindledarcane.common.event.DataEventHandler;
import excsi.rekindledarcane.common.network.PacketManager;
import excsi.rekindledarcane.common.registry.SkillSystemRegistry;
import excsi.rekindledarcane.common.util.Config;
import excsi.rekindledarcane.common.util.commands.SkillSystemCommands;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        Config.loadConfig(event.getSuggestedConfigurationFile());
        PacketManager.init();
        SkillSystemRegistry.register();

        MinecraftForge.EVENT_BUS.register(new DataEventHandler());
        FMLCommonHandler.instance().bus().register(new DataEventHandler());
        MinecraftForge.EVENT_BUS.register(new RekindledArcaneEvents());
        FMLCommonHandler.instance().bus().register(new RekindledArcaneEvents());

    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new SkillSystemCommands());
    }
}
