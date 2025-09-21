package excsi.rekindledarcane.client.util;

import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.api.skill.ICastableAbility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;
import java.util.UUID;

public class ClientSkillCastingManager {

    public static ClientSkillCastingManager INSTANCE = new ClientSkillCastingManager();

    private final HashMap<UUID, ICastableAbility> playerAbilityMap;

    private final HashMap<UUID, Integer> playerCastTimeMap;

    private ClientSkillCastingManager() {
        playerAbilityMap = new HashMap<>();
        playerCastTimeMap = new HashMap<>();
    }

    /**
     * Ticked from {@link excsi.rekindledarcane.client.event.ClientEventHandler#onPlayerTick(TickEvent.PlayerTickEvent)}
    */
    public void tick(EntityPlayer player) {
        if(!playerCastTimeMap.containsKey(player.getUniqueID()))
            return;
        UUID uuid = player.getUniqueID();
        int time = playerCastTimeMap.get(uuid);
        if(time-- > 0) {
            playerAbilityMap.get(uuid).onCastTick(player, Side.CLIENT);
            playerCastTimeMap.put(uuid, time);
            return;
        }
        playerCastTimeMap.remove(uuid);
        playerAbilityMap.remove(uuid);
    }

    public void addCastingPlayer(EntityPlayer player, ICastableAbility ability) {
        playerCastTimeMap.put(player.getUniqueID(), ability.getCastingTickAmount());
        playerAbilityMap.put(player.getUniqueID(), ability);
        ability.onCastingStart(player, Side.CLIENT);
    }

    /**
     * {@link excsi.rekindledarcane.core.transformers.ModelBipedTransformer}
     */
    public static void renderCallback(Entity entity, ModelBiped modelBiped) {
        if(!(entity instanceof EntityPlayer))
            return;

        EntityPlayer player = (EntityPlayer) entity;
        if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 && player == Minecraft.getMinecraft().thePlayer)
            return;

        UUID uuid = player.getUniqueID();
        if(!ClientSkillCastingManager.INSTANCE.playerAbilityMap.containsKey(uuid))
            return;

        ICastableAbility handler = ClientSkillCastingManager.INSTANCE.playerAbilityMap.get(player.getUniqueID());
        float partialTicks = Minecraft.getMinecraft().timer.renderPartialTicks;
        int time = ClientSkillCastingManager.INSTANCE.playerCastTimeMap.get(uuid);
        handler.getAnimation().animateModel(player, modelBiped, handler.getCastingTickAmount() - time, partialTicks);
    }

    public void clearPlayer(EntityPlayer player) {
        UUID uuid = player.getUniqueID();
        playerCastTimeMap.remove(uuid);
        playerAbilityMap.remove(uuid);
    }
}
