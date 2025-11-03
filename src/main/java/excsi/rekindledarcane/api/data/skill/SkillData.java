package excsi.rekindledarcane.api.data.skill;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public abstract class SkillData {

    private final String registryName; // Usually CategoryID:SkillID

    private final boolean sendClientUpdates;

    private final boolean isTickingData;

    private IDataTracker dataTracker;

    public SkillData(String registryName, boolean sendClientUpdates, boolean shouldTick) {
        this.registryName = registryName;
        this.sendClientUpdates = sendClientUpdates;
        this.isTickingData = shouldTick;
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

    public boolean isTickingData() {
        return isTickingData;
    }

    public boolean readyToTick() {
        return false;
    }

    public void tick() {}

    public void setDataTracker(IDataTracker playerData) {
        this.dataTracker = playerData;
    }

    @Override
    public int hashCode() {
        return registryName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SkillData))
            return false;
        SkillData data = (SkillData) obj;
        return data.registryName.equals(this.registryName);
    }
}
