package excsi.rekindledarcane.common.skill.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.api.skill.IToggleSwitch;
import excsi.rekindledarcane.api.skill.templates.EventWithDataSkillBase;
import excsi.rekindledarcane.common.data.skill.ToggleAndCounterData;
import excsi.rekindledarcane.common.skill.event.ShadowVeilSkill.ShadowVeilData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class ShadowVeilSkill extends EventWithDataSkillBase<ShadowVeilData> implements IToggleSwitch {

    public ShadowVeilSkill(String nameID) {
        super(nameID);
    }

    @Override
    public ShadowVeilData createDefaultDataInstance() {
        ShadowVeilData data = new ShadowVeilData(getRegistryName(), false);
        data.setToggled(true);
        return data;
    }

    @Override
    public void toggle(EntityPlayer player, boolean toggled) {
        getSkillData(player).setToggled(toggled);
    }

    @Override
    public boolean isToggled(EntityPlayer player) {
        return getSkillData(player).isToggled();
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START || event.side == Side.CLIENT)
            return;
        EntityPlayer player = event.player;
        if (!hasThisSkill(player))
            return;
        ShadowVeilData data = getSkillData(player);
        if (!data.isToggled())
            return;
        if (data.setPos(player.posX, player.posY, player.posZ)) {
            data.resetCounter();
        } else {
            data.incrementCounter();
            if (player.ticksExisted % 20 == 0 && data.getCounter() > 80)
                player.addPotionEffect(new PotionEffect(Potion.invisibility.getId(), 21));
        }
    }

    public static class ShadowVeilData extends ToggleAndCounterData {

        private double x,y,z;

        public ShadowVeilData(String registryName, boolean sendClientUpdates) {
            super(registryName, sendClientUpdates);
        }

        public boolean setPos(double x, double y, double z) {
            boolean different = false;
            if (this.x != x) {
                this.x = x;
                different = true;
            }
            if (this.y != y) {
                this.y = y;
                different = true;
            }
            if (this.z != z) {
                this.z = z;
                different = true;
            }
            return different;
        }
    }
}
