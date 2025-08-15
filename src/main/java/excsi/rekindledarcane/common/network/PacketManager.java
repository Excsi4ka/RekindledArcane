package excsi.rekindledarcane.common.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.RekindledArcane;
import excsi.rekindledarcane.common.network.server.PacketFullPlayerDataSync;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketManager {

    public static final SimpleNetworkWrapper WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel(RekindledArcane.MODID);

    public static void init() {
        int id = 0;
        WRAPPER.registerMessage(PacketFullPlayerDataSync.class, PacketFullPlayerDataSync.class, id++, Side.CLIENT);
    }

    public static void sendToPlayer(IMessage message, EntityPlayer player) {
        WRAPPER.sendTo(message, (EntityPlayerMP) player);
    }
}
