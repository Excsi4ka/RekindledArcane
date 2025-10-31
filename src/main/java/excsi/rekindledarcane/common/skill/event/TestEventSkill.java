package excsi.rekindledarcane.common.skill.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import excsi.rekindledarcane.common.data.skill.CooldownData;
import excsi.rekindledarcane.api.skill.templates.EventWithDataSkillBase;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class TestEventSkill extends EventWithDataSkillBase<CooldownData> {

    public TestEventSkill(String nameID) {
        super(nameID);
    }

    @Override
    public CooldownData createDefaultDataInstance() {
        return new CooldownData(getRegistryName(), 5, false);
    }

    @SubscribeEvent
    public void onDamage(LivingHurtEvent event) {
        if(!hasThisSkill(event.entityLiving))
            return;
        if(event.entityLiving.worldObj.isRemote)
            return;
        CooldownData data = getSkillData(event.entityLiving);
        if(data.getSkillCooldown() == 0) {
            data.setSkillCooldown(600);
            event.setCanceled(true);
        }
    }
}
