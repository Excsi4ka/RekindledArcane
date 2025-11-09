package excsi.rekindledarcane.common.data.skill;

import excsi.rekindledarcane.api.data.skill.SkillData;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class CounterData extends SkillData {

    private int counter = 0;

    public CounterData(String registryName, boolean sendClientUpdates) {
        super(registryName, sendClientUpdates, false);
    }

    public void incrementCounter() {
        markChanged();
        counter++;
    }

    public void resetCounter() {
        markChanged();
        counter = 0;
    }

    public int getCounter() {
        return counter;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        counter = compound.getInteger("counter");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        compound.setInteger("counter", counter);
    }

    @Override
    public void writeToBuffer(ByteBuf buf) {
        buf.writeInt(counter);
    }

    @Override
    public void readFromBuffer(ByteBuf buf) {
        counter = buf.readInt();
    }
}
