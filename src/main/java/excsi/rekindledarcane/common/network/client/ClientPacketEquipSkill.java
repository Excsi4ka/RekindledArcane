package excsi.rekindledarcane.common.network.client;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.api.RekindledArcaneAPI;
import excsi.rekindledarcane.api.skill.IActiveSkillAbility;
import excsi.rekindledarcane.api.skill.ISkill;
import excsi.rekindledarcane.api.skill.ISkillCategory;
import excsi.rekindledarcane.common.data.player.PlayerData;
import excsi.rekindledarcane.common.data.player.PlayerDataManager;
import excsi.rekindledarcane.common.network.PacketManager;
import excsi.rekindledarcane.common.network.server.ServerPacketEquipSkill;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class ClientPacketEquipSkill implements IMessage, IMessageHandler<ClientPacketEquipSkill,IMessage> {

    public String categoryID, skillID;

    public int slot;

    public ClientPacketEquipSkill() {}

    public ClientPacketEquipSkill(IActiveSkillAbility skillAbility, int slot) {
        this.categoryID = skillAbility.getSkillCategory().getNameID();
        this.skillID = skillAbility.getNameID();
        this.slot = slot;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        categoryID = ByteBufUtils.readUTF8String(buf);
        skillID = ByteBufUtils.readUTF8String(buf);
        slot = buf.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, categoryID);
        ByteBufUtils.writeUTF8String(buf, skillID);
        buf.writeByte(slot);
    }

    @Override
    public IMessage onMessage(ClientPacketEquipSkill message, MessageContext ctx) {
        if (ctx.side == Side.SERVER) {
            EntityPlayer player = ctx.getServerHandler().playerEntity;
            PlayerData data = PlayerDataManager.getPlayerData(player);
            ISkillCategory category = RekindledArcaneAPI.getCategory(message.categoryID);
            if (category == null)
                return null;
            ISkill skill = category.getSkillFromID(message.skillID);
            if (!data.hasSkill(skill))
                return null;
            if (!(skill instanceof IActiveSkillAbility))
                return null;
            if (message.slot < data.getActiveSlotCount()) {
                IActiveSkillAbility ability = (IActiveSkillAbility) skill;
                data.getEquippedActiveSkills().set(message.slot, ability);
                PacketManager.sendToPlayer(new ServerPacketEquipSkill(ability, message.slot), player);
            }
        }
        return null;
    }
}
