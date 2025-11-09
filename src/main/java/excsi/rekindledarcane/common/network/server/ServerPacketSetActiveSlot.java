package excsi.rekindledarcane.common.network.server;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import excsi.rekindledarcane.common.data.player.PlayerData;
import excsi.rekindledarcane.common.data.player.PlayerDataManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class ServerPacketSetActiveSlot implements IMessage, IMessageHandler<ServerPacketSetActiveSlot,IMessage> {

    public int slot;

    public ServerPacketSetActiveSlot() {}

    public ServerPacketSetActiveSlot(int slot) {
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
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(ServerPacketSetActiveSlot message, MessageContext ctx) {
        if (ctx.side == Side.CLIENT) {
            PlayerData data = PlayerDataManager.getPlayerData(Minecraft.getMinecraft().thePlayer);
            data.setCurrentActiveSlot(message.slot, null, false);
        }
        return null;
    }
}
