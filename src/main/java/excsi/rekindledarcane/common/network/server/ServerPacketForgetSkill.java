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
import excsi.rekindledarcane.client.gui.SkillTreeScreen;
import excsi.rekindledarcane.client.gui.widgets.SkillUnlockWidget;
import excsi.rekindledarcane.common.data.player.PlayerData;
import excsi.rekindledarcane.common.data.player.PlayerDataManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ServerPacketForgetSkill implements IMessage, IMessageHandler<ServerPacketForgetSkill,IMessage> {

    public String categoryID, skillID;

    public ServerPacketForgetSkill() {}

    public ServerPacketForgetSkill(ISkillCategory skillCategory, ISkill skill) {
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
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(ServerPacketForgetSkill message, MessageContext ctx) {
        if (ctx.side == Side.CLIENT) {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            PlayerData data = PlayerDataManager.getPlayerData(player);
            ISkillCategory category = RekindledArcaneAPI.getCategory(message.categoryID);
            if (category == null)
                return null;
            ISkill skill = category.getSkillFromID(message.skillID);
            if (skill == null)
                return null;
            data.forgetSkill(player, skill, false);
            if (Minecraft.getMinecraft().currentScreen instanceof SkillTreeScreen) {
                SkillTreeScreen screen = (SkillTreeScreen) Minecraft.getMinecraft().currentScreen;
                SkillUnlockWidget widget = screen.skillWidgets.get(skill);
                if (widget != null) {
                    widget.unlocked = false;
                }
            }
        }
        return null;
    }
}
