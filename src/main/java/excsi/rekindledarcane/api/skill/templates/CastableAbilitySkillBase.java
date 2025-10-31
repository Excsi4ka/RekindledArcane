package excsi.rekindledarcane.api.skill.templates;

import excsi.rekindledarcane.api.skill.ICastableAbility;
import excsi.rekindledarcane.api.skill.ISkillCastAnimation;
import excsi.rekindledarcane.api.skill.SkillType;
import excsi.rekindledarcane.api.data.skill.AbstractData;

public abstract class CastableAbilitySkillBase<DATA extends AbstractData> extends AbstractSkillWithData<DATA> implements ICastableAbility {

    public CastableAbilitySkillBase(String nameID) {
        super(nameID);
    }

    @Override
    public float getMovementSpeedMultiplier() {
        return 1f;
    }

    @Override
    public ISkillCastAnimation getAnimation() {
        return ISkillCastAnimation.NO_OP;
    }

    @Override
    public SkillType getSkillType() {
        return SkillType.ABILITY;
    }
}
