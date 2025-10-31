package excsi.rekindledarcane.common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import excsi.rekindledarcane.RekindledArcane;
import excsi.rekindledarcane.common.event.RekindledArcaneEvents;
import excsi.rekindledarcane.common.event.DataEventHandler;
import excsi.rekindledarcane.common.network.PacketManager;
import excsi.rekindledarcane.common.registry.RekindledArcaneEffects;
import excsi.rekindledarcane.common.registry.RekindledArcaneEntities;
import excsi.rekindledarcane.common.registry.RekindledArcaneItems;
import excsi.rekindledarcane.common.registry.SkillSystemRegistry;
import excsi.rekindledarcane.common.util.ParticleType;
import excsi.rekindledarcane.common.util.RekindledArcaneConfig;
import excsi.rekindledarcane.common.util.commands.SkillSystemCommands;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import java.awt.Color;
import java.lang.reflect.Field;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        RekindledArcaneConfig.loadConfig(event.getSuggestedConfigurationFile());
        PacketManager.init();
        SkillSystemRegistry.register();
        RekindledArcaneItems.register();
        RekindledArcaneEntities.register();

        if(RekindledArcaneConfig.extendPotionIDS) extendPotionIDS();
        RekindledArcaneEffects.register();

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

    public void addEffect(ParticleType type, World world, double x, double y, double z, int r, int g, int b, int alpha, float scale, float resizeSpeed, int maxAge) {}

    public void addEffect(ParticleType type, World world, double x, double y, double z, int r, int g, int b, int alpha, double dx, double dy, double dz, float scale, float resizeSpeed, int maxAge) {}

    public void addBeam(ParticleType type, World world, double x, double y, double z, int r, int g, int b, float scale, int maxAge) {}

    public void extendPotionIDS() {
        if(Potion.potionTypes.length >= 256)
            return;
        for(Field f : Potion.class.getDeclaredFields()) {
            f.setAccessible(true);
            try {
                if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) {
                    Field modfield = Field.class.getDeclaredField("modifiers");
                    modfield.setAccessible(true);
                    modfield.setInt(f, f.getModifiers() & -17);
                    Potion[] potionTypes = (Potion[])(f.get(null));
                    Potion[] newPotionTypes = new Potion[256];
                    System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
                    f.set(null, newPotionTypes);
                }
            } catch (Exception e) {
                RekindledArcane.LOG.error("Couldn't expand potion IDS");
                e.printStackTrace();
            }
        }
    }
}
