package excsi.rekindledarcane.common.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.RekindledArcane;
import excsi.rekindledarcane.common.network.client.ClientPacketUnlockSkill;
import excsi.rekindledarcane.common.network.server.ServerPacketFullPlayerDataSync;
import excsi.rekindledarcane.common.network.server.ServerPacketSyncSkillPoints;
import excsi.rekindledarcane.common.network.server.ServerPacketSyncTrackingData;
import excsi.rekindledarcane.common.network.server.ServerPacketUnlockSkill;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketManager {

    public static final SimpleNetworkWrapper WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel(RekindledArcane.MODID);

    public static void init() {
        int id = 0;
        WRAPPER.registerMessage(ServerPacketFullPlayerDataSync.class, ServerPacketFullPlayerDataSync.class, id++, Side.CLIENT);
        WRAPPER.registerMessage(ClientPacketUnlockSkill.class, ClientPacketUnlockSkill.class, id++, Side.SERVER);
        WRAPPER.registerMessage(ServerPacketUnlockSkill.class, ServerPacketUnlockSkill.class, id++, Side.CLIENT);
        WRAPPER.registerMessage(ServerPacketSyncTrackingData.class, ServerPacketSyncTrackingData.class, id++, Side.CLIENT);
        WRAPPER.registerMessage(ServerPacketSyncSkillPoints.class, ServerPacketSyncSkillPoints.class, id++, Side.CLIENT);
    }

    public static void sendToPlayer(IMessage message, EntityPlayer player) {
        WRAPPER.sendTo(message, (EntityPlayerMP) player);
    }

    public static void sendToServer(IMessage message) {
        WRAPPER.sendToServer(message);
    }
}
