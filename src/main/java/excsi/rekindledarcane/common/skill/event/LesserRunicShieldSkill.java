package excsi.rekindledarcane.common.skill.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import excsi.rekindledarcane.api.event.RunicShieldAmountEvent;

public class LesserRunicShieldSkill extends EventSkillBase {

    public LesserRunicShieldSkill(String nameID) {
        super(nameID);
    }

    @SubscribeEvent
    public void onRunicShield(RunicShieldAmountEvent event) {
        if(!hasThisSkill(event.entityPlayer))
            return;
        event.runicShieldAmount += 10;
    }
}
