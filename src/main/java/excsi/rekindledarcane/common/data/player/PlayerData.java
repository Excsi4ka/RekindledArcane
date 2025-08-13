package excsi.rekindledarcane.common.data.player;

import excsi.rekindledarcane.api.RekindledArcaneAPI;
import excsi.rekindledarcane.api.skill.ISkill;
import excsi.rekindledarcane.api.skill.ISkillCategory;
import excsi.rekindledarcane.common.util.NBTUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerData {

    private int skillPoints;

    public HashMap<ISkillCategory, List<ISkill>> unlockedSkills = new HashMap<>();

    public PlayerData() {
        skillPoints = 0;
        RekindledArcaneAPI.getAllCategories().forEach(category -> unlockedSkills.put(category, new ArrayList<>()));
    }

    public void readData(NBTTagCompound compound) {
        skillPoints = compound.getInteger("skillPoints");
        unlockedSkills.forEach((category, skills) -> {
            if(compound.hasKey(category.getNameID())) {
                NBTTagList list = compound.getTagList(category.getNameID(),8); //8 for string nbt
                NBTUtils.readSkillsFromNBT(category, list, skills);
            }
        });
    }

    public void writeData(NBTTagCompound compound) {
        compound.setInteger("skillPoints", skillPoints);
        unlockedSkills.forEach((category, skills) -> compound.setTag(category.getNameID(), NBTUtils.writeSkillsToNBT(new NBTTagList(), skills)));
    }

    public void addSkillPoints(int points, boolean notifyClient) {
        skillPoints += points;
        if(notifyClient) {

        }
    }

    public boolean hasSkill(ISkill skill) {
        List<ISkill> skills = unlockedSkills.get(skill.getSkillCategory());
        if(skills.isEmpty())
            return false;
        return skills.contains(skill);
    }

    public boolean hasEnoughPointsForSkill(ISkill skill) {
        return skillPoints >= skill.getSkillPointCost();
    }

    public boolean unlockSkill(EntityPlayer player, ISkill skill, boolean notifyClient) {
        if(hasSkill(skill))
            return false;
        unlockedSkills.get(skill.getSkillCategory()).add(skill);
        skill.unlockSkill(player);
        return true;
    }

    public boolean forgetSkill(EntityPlayer player, ISkill skill, boolean notifyClient) {
        if(!hasSkill(skill))
            return false;
        unlockedSkills.get(skill.getSkillCategory()).remove(skill);
        skill.forgetSkill(player);
        return true;
    }
}
