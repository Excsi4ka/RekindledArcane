package excsi.rekindledarcane.common.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import excsi.rekindledarcane.RekindledArcane;

public class PacketManager {

    public static final SimpleNetworkWrapper WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel(RekindledArcane.MODID);

    public static void init() {
        int id = 0;
    }
}
