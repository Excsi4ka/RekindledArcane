package excsi.rekindledarcane.api.skill.templates;

import excsi.rekindledarcane.api.skill.IActiveAbilitySkill;
import excsi.rekindledarcane.api.skill.SkillType;
import excsi.rekindledarcane.api.data.skill.SkillData;
import net.minecraft.util.StatCollector;

import java.util.List;

public abstract class InstantAbilitySkillBase<DATA extends SkillData> extends AbstractSkillWithData<DATA> implements IActiveAbilitySkill {

    public InstantAbilitySkillBase(String nameID) {
        super(nameID);
    }

    @Override
    public void addDescription(List<String> description) {
        super.addDescription(description);
        description.add(StatCollector.translateToLocal("rekindledarcane.skill.equip"));
    }

    @Override
    public SkillType getSkillType() {
        return SkillType.ABILITY;
    }
}
