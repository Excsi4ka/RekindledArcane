package excsi.rekindledarcane;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import excsi.rekindledarcane.common.CommonProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = RekindledArcane.MODID, name = RekindledArcane.NAME, version = RekindledArcane.VERSION, dependencies = "required-after:Baubles")
public class RekindledArcane {

    public static final String MODID = "RekindledArcane";

    public static final String NAME = "Rekindled Arcane";

    public static final String VERSION = "1.0.0";

    public static final Logger LOG = LogManager.getLogger(MODID);

    @SidedProxy(clientSide = "excsi.rekindledarcane.client.ClientProxy", serverSide = "excsi.rekindledarcane.common.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance(RekindledArcane.MODID)
    public static RekindledArcane instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }
}
