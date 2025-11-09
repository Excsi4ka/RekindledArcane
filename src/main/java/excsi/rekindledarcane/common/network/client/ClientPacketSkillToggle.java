package excsi.rekindledarcane.common.network.client;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.api.RekindledArcaneAPI;
import excsi.rekindledarcane.api.skill.ISkill;
import excsi.rekindledarcane.api.skill.ISkillCategory;
import excsi.rekindledarcane.api.skill.IToggleSwitch;
import excsi.rekindledarcane.common.data.player.PlayerData;
import excsi.rekindledarcane.common.data.player.PlayerDataManager;
import excsi.rekindledarcane.common.network.PacketManager;
import excsi.rekindledarcane.common.network.server.ServerPacketSkillToggle;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class ClientPacketSkillToggle implements IMessage, IMessageHandler<ClientPacketSkillToggle,IMessage> {

    public String categoryID, skillID;

    public ClientPacketSkillToggle() {}

    public ClientPacketSkillToggle(ISkillCategory skillCategory, ISkill skill) {
        this.categoryID = skillCategory.getNameID();
        this.skillID = skill.getNameID();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        categoryID = ByteBufUtils.readUTF8String(buf);
        skillID = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, categoryID);
        ByteBufUtils.writeUTF8String(buf, skillID);
    }

    @Override
    public IMessage onMessage(ClientPacketSkillToggle message, MessageContext ctx) {
        if (ctx.side == Side.SERVER) {
            EntityPlayer player = ctx.getServerHandler().playerEntity;
            PlayerData data = PlayerDataManager.getPlayerData(player);
            ISkillCategory category = RekindledArcaneAPI.getCategory(message.categoryID);
            if (category == null)
                return null;
            ISkill skill = category.getSkillFromID(message.skillID);
            if (skill == null)
                return null;
            if (!data.hasSkill(skill))
                return null;
            if (!(skill instanceof IToggleSwitch))
                return null;
            IToggleSwitch toggleSkill = (IToggleSwitch) skill;
            boolean toggled = toggleSkill.isToggled(player);
            toggleSkill.toggle(player, !toggled);
            PacketManager.sendToPlayer(new ServerPacketSkillToggle(category, skill, !toggled), player);
        }
        return null;
    }
}
