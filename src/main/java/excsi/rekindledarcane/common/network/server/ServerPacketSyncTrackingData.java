package excsi.rekindledarcane.common.network.server;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import excsi.rekindledarcane.common.data.player.PlayerData;
import excsi.rekindledarcane.common.data.player.PlayerDataManager;
import excsi.rekindledarcane.api.data.skill.SkillData;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class ServerPacketSyncTrackingData implements IMessage, IMessageHandler<ServerPacketSyncTrackingData, IMessage> {

    public List<SkillData> enqueuedData;

    public ByteBuf buffer;

    public int dataEntries;

    public ServerPacketSyncTrackingData() {}

    public ServerPacketSyncTrackingData(List<SkillData> data) {
        this.enqueuedData = data;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        dataEntries = buf.readInt();
        buffer = buf;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(enqueuedData.size());
        enqueuedData.forEach(data -> {
            ByteBufUtils.writeUTF8String(buf, data.getRegistryName());
            data.writeToBuffer(buf);
        });
        enqueuedData.clear();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(ServerPacketSyncTrackingData message, MessageContext ctx) {
        if (ctx.side == Side.CLIENT) {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            PlayerData playerData = PlayerDataManager.getPlayerData(player);
            for (int i = 0; i < message.dataEntries; i++) {
                String key = ByteBufUtils.readUTF8String(message.buffer);
                playerData.trackingData.get(key).readFromBuffer(message.buffer);
            }
        }
        return null;
    }
}
