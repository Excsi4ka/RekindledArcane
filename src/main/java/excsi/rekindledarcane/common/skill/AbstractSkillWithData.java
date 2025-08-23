package excsi.rekindledarcane.common.skill;

import excsi.rekindledarcane.api.skill.ISkill;
import excsi.rekindledarcane.api.skill.ISkillCategory;
import excsi.rekindledarcane.common.data.player.PlayerData;
import excsi.rekindledarcane.common.data.player.PlayerDataManager;
import excsi.rekindledarcane.common.data.skill.templates.AbstractData;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.UUID;

public abstract class AbstractSkillWithData<DATA extends AbstractData> extends AbstractSkill {

    public String registryName;

    public final HashMap<UUID, DATA> skillPlayerToDataMap = new HashMap<>();

    public AbstractSkillWithData(String nameID) {
        super(nameID);
    }

    @Override
    public ISkill setSkillCategory(ISkillCategory skillCategory) {
        this.registryName = skillCategory.getNameID() + ":" + getNameID();
        return super.setSkillCategory(skillCategory);
    }

    @Override
    public void unlockSkill(EntityPlayer player) {
        PlayerData playerData = PlayerDataManager.getPlayerData(player);
        DATA data = createDefaultDataInstance();
        playerData.trackData(data);
        skillPlayerToDataMap.put(player.getUniqueID(), data);
    }

    @Override
    public void forgetSkill(EntityPlayer player) {
        PlayerData playerData = PlayerDataManager.getPlayerData(player);
        DATA data = skillPlayerToDataMap.get(player.getUniqueID());
        playerData.stopTrackingData(data);
        skillPlayerToDataMap.remove(player.getUniqueID());
    }

    @Override
    public boolean reapplyOnRestart() {
        return false;
    }

    public void writeData(EntityPlayer player, NBTTagCompound compound) {
        DATA data = skillPlayerToDataMap.get(player.getUniqueID());
        NBTTagCompound tagCompound = new NBTTagCompound();
        data.writeToNBT(tagCompound);
        compound.setTag(registryName, tagCompound);
    }

    public void readData(EntityPlayer player, NBTTagCompound compound) {
        DATA data = createDefaultDataInstance();
        data.readFromNBT(compound.getCompoundTag(registryName));
        PlayerData playerData = PlayerDataManager.getPlayerData(player);
        playerData.trackData(data);
        skillPlayerToDataMap.put(player.getUniqueID(), data);
    }

    public abstract DATA createDefaultDataInstance();

    public DATA getSkillData(EntityLivingBase entityLivingBase) {
        if(!(entityLivingBase instanceof EntityPlayer))
            return null;
        EntityPlayer player = (EntityPlayer) entityLivingBase;
        return skillPlayerToDataMap.get(player.getUniqueID());
    }
}
