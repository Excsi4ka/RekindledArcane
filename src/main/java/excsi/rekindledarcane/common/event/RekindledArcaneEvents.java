package excsi.rekindledarcane.common.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import excsi.rekindledarcane.api.RekindledArcaneAPI;
import excsi.rekindledarcane.api.event.PotionApplyEvent;
import excsi.rekindledarcane.common.skill.attribute.PersistentAttributeModifier;
import excsi.rekindledarcane.common.util.Config;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

import java.util.Collection;

public class RekindledArcaneEvents {

    @SubscribeEvent
    public void onEntityCreated(EntityConstructing event) {
        if(event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            player.getAttributeMap().registerAttribute(RekindledArcaneAPI.MAGIC_RESISTANCE);
            player.getAttributeMap().registerAttribute(RekindledArcaneAPI.MAX_SUMMONS);
        }
    }

    @SubscribeEvent
    public void onRespawn(PlayerRespawnEvent event) {
        EntityPlayer player = event.player;
        if(Config.shouldHealPlayerOnRespawn && !player.worldObj.isRemote) {
            player.setHealth(player.getMaxHealth());
        }
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if(event.entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            double value = player.getEntityAttribute(RekindledArcaneAPI.MAGIC_RESISTANCE).getAttributeValue();
            value = Math.min(Config.magicResistanceCap, value);
            if(!event.source.isMagicDamage())
                return;
            event.ammount *= 1 - value / 100;
        }
    }

    @SubscribeEvent
    public void onPotionApply(PotionApplyEvent event) {
        //event.setCanceled(true);
    }

    //reapply persistent attribute modifiers on respawn
    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public void onPlayerClone(Clone event) {
        if(!event.wasDeath) return;

        Collection<IAttributeInstance> attributeInstanceList = event.original.getAttributeMap().getAllAttributes();
        attributeInstanceList.forEach(attributeInstance -> {
            IAttributeInstance newAttribInstance = event.entityPlayer.getEntityAttribute(attributeInstance.getAttribute());
            Collection<AttributeModifier> oldModifiers = attributeInstance.func_111122_c();
            oldModifiers.forEach(oldAttribModifier -> {
                if(oldAttribModifier instanceof PersistentAttributeModifier) {
                    newAttribInstance.applyModifier(new PersistentAttributeModifier(oldAttribModifier.getID(),
                            oldAttribModifier.getName(), oldAttribModifier.getAmount(), oldAttribModifier.getOperation()));
                    return;
                }
                if(oldAttribModifier.getName().startsWith("PersistentModifier:")) {
                    newAttribInstance.applyModifier(new PersistentAttributeModifier(oldAttribModifier.getID(),
                            oldAttribModifier.getName(), oldAttribModifier.getAmount(), oldAttribModifier.getOperation()));
                }
            });
        });
    }
}
