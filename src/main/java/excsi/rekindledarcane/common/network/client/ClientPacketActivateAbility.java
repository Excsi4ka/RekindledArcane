package excsi.rekindledarcane.common.network.client;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.api.skill.IActiveAbilitySkill;
import excsi.rekindledarcane.api.skill.ICastableSkill;
import excsi.rekindledarcane.common.data.player.PlayerData;
import excsi.rekindledarcane.common.data.player.PlayerDataManager;
import excsi.rekindledarcane.common.network.PacketManager;
import excsi.rekindledarcane.common.network.server.ServerPacketNotifyCasting;
import excsi.rekindledarcane.common.skill.ServerSkillManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldServer;

import java.util.Set;

public class ClientPacketActivateAbility implements IMessage, IMessageHandler<ClientPacketActivateAbility,IMessage> {

    public ClientPacketActivateAbility() {}

    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public void toBytes(ByteBuf buf) {}

    @Override
    public IMessage onMessage(ClientPacketActivateAbility message, MessageContext ctx) {
        if (ctx.side == Side.SERVER) {
            EntityPlayer player = ctx.getServerHandler().playerEntity;
            PlayerData data = PlayerDataManager.getPlayerData(player);
            IActiveAbilitySkill ability = data.getEquippedActiveSkills().get(data.getCurrentActiveSlot());

            if (ability == null)
                return null;

            if (!ability.canUse(player))
                return null;

            if (ServerSkillManager.INSTANCE.alreadyCasting(player)) //maybe cancel casting if activated twice
                return null;

            if (ability instanceof ICastableSkill) {
                ICastableSkill castableAbility = (ICastableSkill) ability;
                ServerSkillManager.INSTANCE.startCasting(player, castableAbility);

                WorldServer worldServer = (WorldServer) player.worldObj;
                EntityTracker tracker = worldServer.getEntityTracker();
                Set<EntityPlayer> trackingPlayers = tracker.getTrackingPlayers(player);

                trackingPlayers.forEach(trackingPlayer ->
                        PacketManager.sendToPlayer(new ServerPacketNotifyCasting(ability.getSkillCategory().getNameID(),
                                ability.getNameID(), player.getEntityId()), trackingPlayer));
                //the player isn't tracking itself so must notify the packet sender as well
                PacketManager.sendToPlayer(new ServerPacketNotifyCasting(ability.getSkillCategory().getNameID(),
                        ability.getNameID(), player.getEntityId()), player);
            } else {
                ability.resolveSkillCast(player);
            }
        }
        return null;
    }
}
