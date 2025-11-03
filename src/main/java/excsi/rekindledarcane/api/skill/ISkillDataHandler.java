package excsi.rekindledarcane.api.skill;

import excsi.rekindledarcane.api.data.skill.IDataTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Skills that implement this interface can save and load their data from nbt and add them to a tracker to tick and send client updates
 */
public interface ISkillDataHandler {

    /**
     * @return A combined name SkillCategory:SkillName used for saving data to nbt as its key
     */
    String getRegistryName();

    /**
     * This method is called AFTER {@link ISkill#unlockSkill(EntityPlayer)}, you can add skill data to the tracker here
     */
    void linkSkillData(EntityPlayer player, IDataTracker tracker);

    /**
     * This method is called BEFORE {@link ISkill#forgetSkill(EntityPlayer)},
     * you can remove skill data from the tracker here before the actual skill is removed from the player
     */
    void unlinkSkillData(EntityPlayer player, IDataTracker tracker);

    void writeData(EntityPlayer player, NBTTagCompound tagCompound);

    void readData(EntityPlayer player, NBTTagCompound tagCompound);
}
