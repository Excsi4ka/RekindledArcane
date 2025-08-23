package excsi.rekindledarcane.common.skill.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import excsi.rekindledarcane.common.data.skill.templates.BasicSkillData;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class TestEventSkill extends EventWithDataSkillBase<BasicSkillData> {

    public TestEventSkill(String nameID) {
        super(nameID);
    }

    @Override
    public BasicSkillData createDefaultDataInstance() {
        return new BasicSkillData(registryName, true);
    }

    @SubscribeEvent
    public void onDamage(LivingHurtEvent event) {
        if(!hasThisSkill(event.entityLiving))
            return;
        if(event.entityLiving.worldObj.isRemote)
            return;
        BasicSkillData data = getSkillData(event.entityLiving);
        if(data.getSkillCooldown() == 0) {
            data.setSkillCooldown(600);
            event.setCanceled(true);
        }
    }
}
