package excsi.rekindledarcane.common.data.skill;

import excsi.rekindledarcane.api.data.skill.SkillData;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class ToggleData extends SkillData {

    private boolean toggled = false;

    public ToggleData(String registryName, boolean sendClientUpdates) {
        super(registryName, sendClientUpdates, false);
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
        compound.setBoolean("toggled", toggled);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        toggled = compound.getBoolean("toggled");
    }

    @Override
    public void writeToBuffer(ByteBuf buf) {
        buf.writeBoolean(toggled);
    }

    @Override
    public void readFromBuffer(ByteBuf buf) {
        toggled = buf.readBoolean();
    }
}
