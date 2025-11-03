package excsi.rekindledarcane.api.skill.templates;

import excsi.rekindledarcane.api.skill.ICastableSkill;
import excsi.rekindledarcane.api.client.ISkillCastAnimation;
import excsi.rekindledarcane.api.skill.SkillType;
import excsi.rekindledarcane.api.data.skill.SkillData;

public abstract class CastableAbilitySkillBase<DATA extends SkillData> extends AbstractSkillWithData<DATA> implements ICastableSkill {

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
