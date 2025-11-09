package excsi.rekindledarcane.common.data.skill;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class ToggleAndCounterData extends CounterData {

    private boolean toggled = false;

    public ToggleAndCounterData(String registryName, boolean sendClientUpdates) {
        super(registryName, sendClientUpdates);
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
        super.writeToNBT(compound);
        compound.setBoolean("toggled", toggled);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        toggled = compound.getBoolean("toggled");
    }

    @Override
    public void writeToBuffer(ByteBuf buf) {
        super.writeToBuffer(buf);
        buf.writeBoolean(toggled);
    }

    @Override
    public void readFromBuffer(ByteBuf buf) {
        super.readFromBuffer(buf);
        toggled = buf.readBoolean();
    }
}
