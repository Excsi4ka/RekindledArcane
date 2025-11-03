package excsi.rekindledarcane.api.skill.templates;

import excsi.rekindledarcane.api.skill.IActiveAbilitySkill;
import excsi.rekindledarcane.api.skill.SkillType;
import excsi.rekindledarcane.api.data.skill.SkillData;

public abstract class InstantAbilitySkillBase<DATA extends SkillData> extends AbstractSkillWithData<DATA> implements IActiveAbilitySkill {

    public InstantAbilitySkillBase(String nameID) {
        super(nameID);
    }

    @Override
    public SkillType getSkillType() {
        return SkillType.ABILITY;
    }
}
