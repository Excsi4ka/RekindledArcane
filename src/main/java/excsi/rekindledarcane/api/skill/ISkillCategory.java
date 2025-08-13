package excsi.rekindledarcane.api.skill;

import java.awt.Color;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ISkillCategory {

    String getNameID();

    void registerSkill(ISkill skill);

    void unregisterSkill(String id);

    ISkill getSkillFromID(String id);

    String getUnlocalizedName();

    Collection<ISkill> getAllSkills();

    Set<String> getAllSkillIDs();

    Color getPrimaryColor();
}
