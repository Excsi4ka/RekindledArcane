package excsi.rekindledarcane.common.skill.ability;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import excsi.rekindledarcane.api.skill.templates.InstantAbilitySkillBase;
import excsi.rekindledarcane.common.data.skill.ToggleAndCooldownData;
import excsi.rekindledarcane.common.entity.projectile.TracedArrowProjectile;
import excsi.rekindledarcane.common.skill.ability.ChargedShotSkill.ChargedShotData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;

import java.util.List;

public class ChargedShotSkill extends InstantAbilitySkillBase<ChargedShotData> {

    public ChargedShotSkill(String nameID) {
        super(nameID, true);
    }

    @Override
    public void addDescription(List<String> description) {
        super.addDescription(description);
        description.add(StatCollector.translateToLocal("rekindledarcane.charged.shot.skill"));
    }

    @Override
    public ChargedShotData createDefaultDataInstance() {
        return new ChargedShotData(getRegistryName(), 1);
    }

    @Override
    public boolean canUse(EntityPlayer player) {
        if(!hasThisSkill(player))
            return false;
        ChargedShotData data = getSkillData(player);
        return !data.isToggled() && data.getSkillCooldown() == 0;
    }

    @Override
    public void resolveSkillCast(EntityPlayer player) {
        getSkillData(player).setToggled(true);
    }

    @SubscribeEvent
    public void onBowShot(ArrowLooseEvent event) {
        EntityPlayer player = event.entityPlayer;
        if (player.worldObj.isRemote)
            return;
        if (!hasThisSkill(event.entityPlayer))
            return;
        ChargedShotData data = getSkillData(player);
        if (!data.isToggled())
            return;
        if (data.getChargedTick() < 100) {
            event.setCanceled(true);
        } else {
            event.setCanceled(true);
            TracedArrowProjectile projectile = new TracedArrowProjectile(player.worldObj, player, 3f);
            projectile.setDamage(100);
            player.worldObj.spawnEntityInWorld(projectile);
        }
        data.setSkillCooldown(20);
        data.setToggled(false);
        data.resetChargedTick();
    }

    @SubscribeEvent
    public void onUseTick(PlayerUseItemEvent.Tick event) {
        EntityPlayer player = event.entityPlayer;
        if (player.worldObj.isRemote)
            return;
        if (!(event.item.getItem() instanceof ItemBow))
            return;
        if (!hasThisSkill(event.entityPlayer))
            return;
        ChargedShotData data = getSkillData(player);
        if (!data.isToggled())
            return;
        data.incrementTick();
        if (data.getChargedTick() == 100)
            player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "rekindledarcane:menu.ding", 1f, 1f);
    }

    public static class ChargedShotData extends ToggleAndCooldownData {

        private int chargedTick = 0;

        public ChargedShotData(String regName, int updateFrequency) {
            super(regName, updateFrequency);
        }

        public void incrementTick() {
            chargedTick++;
        }

        public int getChargedTick() {
            return chargedTick;
        }

        public void resetChargedTick() {
            chargedTick = 0;
        }
    }
}
