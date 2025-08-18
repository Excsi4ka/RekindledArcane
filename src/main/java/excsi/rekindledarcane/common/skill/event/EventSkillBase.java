package excsi.rekindledarcane.common.skill.event;

import excsi.rekindledarcane.api.skill.SkillType;
import excsi.rekindledarcane.common.skill.AdditionalDataSkill;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;

import java.util.HashSet;
import java.util.UUID;

public abstract class EventSkillBase extends AdditionalDataSkill {

    public final HashSet<UUID> playersWithSkill;

    public EventSkillBase(String nameID) {
        super(nameID);
        playersWithSkill = new HashSet<>();
        MinecraftForge.EVENT_BUS.register(this);
    }

    public boolean hasThisSkill(EntityLivingBase entityLivingBase) {
        if(!(entityLivingBase instanceof EntityPlayer))
            return false;
        return playersWithSkill.contains(entityLivingBase.getUniqueID());
    }

    @Override
    public SkillType getSkillType() {
        return SkillType.PASSIVE;
    }

    @Override
    public void unlockSkill(EntityPlayer player) {
        playersWithSkill.add(player.getUniqueID());
    }

    @Override
    public void forgetSkill(EntityPlayer player) {
        playersWithSkill.remove(player.getUniqueID());
    }

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
