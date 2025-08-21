package excsi.rekindledarcane.common.skill.ability;

import excsi.rekindledarcane.api.skill.SkillType;
import excsi.rekindledarcane.common.data.player.ToggleableData;
import excsi.rekindledarcane.common.skill.AbstractSkillWithData;
import net.minecraft.nbt.NBTTagCompound;

public class ToggleableSkill extends AbstractSkillWithData<ToggleableData> {

    public ToggleableSkill(String nameID) {
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
    public void writeToNBT(ToggleableData skillData, NBTTagCompound tagCompound) {
        tagCompound.setBoolean("toggled", skillData.toggled);
    }

    @Override
    public ToggleableData readFromNBT(NBTTagCompound compound) {
        ToggleableData data = createDefaultDataInstance();
        data.toggled = compound.getBoolean("toggled");
        return data;
    }

    @Override
    public ToggleableData createDefaultDataInstance() {
        return new ToggleableData();
    }
}
