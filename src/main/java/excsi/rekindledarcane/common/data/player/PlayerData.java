package excsi.rekindledarcane.common.data.player;

import excsi.rekindledarcane.api.RekindledArcaneAPI;
import excsi.rekindledarcane.api.data.skill.SkillData;
import excsi.rekindledarcane.api.data.skill.IDataTracker;
import excsi.rekindledarcane.api.skill.IActiveAbilitySkill;
import excsi.rekindledarcane.api.skill.ISkill;
import excsi.rekindledarcane.api.skill.ISkillCategory;
import excsi.rekindledarcane.api.skill.ISkillDataHandler;
import excsi.rekindledarcane.common.network.PacketManager;
import excsi.rekindledarcane.common.network.server.ServerPacketForgetSkill;
import excsi.rekindledarcane.common.network.server.ServerPacketSyncSkillPoints;
import excsi.rekindledarcane.common.network.server.ServerPacketSyncTrackingData;
import excsi.rekindledarcane.common.network.server.ServerPacketUnlockSkill;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlayerData implements IDataTracker {

    private int skillPoints, unlockedSkillsCount, slotCount;

    private final HashMap<ISkillCategory, Set<ISkill>> unlockedSkills = new HashMap<>();

    private final List<IActiveAbilitySkill> activeSkills = Arrays.asList(new IActiveAbilitySkill[5]);

    //-------- For data tracking and updating ---------
    public HashMap<String, SkillData> trackingData = new HashMap<>();

    private final List<SkillData> dataQueuedToSync = new ArrayList<>();

    private final Set<SkillData> tickingStuff = new HashSet<>();
    //----------------------------------------------------

    public PlayerData() {
        this.skillPoints = 0;
        this.unlockedSkillsCount = 0;
        this.slotCount = 1;
        RekindledArcaneAPI.getAllCategories().forEach(category -> unlockedSkills.put(category, new HashSet<>()));
    }

    public void readData(NBTTagCompound compound, EntityPlayer player) {
        skillPoints = compound.getInteger("skillPoints");
        unlockedSkillsCount = compound.getInteger("unlockedSkillsCount");
        slotCount = compound.getInteger("activeSlotCount");
        unlockedSkills.forEach((category, skills) -> {
            if(compound.hasKey(category.getNameID())) {
                readSkillsFromNBT(player, category, skills, compound);
            }
        });
        NBTTagList list = compound.getTagList("equippedSkills", 8); //8 for string
        for (int i = 0; i < list.tagCount(); i++) {
            ISkill skill = RekindledArcaneAPI.getSkillByRegistryName(list.getStringTagAt(i));
            activeSkills.set(i, (IActiveAbilitySkill) skill);
        }
    }

    public void writeData(NBTTagCompound compound, EntityPlayer player) {
        compound.setInteger("skillPoints", skillPoints);
        compound.setInteger("unlockedSkillsCount", unlockedSkillsCount);
        compound.setInteger("activeSlotCount", slotCount);
        unlockedSkills.forEach((category, skills) -> compound.setTag(category.getNameID(),
                writeSkillsToNBT(player, compound, skills)));
        NBTTagList list = new NBTTagList();
        activeSkills.forEach(activeSkill -> list.appendTag(new NBTTagString(activeSkill != null ? activeSkill.getRegistryName() : "")));
        compound.setTag("equippedSkills", list);
    }

    public void unlockSkill(EntityPlayer player, ISkill skill, boolean notifyClient) {
        if (hasSkill(skill))
            return;
        unlockedSkills.get(skill.getSkillCategory()).add(skill);
        unlockedSkillsCount++;

        skill.unlockSkill(player);

        if (skill instanceof ISkillDataHandler) {
            ((ISkillDataHandler) skill).linkSkillData(player, this);
        }

        if (notifyClient) {
            PacketManager.sendToPlayer(new ServerPacketUnlockSkill(skill.getSkillCategory(), skill), player);
        }
    }

    public void forgetSkill(EntityPlayer player, ISkill skill, boolean notifyClient) {
        if (!hasSkill(skill))
            return;
        unlockedSkills.get(skill.getSkillCategory()).remove(skill);
        unlockedSkillsCount--;

        if (skill instanceof ISkillDataHandler) {
            ((ISkillDataHandler) skill).unlinkSkillData(player, this);
        }

        if (skill instanceof IActiveAbilitySkill) {
            int index = activeSkills.indexOf(skill);
            if (index != -1) activeSkills.set(index, null);
        }

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
            PacketManager.sendToPlayer(new ServerPacketSyncSkillPoints(skillPoints), player);
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

    public List<IActiveAbilitySkill> getEquippedActiveSkills() {
        return activeSkills;
    }

//    public List<IActiveAbilitySkill> getAllActiveSkills() {
//        List<IActiveAbilitySkill> list = new ArrayList<>();
//        unlockedSkills.forEach(((category, iSkills) -> iSkills.forEach(skill -> {
//            if (skill instanceof IActiveAbilitySkill) {
//                list.add((IActiveAbilitySkill) skill);
//            }
//        })));
//        return list;
//    }

    public void unlockActiveSlot() {
        slotCount = Math.min(slotCount + 1, 5);
    }

    public void lockActiveSlot() {
        slotCount = Math.max(slotCount - 1, 0);
        activeSkills.set(slotCount, null);
    }

    public int getActiveSlotCount() {
        return slotCount;
    }

    public void tick(EntityPlayer player) {
        tickingStuff.forEach(tickData -> {
            if (tickData.readyToTick()) tickData.tick();
        });
        if(dataQueuedToSync.isEmpty())
            return;
        ServerPacketSyncTrackingData packetSyncTrackingData = new ServerPacketSyncTrackingData(dataQueuedToSync);
        PacketManager.sendToPlayer(packetSyncTrackingData, player);
    }

    private void readSkillsFromNBT(EntityPlayer player, ISkillCategory category, Set<ISkill> skillList, NBTTagCompound compound) {
        NBTTagList nbtList = compound.getTagList(category.getNameID(),8); //8 for string nbt
        for (int i = 0; i < nbtList.tagCount(); i++) {
            ISkill skill = category.getSkillFromID(nbtList.getStringTagAt(i));
            if (skill == null)
                continue;
            skillList.add(skill);

            if (skill.reapplyOnRestart())
                skill.unlockSkill(player);

            if (skill instanceof ISkillDataHandler) {
                ISkillDataHandler dataHandler = (ISkillDataHandler) skill;
                dataHandler.readData(player, compound.getCompoundTag(dataHandler.getRegistryName()));
                dataHandler.linkSkillData(player, this);
            }
        }
    }

    private NBTTagList writeSkillsToNBT(EntityPlayer player, NBTTagCompound compound, Set<ISkill> skillList) {
        NBTTagList nbtTagList = new NBTTagList();
        skillList.forEach(skill -> {
            nbtTagList.appendTag(new NBTTagString(skill.getNameID()));
            if(skill instanceof ISkillDataHandler) {
                ISkillDataHandler dataHandler = (ISkillDataHandler) skill;
                NBTTagCompound tagCompound = new NBTTagCompound();
                dataHandler.writeData(player, tagCompound);
                compound.setTag(dataHandler.getRegistryName(), tagCompound);
            }
        });
        return nbtTagList;
    }

    @Override
    public void trackData(SkillData data) {
        if(data.shouldSendClientUpdates()) {
            trackingData.put(data.getRegistryName(), data);
            data.setDataTracker(this);
        }
        if(data.isTickingData()) {
            tickingStuff.add(data);
        }
    }

    @Override
    public void stopTrackingData(SkillData data) {
        trackingData.remove(data.getRegistryName());
        tickingStuff.remove(data);
    }

    @Override
    public void queueDataToSync(SkillData data) {
        dataQueuedToSync.add(data);
    }
}
