package excsi.rekindledarcane.common.data.skill;

import excsi.rekindledarcane.common.data.skill.templates.AbstractData;
import excsi.rekindledarcane.common.data.skill.templates.ITickingData;
import excsi.rekindledarcane.common.network.PacketManager;
import excsi.rekindledarcane.common.network.server.ServerPacketSyncTrackingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class SkillDataTracker {

    public HashMap<String, AbstractData> trackingData;

    public List<AbstractData> dataQueuedToSync;

    public List<ITickingData> tickingData;

    public SkillDataTracker() {
        this.trackingData = new HashMap<>();
        this.dataQueuedToSync = new ArrayList<>();
        this.tickingData = new ArrayList<>();
    }

    public void addDataToTracker(AbstractData data) {
        if(data.needsClientUpdates()) {
            trackingData.put(data.getRegistryName(), data);
            data.setDataTracker(this);
        }
        if(data instanceof ITickingData) {
            tickingData.add((ITickingData) data);
        }
    }

    public void stopTrackingData(AbstractData data) {
        trackingData.remove(data.getRegistryName());
        tickingData.remove(data);
    }

    public void queueDataToSync(AbstractData data) {
        dataQueuedToSync.add(data);
    }

    public void tick(EntityPlayer player) {
        for (ITickingData tickable : tickingData) {
            if(!tickable.shouldTick())
                continue;
            tickable.tick();
        }
        if(dataQueuedToSync.isEmpty())
            return;
        ServerPacketSyncTrackingData packetSyncTrackingData = new ServerPacketSyncTrackingData(createPacketData());
        PacketManager.sendToPlayer(packetSyncTrackingData, player);
        dataQueuedToSync.clear();
    }

    public NBTTagCompound createPacketData() {
        NBTTagCompound compound = new NBTTagCompound();
        trackingData.forEach((key, data) -> {
            NBTTagCompound dataNbt = new NBTTagCompound();
            data.writeToNBT(dataNbt);
            compound.setTag(key, dataNbt);
        });
        return compound;
    }

    @SuppressWarnings("unchecked")
    public void readPacketData(NBTTagCompound compound) {
        Set<String> keys = (Set<String>) compound.func_150296_c();
        keys.forEach(key -> {
            NBTTagCompound tagCompound = compound.getCompoundTag(key);
            AbstractData data = trackingData.get(key);
            data.readFromNBT(tagCompound);
        });
    }
}
