package excsi.rekindledarcane.common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import excsi.rekindledarcane.common.event.RekindledArcaneEvents;
import excsi.rekindledarcane.common.event.DataEventHandler;
import excsi.rekindledarcane.common.network.PacketManager;
import excsi.rekindledarcane.common.registry.RekindledArcaneEntities;
import excsi.rekindledarcane.common.registry.RekindledArcaneItems;
import excsi.rekindledarcane.common.registry.SkillSystemRegistry;
import excsi.rekindledarcane.common.util.ParticleType;
import excsi.rekindledarcane.common.util.RekindledArcaneConfig;
import excsi.rekindledarcane.common.util.commands.SkillSystemCommands;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import java.awt.*;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        RekindledArcaneConfig.loadConfig(event.getSuggestedConfigurationFile());
        PacketManager.init();
        SkillSystemRegistry.register();
        RekindledArcaneItems.register();
        RekindledArcaneEntities.register();

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

    public void addEffect(ParticleType type, World world, double x, double y, double z, Color color, float scale, float resizeSpeed, int maxAge) {}

    public void addEffect(ParticleType type, World world, double x, double y, double z, int r, int g, int b, float scale, float resizeSpeed, int maxAge) {}

    public void addEffect(ParticleType type, World world, double x, double y, double z, int r, int g, int b, double dx, double dy, double dz, float scale, float resizeSpeed, int maxAge) {}

}
