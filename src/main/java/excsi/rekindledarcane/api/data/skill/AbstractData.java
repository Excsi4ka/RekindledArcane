package excsi.rekindledarcane.api.data.skill;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public abstract class AbstractData {

    private final String registryName;

    private final boolean sendClientUpdates;

    private ISkillDataTracker dataTracker;

    public AbstractData(String registryName, boolean sendClientUpdates) {
        this.registryName = registryName;
        this.sendClientUpdates = sendClientUpdates;
    }

    public abstract void writeToNBT(NBTTagCompound compound);

    public abstract void readFromNBT(NBTTagCompound compound);

    public abstract void writeToBuffer(ByteBuf buf);

    public abstract void readFromBuffer(ByteBuf buf);

    public void markChanged() {
        if (!sendClientUpdates) return;
        dataTracker.queueDataToSync(this);
    }

    public String getRegistryName() {
        return registryName;
    }

    public boolean shouldSendClientUpdates() {
        return sendClientUpdates;
    }

    public void setDataTracker(ISkillDataTracker playerData) {
        this.dataTracker = playerData;
    }

    @Override
    public int hashCode() {
        return registryName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractData))
            return false;
        AbstractData data = (AbstractData) obj;
        return data.registryName.equals(this.registryName);
    }
}
