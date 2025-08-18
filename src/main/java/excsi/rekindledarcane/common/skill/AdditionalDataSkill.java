package excsi.rekindledarcane.common.skill;

import excsi.rekindledarcane.api.skill.ISkill;
import excsi.rekindledarcane.api.skill.ISkillCategory;
import net.minecraft.nbt.NBTTagCompound;

public abstract class AdditionalDataSkill extends AbstractSkill {

    public String registryName;

    public AdditionalDataSkill(String nameID) {
        super(nameID);
    }

    @Override
    public ISkill setSkillCategory(ISkillCategory skillCategory) {
        this.registryName = skillCategory.getNameID() + ":" + getNameID();
        return super.setSkillCategory(skillCategory);
    }

    public void writeDataInternal(NBTTagCompound compound) {
        NBTTagCompound data = writeToNBT();
        if(data != null) {
            compound.setTag(registryName, data);
        }
    }
    //todo change thuis
    public void readDataInternal(NBTTagCompound compound) {
        readFromNBT(compound.getCompoundTag(registryName));
    }

    public abstract boolean hasData();

    public abstract NBTTagCompound writeToNBT();

    public abstract void readFromNBT(NBTTagCompound compound);

}
