package excsi.rekindledarcane.api.skill.templates;

import excsi.rekindledarcane.api.skill.SkillType;
import excsi.rekindledarcane.api.data.skill.AbstractData;
import net.minecraftforge.common.MinecraftForge;

public abstract class EventWithDataSkillBase<DATA extends AbstractData> extends AbstractSkillWithData<DATA> {

    public EventWithDataSkillBase(String nameID) {
        super(nameID);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public SkillType getSkillType() {
        return SkillType.ABILITY;
    }
}
