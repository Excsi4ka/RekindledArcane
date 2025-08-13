package excsi.rekindledarcane.common.util;

import excsi.rekindledarcane.api.skill.ISkill;
import excsi.rekindledarcane.api.skill.ISkillCategory;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

import java.util.List;

public class NBTUtils {

    public static List<ISkill> readSkillsFromNBT(ISkillCategory category, NBTTagList nbtList, List<ISkill> skillList) {
        for(int i = 0; i < nbtList.tagCount(); i++) {
            ISkill skill = category.getSkillFromID(nbtList.getStringTagAt(i));
            if(skill != null)
                skillList.add(skill);
        }
        return skillList;
    }

    public static NBTTagList writeSkillsToNBT(NBTTagList nbtTagList, List<ISkill> skillList) {
        skillList.forEach(s -> nbtTagList.appendTag(new NBTTagString(s.getNameID())));
        return nbtTagList;
    }
}
