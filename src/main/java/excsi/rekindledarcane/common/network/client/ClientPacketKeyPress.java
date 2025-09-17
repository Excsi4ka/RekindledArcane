package excsi.rekindledarcane.common.network.client;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.api.skill.IActiveSkillAbility;
import excsi.rekindledarcane.common.data.player.PlayerData;
import excsi.rekindledarcane.common.data.player.PlayerDataManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class ClientPacketKeyPress implements IMessage, IMessageHandler<ClientPacketKeyPress,IMessage> {

    public byte currentlySelected;

    public ClientPacketKeyPress() {}

    public ClientPacketKeyPress(int keyType) {
        this.currentlySelected = (byte) keyType;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        currentlySelected = buf.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(currentlySelected);
    }

    @Override
    public IMessage onMessage(ClientPacketKeyPress message, MessageContext ctx) {
        if (ctx.side == Side.SERVER) {
            EntityPlayer player = ctx.getServerHandler().playerEntity;
            PlayerData data = PlayerDataManager.getPlayerData(player);
            IActiveSkillAbility ability = data.getEquippedActiveSkills().get(message.currentlySelected);
            if(ability == null)
                return null;
            if(ability.canUse(player)) {
                ability.activateAbility(player);
                ability.afterActivate(player);
            }
        }
        return null;
    }
}
