package excsi.rekindledarcane.common.network.client;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.common.data.player.PlayerData;
import excsi.rekindledarcane.common.data.player.PlayerDataManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public class ClientPacketSetActiveSlot implements IMessage, IMessageHandler<ClientPacketSetActiveSlot,IMessage> {

    public int slot;

    public ClientPacketSetActiveSlot() {}

    public ClientPacketSetActiveSlot(int slot) {
        this.slot = MathHelper.clamp_int(slot, 0, 7);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        slot = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(slot);
    }

    @Override
    public IMessage onMessage(ClientPacketSetActiveSlot message, MessageContext ctx) {
        if (ctx.side == Side.SERVER) {
            EntityPlayer player = ctx.getServerHandler().playerEntity;
            PlayerData data = PlayerDataManager.getPlayerData(player);
            data.setCurrentActiveSlot(message.slot, player,true);
        }
        return null;
    }
}
