package excsi.rekindledarcane.common.network.server;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import excsi.rekindledarcane.api.RekindledArcaneAPI;
import excsi.rekindledarcane.api.skill.ICastableSkill;
import excsi.rekindledarcane.api.skill.ISkill;
import excsi.rekindledarcane.api.skill.ISkillCategory;
import excsi.rekindledarcane.client.util.ClientSkillCastingManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class ServerPacketNotifyCasting implements IMessage, IMessageHandler<ServerPacketNotifyCasting,IMessage> {

    public String categoryID, skillID;

    public int entityID;

    public ServerPacketNotifyCasting() {}

    public ServerPacketNotifyCasting(String categoryID, String skillID, int entityID) {
        this.categoryID = categoryID;
        this.skillID = skillID;
        this.entityID = entityID;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        categoryID = ByteBufUtils.readUTF8String(buf);
        skillID = ByteBufUtils.readUTF8String(buf);
        entityID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, categoryID);
        ByteBufUtils.writeUTF8String(buf, skillID);
        buf.writeInt(entityID);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(ServerPacketNotifyCasting message, MessageContext ctx) {
        if(ctx.side == Side.CLIENT) {
            Entity entity = Minecraft.getMinecraft().thePlayer.worldObj.getEntityByID(message.entityID);
            if(!(entity instanceof EntityPlayer))
                return null;
            EntityPlayer clientPlayer = (EntityPlayer) entity;
            ISkillCategory category = RekindledArcaneAPI.getCategory(message.categoryID);
            if (category == null)
                return null;
            ISkill skill = category.getSkillFromID(message.skillID);
            if (!(skill instanceof ICastableSkill))
                return null;
            ICastableSkill ability = (ICastableSkill) skill;
            ClientSkillCastingManager.INSTANCE.startCasting(clientPlayer, ability);
        }
        return null;
    }
}
