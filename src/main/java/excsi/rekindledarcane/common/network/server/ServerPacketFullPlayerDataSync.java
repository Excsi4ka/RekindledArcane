package excsi.rekindledarcane.common.network.server;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import excsi.rekindledarcane.common.data.player.PlayerData;
import excsi.rekindledarcane.common.data.player.PlayerDataManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class ServerPacketFullPlayerDataSync implements IMessage, IMessageHandler<ServerPacketFullPlayerDataSync,IMessage> {

    public NBTTagCompound data;

    public ServerPacketFullPlayerDataSync() {}

    public ServerPacketFullPlayerDataSync(NBTTagCompound data) {
        this.data = data;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        data = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, data);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(ServerPacketFullPlayerDataSync message, MessageContext ctx) {
        if (ctx.side == Side.CLIENT) {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            PlayerData playerData = PlayerDataManager.setPlayerDataDefault(player);
            playerData.readData(message.data, player);
        }
        return null;
    }
}
