package excsi.rekindledarcane.common.skill.ability;

import excsi.rekindledarcane.api.skill.IActiveSkillAbility;
import excsi.rekindledarcane.common.data.skill.AbstractData;
import excsi.rekindledarcane.common.skill.AbstractSkillWithData;

public abstract class AbilitySkillBase<DATA extends AbstractData> extends AbstractSkillWithData<DATA> implements IActiveSkillAbility {

    public AbilitySkillBase(String nameID) {
        super(nameID);
    }
}
