package excsi.rekindledarcane.api.skill.templates;

import excsi.rekindledarcane.api.data.skill.IDataTracker;
import excsi.rekindledarcane.api.data.skill.SkillData;
import excsi.rekindledarcane.api.skill.ISkill;
import excsi.rekindledarcane.api.skill.ISkillCategory;
import excsi.rekindledarcane.api.skill.ISkillDataHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.UUID;

public abstract class AbstractSkillWithData<DATA extends SkillData> extends AbstractSkill implements ISkillDataHandler {

    private String registryName;

    //fast data lookup? maybe
    private final HashMap<UUID, DATA> playerToSkillDataMap = new HashMap<>();

    public AbstractSkillWithData(String nameID) {
        super(nameID);
    }

    public boolean hasThisSkill(EntityLivingBase entityLivingBase) {
        if(!(entityLivingBase instanceof EntityPlayer))
            return false;
        return playerToSkillDataMap.containsKey(entityLivingBase.getUniqueID());
    }

    public DATA getSkillData(EntityLivingBase entityLivingBase) {
        if(!(entityLivingBase instanceof EntityPlayer))
            return null;
        EntityPlayer player = (EntityPlayer) entityLivingBase;
        return playerToSkillDataMap.get(player.getUniqueID());
    }

    public abstract DATA createDefaultDataInstance();

    @Override
    public ISkill setSkillCategory(ISkillCategory skillCategory) {
        this.registryName = skillCategory.getNameID() + ":" + getNameID();
        return super.setSkillCategory(skillCategory);
    }

    @Override
    public void unlockSkill(EntityPlayer player) {
        DATA data = createDefaultDataInstance();
        playerToSkillDataMap.put(player.getUniqueID(), data);
    }

    @Override
    public void forgetSkill(EntityPlayer player) {
        playerToSkillDataMap.remove(player.getUniqueID());
    }

    @Override
    public void linkSkillData(EntityPlayer player, IDataTracker tracker) {
        DATA data = playerToSkillDataMap.get(player.getUniqueID());
        tracker.trackData(data);
    }

    @Override
    public void unlinkSkillData(EntityPlayer player, IDataTracker tracker) {
        DATA data = playerToSkillDataMap.get(player.getUniqueID());
        tracker.stopTrackingData(data);
    }

    @Override
    public void writeData(EntityPlayer player, NBTTagCompound compound) {
        DATA data = playerToSkillDataMap.get(player.getUniqueID());
        data.writeToNBT(compound);
    }

    @Override
    public void readData(EntityPlayer player, NBTTagCompound compound) {
        DATA data = playerToSkillDataMap.get(player.getUniqueID());
        data.readFromNBT(compound);
    }

    @Override
    public String getRegistryName() {
        return registryName;
    }

    @Override
    public boolean reapplyOnRestart() {
        return true;
    }
}
