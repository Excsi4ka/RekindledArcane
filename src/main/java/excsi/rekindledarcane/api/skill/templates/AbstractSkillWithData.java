package excsi.rekindledarcane.api.skill.templates;

import excsi.rekindledarcane.api.data.skill.ISkillDataTracker;
import excsi.rekindledarcane.api.data.skill.AbstractData;
import excsi.rekindledarcane.api.skill.ISkill;
import excsi.rekindledarcane.api.skill.ISkillCategory;
import excsi.rekindledarcane.api.skill.ISkillDataHandler;
import excsi.rekindledarcane.common.data.player.PlayerData;
import excsi.rekindledarcane.common.data.player.PlayerDataManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.UUID;

public abstract class AbstractSkillWithData<DATA extends AbstractData> extends AbstractSkill implements ISkillDataHandler {

    private String registryName;

    //fast data lookup? maybe
    private final HashMap<UUID, DATA> skillPlayerToDataMap = new HashMap<>();

    public AbstractSkillWithData(String nameID) {
        super(nameID);
    }

    public boolean hasThisSkill(EntityLivingBase entityLivingBase) {
        if(!(entityLivingBase instanceof EntityPlayer))
            return false;
        return skillPlayerToDataMap.containsKey(entityLivingBase.getUniqueID());
    }

    public DATA getSkillData(EntityLivingBase entityLivingBase) {
        if(!(entityLivingBase instanceof EntityPlayer))
            return null;
        EntityPlayer player = (EntityPlayer) entityLivingBase;
        return skillPlayerToDataMap.get(player.getUniqueID());
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

    public abstract DATA createDefaultDataInstance();

    @Override
    public void writeData(EntityPlayer player, ISkillDataTracker tracker, NBTTagCompound compound) {
        DATA data = skillPlayerToDataMap.get(player.getUniqueID());
        NBTTagCompound tagCompound = new NBTTagCompound();
        data.writeToNBT(tagCompound);
        compound.setTag(registryName, tagCompound);
    }

    @Override
    public void readData(EntityPlayer player, ISkillDataTracker tracker, NBTTagCompound compound) {
        DATA data = createDefaultDataInstance();
        data.readFromNBT(compound.getCompoundTag(registryName));
        tracker.trackData(data);
        skillPlayerToDataMap.put(player.getUniqueID(), data);
    }

    @Override
    public String getRegistryName() {
        return registryName;
    }
}
