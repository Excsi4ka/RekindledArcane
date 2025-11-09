package excsi.rekindledarcane.common.data.skill;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class CounterAndCooldownData extends CooldownData {

    private int counter = 0;

    public CounterAndCooldownData(String regName, int updateFrequency, boolean needsClientUpdates) {
        super(regName, updateFrequency, needsClientUpdates);
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
        super.readFromNBT(compound);
        counter = compound.getInteger("counter");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("counter", counter);
    }

    @Override
    public void writeToBuffer(ByteBuf buf) {
        super.writeToBuffer(buf);
        buf.writeInt(counter);
    }

    @Override
    public void readFromBuffer(ByteBuf buf) {
        super.readFromBuffer(buf);
        counter = buf.readInt();
    }
}
