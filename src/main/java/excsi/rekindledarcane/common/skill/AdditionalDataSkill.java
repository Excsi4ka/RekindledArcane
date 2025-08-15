package excsi.rekindledarcane.common.skill;

import net.minecraft.nbt.NBTTagCompound;

public abstract class AdditionalDataSkill extends BaseAbstractSkill {

    public String registryName;

    public AdditionalDataSkill(String nameID) {
        super(nameID);
        this.registryName = getSkillCategory().getNameID() + getNameID();
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

    public abstract NBTTagCompound writeToNBT();

    public abstract void readFromNBT(NBTTagCompound compound);

}
