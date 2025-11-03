package excsi.rekindledarcane.common.skill;

import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.api.skill.ICastableSkill;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;
import java.util.UUID;

public class ServerSkillCastingManager {

    public static ServerSkillCastingManager INSTANCE = new ServerSkillCastingManager();

    private final HashMap<UUID, ICastableSkill> playerAbilityMap;

    private final HashMap<UUID, Integer> playerCastTimeMap;

    private ServerSkillCastingManager() {
        playerAbilityMap = new HashMap<>();
        playerCastTimeMap = new HashMap<>();
    }

    /**
     * Ticked from {@link excsi.rekindledarcane.common.event.RekindledArcaneEvents#onPlayerTick(TickEvent.PlayerTickEvent)}
     */
    public void tick(EntityPlayer player) {
        if(!playerCastTimeMap.containsKey(player.getUniqueID()))
            return;
        UUID uuid = player.getUniqueID();
        int time = playerCastTimeMap.get(uuid);
        ICastableSkill ability = playerAbilityMap.get(uuid);
        if(time-- > 0) {
            ability.onCastTick(player, ability.getCastingTickAmount() - time, Side.SERVER);
            playerCastTimeMap.put(uuid, time);
            applySpeedReduction(player, ability.getMovementSpeedMultiplier());
            return;
        }
        ability.resolveSkillCast(player);
        playerCastTimeMap.remove(uuid);
        playerAbilityMap.remove(uuid);
    }

    public void startCasting(EntityPlayer player, ICastableSkill ability) {
        playerCastTimeMap.put(player.getUniqueID(), ability.getCastingTickAmount());
        playerAbilityMap.put(player.getUniqueID(), ability);
        ability.onCastingStart(player, Side.SERVER);
    }

    public void applySpeedReduction(EntityPlayer player, float speedMultiplier) {
        player.motionX *= speedMultiplier;
        player.motionZ *= speedMultiplier;
    }

    public boolean alreadyCasting(EntityPlayer player) {
        return playerAbilityMap.containsKey(player.getUniqueID());
    }

    public void clearPlayer(EntityPlayer player) {
        UUID uuid = player.getUniqueID();
        playerCastTimeMap.remove(uuid);
        playerAbilityMap.remove(uuid);
    }
}
