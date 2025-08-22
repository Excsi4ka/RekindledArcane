package excsi.rekindledarcane.common.data.skill.templates;

import net.minecraft.nbt.NBTTagCompound;

public class BasicSkillData extends AbstractData implements ITickingData {

    private int skillCooldown;

    public BasicSkillData(String regName, boolean needsClientUpdates) {
        super(regName, needsClientUpdates);
        skillCooldown = 0;
    }

    @Override
    public boolean needsClientSync() {
        return skillCooldown % 10 == 0;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        compound.setInteger("cooldown", skillCooldown);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        skillCooldown = compound.getInteger("cooldown");
    }

    @Override
    public boolean shouldTick() {
        return skillCooldown > 0;
    }

    @Override
    public void tick() {
        markChanged();
        skillCooldown--;
    }

    public int getSkillCooldown() {
        return skillCooldown;
    }

    public void setSkillCooldown(int skillCooldown) {
        markChanged();
        this.skillCooldown = skillCooldown;
    }

}
