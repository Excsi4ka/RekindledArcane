package excsi.rekindledarcane.common.data.skill;

import excsi.rekindledarcane.common.data.ITickable;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class ToggleAndCooldownData extends AbstractData implements ITickable {

    private int skillCooldown;

    private final int updateFrequency;

    private boolean toggled = false;

    public ToggleAndCooldownData(String regName, int updateFrequency) {
        super(regName, true);
        this.skillCooldown = 0;
        this.updateFrequency = updateFrequency;
    }

    public int getSkillCooldown() {
        return skillCooldown;
    }

    public void setSkillCooldown(int skillCooldown) {
        markChanged();
        this.skillCooldown = skillCooldown;
    }

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        markChanged();
        this.toggled = toggled;
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
    public void writeToBuffer(ByteBuf buf) {
        buf.writeInt(skillCooldown);
        buf.writeBoolean(toggled);
    }

    @Override
    public void readFromBuffer(ByteBuf buf) {
        skillCooldown = buf.readInt();
        toggled = buf.readBoolean();
    }

    @Override
    public boolean shouldTick() {
        return skillCooldown > 0;
    }

    @Override
    public void tick() {
        if(skillCooldown-- % updateFrequency == 0) markChanged();
    }
}
