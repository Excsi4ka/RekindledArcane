package excsi.rekindledarcane.api.skill;

import excsi.rekindledarcane.api.data.skill.ISkillDataTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public interface ISkillDataHandler {
    /**
     * @return A combined name SkillCategory:SkillName used for saving data to nbt as its key
     */
    String getRegistryName();

    void writeData(EntityPlayer player, ISkillDataTracker tracker, NBTTagCompound tagCompound);

    void readData(EntityPlayer player, ISkillDataTracker tracker, NBTTagCompound tagCompound);
}
