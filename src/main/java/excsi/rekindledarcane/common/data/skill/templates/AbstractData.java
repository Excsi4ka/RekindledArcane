package excsi.rekindledarcane.common.data.skill.templates;

import excsi.rekindledarcane.common.data.skill.SkillDataTracker;
import net.minecraft.nbt.NBTTagCompound;

public abstract class AbstractData {

    private final String registryName;

    private SkillDataTracker dataTracker;

    private final boolean sendClientUpdates;

    public AbstractData(String registryName, boolean sendClientUpdates) {
        this.registryName = registryName;
        this.sendClientUpdates = sendClientUpdates;
    }

    public abstract boolean needsClientSync();

    public abstract void writeToNBT(NBTTagCompound compound);

    public abstract void readFromNBT(NBTTagCompound compound);

    public void markChanged() {
        if(!sendClientUpdates || !needsClientSync())
            return;
        dataTracker.queueDataToSync(this);
    }

    public String getRegistryName() {
        return registryName;
    }

    public void setDataTracker(SkillDataTracker tracker) {
        this.dataTracker = tracker;
    }

    public boolean needsClientUpdates() {
        return sendClientUpdates;
    }

    @Override
    public int hashCode() {
        return registryName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof AbstractData))
            return false;
        AbstractData data = (AbstractData) obj;
        return data.getRegistryName().equals(this.registryName);
    }
}
