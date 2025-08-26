package excsi.rekindledarcane.common.skill.event;

import excsi.rekindledarcane.api.skill.SkillType;
import excsi.rekindledarcane.common.data.skill.AbstractData;
import excsi.rekindledarcane.common.skill.AbstractSkillWithData;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

public abstract class EventWithDataSkillBase<DATA extends AbstractData> extends AbstractSkillWithData<DATA> {

    public EventWithDataSkillBase(String nameID) {
        super(nameID);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public boolean hasThisSkill(EntityLivingBase entityLivingBase) {
        if(!(entityLivingBase instanceof EntityPlayer))
            return false;
        return skillPlayerToDataMap.containsKey(entityLivingBase.getUniqueID());
    }

    @Override
    public SkillType getSkillType() {
        return SkillType.PASSIVE;
    }
}
