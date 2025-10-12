package excsi.rekindledarcane.client.util;

import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.api.skill.ICastableAbility;
import excsi.rekindledarcane.common.registry.RekindledArcaneItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

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
        ICastableAbility ability = playerAbilityMap.get(uuid);
        if(time-- > 0) {
            ability.onCastTick(player, ability.getCastingTickAmount() - time, Side.CLIENT);
            playerCastTimeMap.put(uuid, time);
            applySpeedReduction(player, ability.getMovementSpeedMultiplier());
            return;
        }
        playerCastTimeMap.remove(uuid);
        playerAbilityMap.remove(uuid);
    }

    public void startCasting(EntityPlayer player, ICastableAbility ability) {
        playerCastTimeMap.put(player.getUniqueID(), ability.getCastingTickAmount());
        playerAbilityMap.put(player.getUniqueID(), ability);
        ability.onCastingStart(player, Side.CLIENT);
    }

    public void applySpeedReduction(EntityPlayer player, float speedMultiplier) {
        player.motionX *= speedMultiplier;
        player.motionZ *= speedMultiplier;
    }

    public void clearAll() {
        playerCastTimeMap.clear();
        playerAbilityMap.clear();
    }

    public boolean isClientCasting() {
        return playerAbilityMap.containsKey(Minecraft.getMinecraft().thePlayer.getUniqueID());
    }

    public int getClientRemainingCastTime() {
        return playerCastTimeMap.get(Minecraft.getMinecraft().thePlayer.getUniqueID());
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

        ItemStack item = player.getHeldItem();
        if(player.isUsingItem() && item.getItemUseAction() == RekindledArcaneItems.raiseSword) {
            float partialTicks = Minecraft.getMinecraft().timer.renderPartialTicks;
            int time = player.getItemInUseDuration();
            float raising = Math.min(1f, (time + partialTicks) / 10f);
            modelBiped.bipedRightArm.rotateAngleX = -1.75f * raising;
            modelBiped.bipedRightArm.rotateAngleY = -0.5f * raising;
            modelBiped.bipedLeftArm.rotateAngleX = -1.75f * raising;
            modelBiped.bipedLeftArm.rotateAngleY = 0.5f * raising;
        }

        UUID uuid = player.getUniqueID();
        if(!ClientSkillCastingManager.INSTANCE.playerAbilityMap.containsKey(uuid))
            return;

        ICastableAbility handler = ClientSkillCastingManager.INSTANCE.playerAbilityMap.get(uuid);
        float partialTicks = Minecraft.getMinecraft().timer.renderPartialTicks;
        int time = ClientSkillCastingManager.INSTANCE.playerCastTimeMap.get(uuid);
        handler.getAnimation().animateModel(player, modelBiped, handler.getCastingTickAmount() - time, partialTicks);
    }
}
