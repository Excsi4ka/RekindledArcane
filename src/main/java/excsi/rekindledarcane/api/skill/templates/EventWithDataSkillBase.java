package excsi.rekindledarcane.api.skill.templates;

import cpw.mods.fml.common.FMLCommonHandler;
import excsi.rekindledarcane.api.skill.SkillType;
import excsi.rekindledarcane.api.data.skill.SkillData;
import net.minecraftforge.common.MinecraftForge;

public abstract class EventWithDataSkillBase<DATA extends SkillData> extends AbstractSkillWithData<DATA> {

    public EventWithDataSkillBase(String nameID) {
        super(nameID);
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
    }

    @Override
    public SkillType getSkillType() {
        return SkillType.PASSIVE;
    }
}
