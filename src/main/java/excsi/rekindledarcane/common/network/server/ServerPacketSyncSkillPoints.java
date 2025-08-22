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

public class ServerPacketSyncSkillPoints implements IMessage, IMessageHandler<ServerPacketSyncSkillPoints, IMessage> {

    public int skillPoints;

    public ServerPacketSyncSkillPoints() {}

    public ServerPacketSyncSkillPoints(int skillPoints) {
        this.skillPoints = skillPoints;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        skillPoints = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(skillPoints);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(ServerPacketSyncSkillPoints message, MessageContext ctx) {
        if(ctx.side == Side.CLIENT) {
            PlayerData data = PlayerDataManager.getPlayerData(Minecraft.getMinecraft().thePlayer);
            data.setSkillPoints(message.skillPoints);
        }
        return null;
    }
}
