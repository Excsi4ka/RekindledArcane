package excsi.rekindledarcane.common.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.api.RekindledArcaneAPI;
import excsi.rekindledarcane.common.registry.RekindledArcaneEffects;
import excsi.rekindledarcane.common.skill.ServerSkillManager;
import excsi.rekindledarcane.common.skill.attribute.PersistentAttributeModifier;
import excsi.rekindledarcane.common.util.RekindledArcaneConfig;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

import java.util.Collection;

public class RekindledArcaneEvents {

    @SubscribeEvent
    public void onEntityCreated(EntityConstructing event) {
        if (event.entity instanceof EntityPlayer) {
            BaseAttributeMap map = ((EntityPlayer)event.entity).getAttributeMap();
            map.registerAttribute(RekindledArcaneAPI.MAGIC_RESISTANCE);
            map.registerAttribute(RekindledArcaneAPI.MAX_SUMMONS);
            map.registerAttribute(RekindledArcaneAPI.SPELL_POWER);
            map.registerAttribute(RekindledArcaneAPI.REACH_DISTANCE);
            map.registerAttribute(RekindledArcaneAPI.SUMMONING_STRENGTH);
            map.registerAttribute(RekindledArcaneAPI.DODGE_CHANCE);
            map.registerAttribute(RekindledArcaneAPI.CRIT_CHANCE);
            map.registerAttribute(RekindledArcaneAPI.CRIT_DAMAGE);
        }
    }

    @SubscribeEvent
    public void onRespawn(PlayerRespawnEvent event) {
        EntityPlayer player = event.player;
        if (RekindledArcaneConfig.shouldHealPlayerOnRespawn && !player.worldObj.isRemote) {
            player.setHealth(player.getMaxHealth());
        }
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (event.entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            if (!player.worldObj.isRemote)
                return;
            double value = player.getEntityAttribute(RekindledArcaneAPI.MAGIC_RESISTANCE).getAttributeValue() - 1;
            value = Math.min(RekindledArcaneConfig.magicResistanceCap / 100d, value);
            if (!event.source.isMagicDamage())
                return;
            event.ammount *= 1 - value;
            if(event.ammount <= 0) event.setCanceled(true);
        }
        if (event.entityLiving.isPotionActive(RekindledArcaneEffects.frailPotion)) {
            PotionEffect effect = event.entityLiving.getActivePotionEffect(RekindledArcaneEffects.frailPotion);
            event.ammount *= 1.2 + 0.2 * effect.getAmplifier();
        }
    }

    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event) {
        if (event.entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            if (!player.worldObj.isRemote)
                return;
            double value = player.getEntityAttribute(RekindledArcaneAPI.DODGE_CHANCE).getAttributeValue() - 1;
            value = Math.min(RekindledArcaneConfig.dodgeChanceCap / 100d, value);
            if (Math.random() < value)
                event.setCanceled(true);
            //play sound
        }
    }

    public static double handleEntityInteractReachSquared(EntityPlayer player, double currentReachDistance) {
        double val = player.getEntityAttribute(RekindledArcaneAPI.REACH_DISTANCE).getAttributeValue();
        return currentReachDistance + val * val;
    }

    public static double handleEntityInteract(double currentReachDistance, EntityPlayer player) {
        return currentReachDistance + player.getEntityAttribute(RekindledArcaneAPI.REACH_DISTANCE).getAttributeValue();
    }

    public static boolean isCriticalHit(boolean defaultCrit, EntityPlayer player) {
        float chanceBoost = defaultCrit ? 0.1f : 0;
        return Math.random() <= player.getEntityAttribute(RekindledArcaneAPI.CRIT_CHANCE).getAttributeValue() + chanceBoost - 1;
    }

    //0.5 is to account for the default mc crit boost
    public static float handleCritDamage(EntityPlayer player, float damage) {
        return (float) (damage * (0.5 + player.getEntityAttribute(RekindledArcaneAPI.CRIT_DAMAGE).getAttributeValue()));
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.side == Side.CLIENT)
            return;
        if(event.phase == TickEvent.Phase.START)
            return;
        ServerSkillManager.INSTANCE.tick(event.player);
    }

    //reapply persistent attribute modifiers on respawn
    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public void onPlayerClone(Clone event) {
        if (!event.wasDeath) return;

        Collection<IAttributeInstance> attributeInstanceList = event.original.getAttributeMap().getAllAttributes();
        attributeInstanceList.forEach(attributeInstance -> {
            IAttributeInstance newAttribInstance = event.entityPlayer.getEntityAttribute(attributeInstance.getAttribute());
            Collection<AttributeModifier> oldModifiers = attributeInstance.func_111122_c();
            oldModifiers.forEach(oldAttribModifier -> {
                if (oldAttribModifier instanceof PersistentAttributeModifier) {
                    newAttribInstance.applyModifier(new PersistentAttributeModifier(oldAttribModifier.getID(),
                            oldAttribModifier.getName(), oldAttribModifier.getAmount(), oldAttribModifier.getOperation()));
                    return;
                }
                if (oldAttribModifier.getName().startsWith("PersistentModifier:")) {
                    newAttribInstance.applyModifier(new PersistentAttributeModifier(oldAttribModifier.getID(),
                            oldAttribModifier.getName(), oldAttribModifier.getAmount(), oldAttribModifier.getOperation()));
                }
            });
        });
    }
}
