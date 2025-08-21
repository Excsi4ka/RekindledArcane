package excsi.rekindledarcane.common.skill.ability;

import excsi.rekindledarcane.api.skill.SkillType;
import excsi.rekindledarcane.common.data.player.SkillData;
import excsi.rekindledarcane.common.skill.AbstractSkillWithData;
import net.minecraft.nbt.NBTTagCompound;

public class CooldownSkill extends AbstractSkillWithData<SkillData> {

    public CooldownSkill(String nameID) {
        super(nameID);
    }

    @Override
    public SkillType getSkillType() {
        return SkillType.PASSIVE;
    }

    @Override
    public boolean reapplyOnRestart() {
        return false;
    }

    @Override
    public void writeToNBT(SkillData skillData, NBTTagCompound tagCompound) {
        tagCompound.setInteger("cooldown", skillData.getSkillCooldown());
    }

    @Override
    public SkillData readFromNBT(NBTTagCompound compound) {
        SkillData data = createDefaultDataInstance();
        data.setSkillCooldown(compound.getInteger("cooldown"));
        return data;
    }

    @Override
    public SkillData createDefaultDataInstance() {
        return new SkillData();
    }
}
