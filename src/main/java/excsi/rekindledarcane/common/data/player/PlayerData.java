package excsi.rekindledarcane.common.data.player;

import excsi.rekindledarcane.api.RekindledArcaneAPI;
import excsi.rekindledarcane.api.skill.ISkill;
import excsi.rekindledarcane.api.skill.ISkillCategory;
import excsi.rekindledarcane.common.data.skill.AbstractData;
import excsi.rekindledarcane.common.network.PacketManager;
import excsi.rekindledarcane.common.network.server.ServerPacketForgetSkill;
import excsi.rekindledarcane.common.network.server.ServerPacketSyncSkillPoints;
import excsi.rekindledarcane.common.network.server.ServerPacketSyncTrackingData;
import excsi.rekindledarcane.common.network.server.ServerPacketUnlockSkill;
import excsi.rekindledarcane.common.skill.AbstractSkillWithData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlayerData {

    private int skillPoints, unlockedSkillsCount;

    private final HashMap<ISkillCategory, Set<ISkill>> unlockedSkills = new HashMap<>();

    //-------- For data tracking and updating --------
    public HashMap<String, AbstractData> trackingData = new HashMap<>();

    public List<AbstractData> dataQueuedToSync = new ArrayList<>();

    public List<ITickable> tickingStuff = new ArrayList<>();
    //----------------------------------------------------

    public PlayerData() {
        this.skillPoints = 0;
        this.unlockedSkillsCount = 0;
        RekindledArcaneAPI.getAllCategories().forEach(category -> unlockedSkills.put(category, new HashSet<>()));
    }

    public void readData(NBTTagCompound compound, EntityPlayer player) {
        skillPoints = compound.getInteger("skillPoints");
        unlockedSkillsCount = compound.getInteger("unlockedSkillsCount");
        unlockedSkills.forEach((category, skills) -> {
            if(compound.hasKey(category.getNameID())) {
                readSkillsFromNBT(player, category, skills, compound);
            }
        });
    }

    public void writeData(NBTTagCompound compound, EntityPlayer player) {
        compound.setInteger("skillPoints", skillPoints);
        compound.setInteger("unlockedSkillsCount", unlockedSkillsCount);
        unlockedSkills.forEach((category, skills) -> compound.setTag(category.getNameID(),
                writeSkillsToNBT(player, compound, skills)));
    }

    public void unlockSkill(EntityPlayer player, ISkill skill, boolean notifyClient) {
        if (hasSkill(skill))
            return;
        unlockedSkills.get(skill.getSkillCategory()).add(skill);
        unlockedSkillsCount++;
        skill.unlockSkill(player);
        if (notifyClient) {
            PacketManager.sendToPlayer(new ServerPacketUnlockSkill(skill.getSkillCategory(), skill), player);
        }
    }

    public void forgetSkill(EntityPlayer player, ISkill skill, boolean notifyClient) {
        if (!hasSkill(skill))
            return;
        unlockedSkills.get(skill.getSkillCategory()).remove(skill);
        unlockedSkillsCount--;
        skill.forgetSkill(player);
        if (notifyClient) {
            PacketManager.sendToPlayer(new ServerPacketForgetSkill(skill.getSkillCategory(), skill), player);
        }
    }

    public boolean hasSkill(ISkill skill) {
        if (skill == null)
            return false;
        Set<ISkill> skills = unlockedSkills.get(skill.getSkillCategory());
        if (skills.isEmpty())
            return false;
        return skills.contains(skill);
    }

    public Set<ISkill> getUnlockedSkillForCategory(ISkillCategory category) {
        return unlockedSkills.get(category);
    }

    public void addSkillPoints(EntityPlayer player, int points, boolean notifyClient) {
        skillPoints += points;
        if(notifyClient) {
            ServerPacketSyncSkillPoints packet = new ServerPacketSyncSkillPoints(skillPoints);
            PacketManager.sendToPlayer(packet, player);
        }
    }

    public int getSkillPoints() {
        return skillPoints;
    }

    public void setSkillPoints(int skillPoints) {
        this.skillPoints = skillPoints;
    }

    public int getUnlockedSkillsCount() {
        return unlockedSkillsCount;
    }

    public boolean hasEnoughPoints(ISkill skill) {
        return skillPoints >= skill.getSkillPointCost();
    }

    public void trackData(AbstractData data) {
        if(data.sendClientUpdates) {
            trackingData.put(data.registryName, data);
            data.setDataTracker(this);
        }
        if(data instanceof ITickable) {
            tickingStuff.add((ITickable) data);
        }
    }

    public void stopTrackingData(AbstractData data) {
        trackingData.remove(data.registryName);
        tickingStuff.remove((ITickable) data);
    }

    public void queueDataToSync(AbstractData data) {
        dataQueuedToSync.add(data);
    }

    public void tick(EntityPlayer player) {
        tickingStuff.forEach(tickable -> {
            if (tickable.shouldTick()) tickable.tick();
        });
        if(dataQueuedToSync.isEmpty())
            return;
        ServerPacketSyncTrackingData packetSyncTrackingData = new ServerPacketSyncTrackingData(dataQueuedToSync);
        PacketManager.sendToPlayer(packetSyncTrackingData, player);
    }

    public void readSkillsFromNBT(EntityPlayer player, ISkillCategory category, Set<ISkill> skillList, NBTTagCompound compound) {
        NBTTagList nbtList = compound.getTagList(category.getNameID(),8); //8 for string nbt
        for (int i = 0; i < nbtList.tagCount(); i++) {
            ISkill skill = category.getSkillFromID(nbtList.getStringTagAt(i));
            if (skill == null)
                continue;
            skillList.add(skill);

            if (skill.reapplyOnRestart())
                skill.unlockSkill(player);

            if (skill instanceof AbstractSkillWithData) {
                AbstractSkillWithData<?> dataSkill = (AbstractSkillWithData<?>) skill;
                dataSkill.readData(player, compound);
            }
        }
    }

    public static NBTTagList writeSkillsToNBT(EntityPlayer player, NBTTagCompound compound, Set<ISkill> skillList) {
        NBTTagList nbtTagList = new NBTTagList();
        skillList.forEach(skill -> {
            nbtTagList.appendTag(new NBTTagString(skill.getNameID()));
            if(skill instanceof AbstractSkillWithData) {
                ((AbstractSkillWithData<?>) skill).writeData(player, compound);
            }
        });
        return nbtTagList;
    }
}
