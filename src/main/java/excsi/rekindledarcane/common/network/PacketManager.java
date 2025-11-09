package excsi.rekindledarcane.common.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.RekindledArcane;
import excsi.rekindledarcane.common.network.client.ClientPacketEquipSkill;
import excsi.rekindledarcane.common.network.client.ClientPacketActivateAbility;
import excsi.rekindledarcane.common.network.client.ClientPacketSkillToggle;
import excsi.rekindledarcane.common.network.client.ClientPacketUnlockSkill;
import excsi.rekindledarcane.common.network.client.ClientPacketSetActiveSlot;
import excsi.rekindledarcane.common.network.server.ServerPacketEquipSkill;
import excsi.rekindledarcane.common.network.server.ServerPacketForgetSkill;
import excsi.rekindledarcane.common.network.server.ServerPacketNotifyCasting;
import excsi.rekindledarcane.common.network.server.ServerPacketSetActiveSlot;
import excsi.rekindledarcane.common.network.server.ServerPacketSkillToggle;
import excsi.rekindledarcane.common.network.server.ServerPacketSyncOnJoin;
import excsi.rekindledarcane.common.network.server.ServerPacketSyncSkillPoints;
import excsi.rekindledarcane.common.network.server.ServerPacketSyncTrackingData;
import excsi.rekindledarcane.common.network.server.ServerPacketUnlockSkill;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketManager {

    public static final SimpleNetworkWrapper WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel(RekindledArcane.MODID);

    public static void init() {
        int id = 0;
        WRAPPER.registerMessage(ClientPacketUnlockSkill.class, ClientPacketUnlockSkill.class, id++, Side.SERVER);
        WRAPPER.registerMessage(ClientPacketActivateAbility.class, ClientPacketActivateAbility.class, id++, Side.SERVER);
        WRAPPER.registerMessage(ClientPacketEquipSkill.class, ClientPacketEquipSkill.class, id++, Side.SERVER);
        //WRAPPER.registerMessage(ClientPacketUnEquipSkill.class, ClientPacketUnEquipSkill.class, id++, Side.SERVER);
        WRAPPER.registerMessage(ClientPacketSetActiveSlot.class, ClientPacketSetActiveSlot.class, id++, Side.SERVER);
        WRAPPER.registerMessage(ClientPacketSkillToggle.class, ClientPacketSkillToggle.class, id++, Side.SERVER);

        WRAPPER.registerMessage(ServerPacketSyncOnJoin.class, ServerPacketSyncOnJoin.class, id++, Side.CLIENT);
        WRAPPER.registerMessage(ServerPacketUnlockSkill.class, ServerPacketUnlockSkill.class, id++, Side.CLIENT);
        WRAPPER.registerMessage(ServerPacketForgetSkill.class, ServerPacketForgetSkill.class, id++, Side.CLIENT);
        WRAPPER.registerMessage(ServerPacketSyncTrackingData.class, ServerPacketSyncTrackingData.class, id++, Side.CLIENT);
        WRAPPER.registerMessage(ServerPacketSyncSkillPoints.class, ServerPacketSyncSkillPoints.class, id++, Side.CLIENT);
        WRAPPER.registerMessage(ServerPacketEquipSkill.class, ServerPacketEquipSkill.class, id++, Side.CLIENT);
        //WRAPPER.registerMessage(ServerPacketUnEquipSkill.class, ServerPacketUnEquipSkill.class, id++, Side.CLIENT);
        WRAPPER.registerMessage(ServerPacketNotifyCasting.class, ServerPacketNotifyCasting.class, id++, Side.CLIENT);
        WRAPPER.registerMessage(ServerPacketSetActiveSlot.class, ServerPacketSetActiveSlot.class, id++, Side.CLIENT);
        WRAPPER.registerMessage(ServerPacketSkillToggle.class, ServerPacketSkillToggle.class, id++, Side.CLIENT);
    }

    public static void sendToPlayer(IMessage message, EntityPlayer player) {
        WRAPPER.sendTo(message, (EntityPlayerMP) player);
    }

    public static void sendToServer(IMessage message) {
        WRAPPER.sendToServer(message);
    }
}
