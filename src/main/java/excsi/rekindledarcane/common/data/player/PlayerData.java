package excsi.rekindledarcane.common.data.player;

import excsi.rekindledarcane.api.RekindledArcaneAPI;
import excsi.rekindledarcane.api.skill.ISkill;
import excsi.rekindledarcane.api.skill.ISkillCategory;
import excsi.rekindledarcane.common.network.PacketManager;
import excsi.rekindledarcane.common.network.server.ServerPacketUnlockSkill;
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
                NBTTagList list = compound.getTagList(category.getNameID(),8); //8 for string nbt
                readSkillsFromNBT(player, category, skills, list);
            }
        });
    }

    public void writeData(NBTTagCompound compound, EntityPlayer player) {
        compound.setInteger("skillPoints", skillPoints);
        unlockedSkills.forEach((category, skills) -> compound.setTag(category.getNameID(),
                writeSkillsToNBT(new NBTTagList(), skills)));
    }

    public void unlockSkill(EntityPlayer player, ISkill skill, boolean notifyClient) {
        if(hasSkill(skill))
            return;
        unlockedSkills.get(skill.getSkillCategory()).add(skill);
        if(notifyClient) {
            PacketManager.sendToPlayer(new ServerPacketUnlockSkill(skill.getSkillCategory(), skill), player);
            skill.unlockSkill(player);
        }
    }

    public void forgetSkill(EntityPlayer player, ISkill skill, boolean notifyClient) {
        if(!hasSkill(skill))
            return;
        unlockedSkills.get(skill.getSkillCategory()).remove(skill);
        skill.forgetSkill(player);
    }

    public void addSkillPoints(int points, boolean notifyClient) {
        skillPoints += points;
    }

    public void consumePoints(int points) {
        skillPoints -= points;
    }

    public boolean hasEnoughPointsForSkill(ISkill skill) {
        return skillPoints >= skill.getSkillPointCost();
    }

    public boolean hasSkill(ISkill skill) {
        if(skill == null)
            return false;
        Set<ISkill> skills = unlockedSkills.get(skill.getSkillCategory());
        if(skills.isEmpty())
            return false;
        return skills.contains(skill);
    }

    public Set<ISkill> getUnlockedSkillForCategory(ISkillCategory category) {
        return unlockedSkills.get(category);
    }

    public void readSkillsFromNBT(EntityPlayer player, ISkillCategory category, Set<ISkill> skillList, NBTTagList nbtList) {
        for(int i = 0; i < nbtList.tagCount(); i++) {
            ISkill skill = category.getSkillFromID(nbtList.getStringTagAt(i));
            if (skill == null)
                continue;
            skillList.add(skill);
            if (skill.reapplyOnRestart()) skill.unlockSkill(player);
        }
    }

    public static NBTTagList writeSkillsToNBT(NBTTagList nbtTagList, Set<ISkill> skillList) {
        skillList.forEach(s -> nbtTagList.appendTag(new NBTTagString(s.getNameID())));
        return nbtTagList;
    }
}
