package excsi.rekindledarcane.common.data.player;

import excsi.rekindledarcane.api.RekindledArcaneAPI;
import excsi.rekindledarcane.api.skill.ISkill;
import excsi.rekindledarcane.api.skill.ISkillCategory;
import excsi.rekindledarcane.common.network.PacketManager;
import excsi.rekindledarcane.common.network.server.ServerPacketUnlockSkill;
import excsi.rekindledarcane.common.skill.AbstractSkillWithData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class PlayerData {

    private int skillPoints;

    private final HashMap<ISkillCategory, Set<ISkill>> unlockedSkills = new HashMap<>();

    public PlayerData() {
        skillPoints = 0;
        RekindledArcaneAPI.getAllCategories().forEach(category -> unlockedSkills.put(category, new HashSet<>()));
    }

    public void readData(NBTTagCompound compound, EntityPlayer player) {
        skillPoints = compound.getInteger("skillPoints");
        unlockedSkills.forEach((category, skills) -> {
            if(compound.hasKey(category.getNameID())) {
                readSkillsFromNBT(player, category, skills, compound);
            }
        });
    }

    public void writeData(NBTTagCompound compound, EntityPlayer player) {
        compound.setInteger("skillPoints", skillPoints);
        unlockedSkills.forEach((category, skills) -> compound.setTag(category.getNameID(),
                writeSkillsToNBT(player, compound, skills)));
    }

    public void unlockSkill(EntityPlayer player, ISkill skill, boolean notifyClient) {
        if (hasSkill(skill))
            return;
        unlockedSkills.get(skill.getSkillCategory()).add(skill);
        if (notifyClient) {
            PacketManager.sendToPlayer(new ServerPacketUnlockSkill(skill.getSkillCategory(), skill), player);
            skill.unlockSkill(player);
        }
    }

    public void forgetSkill(EntityPlayer player, ISkill skill, boolean notifyClient) {
        if (!hasSkill(skill))
            return;
        unlockedSkills.get(skill.getSkillCategory()).remove(skill);
        skill.forgetSkill(player);
    }

    public void addSkillPoints(int points, boolean notifyClient) {
        skillPoints += points;
    }

    public int getSkillPoints() {
        return skillPoints;
    }

    public boolean hasEnoughPointsForSkill(ISkill skill) {
        return skillPoints >= skill.getSkillPointCost();
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
                dataSkill.readDataInternal(player, compound);
            }
        }
    }

    public static NBTTagList writeSkillsToNBT(EntityPlayer player, NBTTagCompound compound, Set<ISkill> skillList) {
        NBTTagList nbtTagList = new NBTTagList();
        skillList.forEach(s -> {
            nbtTagList.appendTag(new NBTTagString(s.getNameID()));
            if(s instanceof AbstractSkillWithData) {
                ((AbstractSkillWithData<?>) s).writeDataInternal(player, compound);
            }
        });
        return nbtTagList;
    }
}
