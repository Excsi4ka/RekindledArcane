package excsi.rekindledarcane.common.skill.ability;

import excsi.rekindledarcane.api.skill.SkillType;
import excsi.rekindledarcane.common.data.skill.BasicSkillData;
import excsi.rekindledarcane.common.skill.AbstractSkillWithData;
import net.minecraft.nbt.NBTTagCompound;

public class CooldownSkill extends AbstractSkillWithData<BasicSkillData> {

    public CooldownSkill(String nameID) {
        super(nameID);
    }

    @Override
    public SkillType getSkillType() {
        return SkillType.PASSIVE;
    }

    @Override
    public void writeToNBT(BasicSkillData skillData, NBTTagCompound tagCompound) {
        tagCompound.setInteger("cooldown", skillData.getSkillCooldown());
    }

    @Override
    public BasicSkillData readFromNBT(NBTTagCompound compound) {
        BasicSkillData data = createDefaultDataInstance();
        data.setSkillCooldown(compound.getInteger("cooldown"));
        return data;
    }

    @Override
    public BasicSkillData createDefaultDataInstance() {
        return new BasicSkillData();
    }
}
