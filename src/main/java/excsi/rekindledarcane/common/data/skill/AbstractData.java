package excsi.rekindledarcane.common.data.skill;

import excsi.rekindledarcane.common.data.player.PlayerData;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public abstract class AbstractData {

    public String registryName;

    public boolean sendClientUpdates;

    public PlayerData playerData;

    public AbstractData(String registryName, boolean sendClientUpdates) {
        this.registryName = registryName;
        this.sendClientUpdates = sendClientUpdates;
    }

    public abstract void writeToNBT(NBTTagCompound compound);

    public abstract void readFromNBT(NBTTagCompound compound);

    public abstract void writeToBuffer(ByteBuf buf);

    public abstract void readFromBuffer(ByteBuf buf);

    public void markChanged() {
        if(!sendClientUpdates)
            return;
        playerData.queueDataToSync(this);
    }

    public void setDataTracker(PlayerData playerData) {
        this.playerData = playerData;
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
        return data.registryName.equals(this.registryName);
    }
}
