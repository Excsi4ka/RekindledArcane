package excsi.rekindledarcane.common.data.skill;

import excsi.rekindledarcane.common.data.player.ITickable;
import excsi.rekindledarcane.common.data.skill.AbstractData;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class CooldownData extends AbstractData implements ITickable {

    private int skillCooldown;

    private final int updateFrequency;

    public CooldownData(String regName, int updateFrequency, boolean needsClientUpdates) {
        super(regName, needsClientUpdates);
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

    @Override
    public boolean readyToSync() {
        return skillCooldown % updateFrequency == 0;
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
    }

    @Override
    public void readFromBuffer(ByteBuf buf) {
        skillCooldown = buf.readInt();
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
}
