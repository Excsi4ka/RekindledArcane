package excsi.rekindledarcane.common.network.server;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import excsi.rekindledarcane.client.gui.SkillSelectionScreen;
import excsi.rekindledarcane.common.data.player.PlayerData;
import excsi.rekindledarcane.common.data.player.PlayerDataManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ServerPacketUnEquipSkill implements IMessage, IMessageHandler<ServerPacketUnEquipSkill,IMessage> {

    public int slot;

    public ServerPacketUnEquipSkill() {}

    public ServerPacketUnEquipSkill(int slot) {
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
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(ServerPacketUnEquipSkill message, MessageContext ctx) {
        if(ctx.side == Side.CLIENT) {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            PlayerData data = PlayerDataManager.getPlayerData(player);
            int slot = message.slot;
            if (slot < 0 || message.slot > 4)
                return null;
            data.getEquippedActiveSkills().set(slot, null);
//            if (Minecraft.getMinecraft().currentScreen instanceof SkillSelectionScreen) {
//                SkillSelectionScreen screen = (SkillSelectionScreen) Minecraft.getMinecraft().currentScreen;
//                screen.setupSlots();
//            }
        }
        return null;
    }
}
