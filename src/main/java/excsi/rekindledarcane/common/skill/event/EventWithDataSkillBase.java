package excsi.rekindledarcane.common.skill.event;

import excsi.rekindledarcane.api.skill.SkillType;
import excsi.rekindledarcane.common.data.player.SkillData;
import excsi.rekindledarcane.common.skill.AbstractSkillWithData;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

public abstract class EventWithDataSkillBase<DATA extends SkillData> extends AbstractSkillWithData<DATA> {

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
    public void unlockSkill(EntityPlayer player) {
        super.unlockSkill(player);
    }

    @Override
    public void forgetSkill(EntityPlayer player) {
        super.forgetSkill(player);
    }

    @Override
    public SkillType getSkillType() {
        return SkillType.PASSIVE;
    }

    @Override
    public boolean reapplyOnRestart() {
        return false;
    }
}
