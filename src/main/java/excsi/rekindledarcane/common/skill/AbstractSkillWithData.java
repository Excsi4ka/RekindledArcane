package excsi.rekindledarcane.common.skill;

import excsi.rekindledarcane.api.skill.ISkill;
import excsi.rekindledarcane.api.skill.ISkillCategory;
import excsi.rekindledarcane.common.data.player.SkillData;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.UUID;

public abstract class AbstractSkillWithData<DATA extends SkillData> extends AbstractSkill {

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
        skillPlayerToDataMap.put(player.getUniqueID(), createDefaultDataInstance());
    }

    @Override
    public void forgetSkill(EntityPlayer player) {
        skillPlayerToDataMap.remove(player.getUniqueID());
    }

    public void writeDataInternal(EntityPlayer player, NBTTagCompound compound) {
        DATA data = skillPlayerToDataMap.get(player.getUniqueID());
        if(data != null) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            writeToNBT(data, tagCompound);
            compound.setTag(registryName, tagCompound);
        }
    }

    public void readDataInternal(EntityPlayer player, NBTTagCompound compound) {
        DATA data = readFromNBT(compound.getCompoundTag(registryName));
        if(data != null) {
            skillPlayerToDataMap.put(player.getUniqueID(), data);
        }
    }

    public abstract void writeToNBT(DATA skillData, NBTTagCompound tagCompound);

    public abstract DATA readFromNBT(NBTTagCompound compound);

    public abstract DATA createDefaultDataInstance();

    public DATA getSkillData(EntityLivingBase entityLivingBase) {
        if(!(entityLivingBase instanceof EntityPlayer))
            return null;
        EntityPlayer player = (EntityPlayer) entityLivingBase;
        if(!skillPlayerToDataMap.containsKey(player.getUniqueID()))
            return null;
        return skillPlayerToDataMap.get(player.getUniqueID());
    }
}
