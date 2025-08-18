package excsi.rekindledarcane.common.skill.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class TestEventSkill extends EventSkillBase {

    public TestEventSkill(String nameID) {
        super(nameID);
    }

    @SubscribeEvent
    public void onDamage(LivingHurtEvent event) {
        if(!hasThisSkill(event.entityLiving))
            return;

    }
}
