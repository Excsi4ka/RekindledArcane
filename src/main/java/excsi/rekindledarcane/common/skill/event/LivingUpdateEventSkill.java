package excsi.rekindledarcane.common.skill.event;

import excsi.rekindledarcane.api.skill.ISkill;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class LivingUpdateEventSkill extends EventBasedSkill<LivingHurtEvent> {

    public LivingUpdateEventSkill(String nameID) {
        super(nameID);
    }

    @Override
    public ISkill getPrerequisite() {
        return null;
    }

    @Override
    public ISkill getAntiRequisite() {
        return null;
    }

    @Override
    public void processEvent(LivingHurtEvent event) {

    }
}
