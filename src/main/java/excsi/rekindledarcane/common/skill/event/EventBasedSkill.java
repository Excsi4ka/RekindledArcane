package excsi.rekindledarcane.common.skill.event;

import excsi.rekindledarcane.api.skill.SkillType;
import excsi.rekindledarcane.common.data.player.PlayerDataManager;
import excsi.rekindledarcane.common.skill.AdditionalDataSkill;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;

public abstract class EventBasedSkill extends AdditionalDataSkill {

    public EventBasedSkill(String nameID) {
        super(nameID);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public boolean hasThisSkill(EntityLivingBase entityLivingBase) {
        if(!(entityLivingBase instanceof EntityPlayer))
            return false;
        return PlayerDataManager.getPlayerData((EntityPlayer) entityLivingBase).hasSkill(this);
    }

    @Override
    public SkillType getSkillType() {
        return SkillType.PASSIVE;
    }

    @Override
    public void unlockSkill(EntityPlayer player) {}

    @Override
    public void forgetSkill(EntityPlayer player) {}

    @Override
    public boolean reapplyOnRestart() {
        return true;
    }

    @Override
    public boolean hasData() {
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {

    }

    @Override
    public NBTTagCompound writeToNBT() {
        return null;
    }
}
