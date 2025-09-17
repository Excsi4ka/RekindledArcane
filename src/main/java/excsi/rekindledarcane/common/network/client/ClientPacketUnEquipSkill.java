package excsi.rekindledarcane.common.network.client;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.common.data.player.PlayerData;
import excsi.rekindledarcane.common.data.player.PlayerDataManager;
import excsi.rekindledarcane.common.network.PacketManager;
import excsi.rekindledarcane.common.network.server.ServerPacketUnEquipSkill;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class ClientPacketUnEquipSkill implements IMessage, IMessageHandler<ClientPacketUnEquipSkill,IMessage> {

    public int slot;

    public ClientPacketUnEquipSkill() {}

    public ClientPacketUnEquipSkill(int slot) {
        this.slot = slot;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        slot = buf.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(slot);
    }

    @Override
    public IMessage onMessage(ClientPacketUnEquipSkill message, MessageContext ctx) {
        if(ctx.side == Side.SERVER) {
            EntityPlayer player = ctx.getServerHandler().playerEntity;
            PlayerData data = PlayerDataManager.getPlayerData(player);
            data.getEquippedActiveSkills().set(message.slot, null);
            PacketManager.sendToPlayer(new ServerPacketUnEquipSkill(message.slot), player);
        }
        return null;
    }
}
