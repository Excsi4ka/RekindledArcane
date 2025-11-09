package excsi.rekindledarcane.common.network.server;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import excsi.rekindledarcane.api.RekindledArcaneAPI;
import excsi.rekindledarcane.api.skill.ISkill;
import excsi.rekindledarcane.api.skill.ISkillCategory;
import excsi.rekindledarcane.api.skill.IToggleSwitch;
import excsi.rekindledarcane.common.data.player.PlayerData;
import excsi.rekindledarcane.common.data.player.PlayerDataManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ServerPacketSkillToggle implements IMessage, IMessageHandler<ServerPacketSkillToggle,IMessage> {

    public String categoryID, skillID;

    public boolean toggled;

    public ServerPacketSkillToggle() {}

    public ServerPacketSkillToggle(ISkillCategory skillCategory, ISkill skill, boolean toggled) {
        this.categoryID = skillCategory.getNameID();
        this.skillID = skill.getNameID();
        this.toggled = toggled;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        categoryID = ByteBufUtils.readUTF8String(buf);
        skillID = ByteBufUtils.readUTF8String(buf);
        toggled = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, categoryID);
        ByteBufUtils.writeUTF8String(buf, skillID);
        buf.writeBoolean(toggled);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(ServerPacketSkillToggle message, MessageContext ctx) {
        if (ctx.side == Side.CLIENT) {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            ISkillCategory category = RekindledArcaneAPI.getCategory(message.categoryID);
            ISkill skill = category.getSkillFromID(message.skillID);
            IToggleSwitch toggleSkill = (IToggleSwitch) skill;
            toggleSkill.toggle(player, message.toggled);
        }
        return null;
    }
}
