package excsi.rekindledarcane.common.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.api.RekindledArcaneAPI;
import excsi.rekindledarcane.api.event.ChangeHeldItemEvent;
import excsi.rekindledarcane.common.skill.ServerSkillCastingManager;
import excsi.rekindledarcane.common.skill.attribute.PersistentAttributeModifier;
import excsi.rekindledarcane.common.util.RekindledArcaneConfig;
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
        if (event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            player.getAttributeMap().registerAttribute(RekindledArcaneAPI.MAGIC_RESISTANCE);
            player.getAttributeMap().registerAttribute(RekindledArcaneAPI.MAX_SUMMONS);
            player.getAttributeMap().registerAttribute(RekindledArcaneAPI.SPELL_POWER);
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
    public void onItemChange(ChangeHeldItemEvent event) {
        System.out.println(event.equippedStack);
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (event.entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            double value = player.getEntityAttribute(RekindledArcaneAPI.MAGIC_RESISTANCE).getAttributeValue();
            value = Math.min(RekindledArcaneConfig.magicResistanceCap, value);
            if (!event.source.isMagicDamage())
                return;
            event.ammount *= 1 - value / 100;
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.side == Side.CLIENT)
            return;
        if(event.phase == TickEvent.Phase.START)
            return;
        ServerSkillCastingManager.INSTANCE.tick(event.player);
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
