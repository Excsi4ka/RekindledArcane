package excsi.rekindledarcane.common.skill.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import excsi.rekindledarcane.api.event.ArmorPropertiesCalculateEvent;
import excsi.rekindledarcane.api.skill.templates.EventSkillBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.ISpecialArmor;

public class FortitudeSkill extends EventSkillBase {

    public FortitudeSkill(String nameID) {
        super(nameID);
    }

    @SubscribeEvent
    public void onArmorCalculate(ArmorPropertiesCalculateEvent event) {
        if (!hasThisSkill(event.entityPlayer))
            return;
        EntityPlayer player = event.entityPlayer;
        if (player.getHealth() > (player.getMaxHealth() * 0.5))
            return;
        double boostPercent = 1.25 - (player.getHealth() * 0.5 / player.getMaxHealth());
        if (boostPercent < 1)
            return;
        for (ISpecialArmor.ArmorProperties property : event.properties) {
            property.AbsorbRatio *= boostPercent;
            property.AbsorbMax *= boostPercent;
        }
    }
}
