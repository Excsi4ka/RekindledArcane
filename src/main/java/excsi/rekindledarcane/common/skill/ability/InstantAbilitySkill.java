package excsi.rekindledarcane.common.skill.ability;

import excsi.rekindledarcane.api.skill.IActiveAbilitySkill;
import excsi.rekindledarcane.api.skill.SkillType;
import excsi.rekindledarcane.common.data.skill.AbstractData;
import excsi.rekindledarcane.common.skill.AbstractSkillWithData;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public abstract class InstantAbilitySkill<DATA extends AbstractData> extends AbstractSkillWithData<DATA> implements IActiveAbilitySkill {

    public InstantAbilitySkill(String nameID) {
        super(nameID);
    }

    public boolean hasThisSkill(EntityLivingBase entityLivingBase) {
        if(!(entityLivingBase instanceof EntityPlayer))
            return false;
        return skillPlayerToDataMap.containsKey(entityLivingBase.getUniqueID());
    }

    @Override
    public SkillType getSkillType() {
        return SkillType.ABILITY;
    }
}
