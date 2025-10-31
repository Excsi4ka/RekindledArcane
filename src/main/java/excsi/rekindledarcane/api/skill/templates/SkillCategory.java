package excsi.rekindledarcane.api.skill.templates;

import excsi.rekindledarcane.api.RekindledArcaneAPI;
import excsi.rekindledarcane.api.skill.ISkill;
import excsi.rekindledarcane.api.skill.ISkillCategory;

import java.awt.Color;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class SkillCategory implements ISkillCategory {

    private final HashMap<String, ISkill> skillMap = new HashMap<>();

    private final String nameID;

    private final Color color;

    public SkillCategory(String name, Color color) {
        this.nameID = name;
        this.color = color;
    }

    @Override
    public String getNameID() {
        return nameID;
    }

    @Override
    public void registerSkill(ISkill skill) {
        if (skillMap.containsKey(skill.getNameID())) {
            RekindledArcaneAPI.LOG.error("Skill with ID: " + skill.getNameID()
                    + " is already registered. Skills should not have duplicate ids, it won't be registered");
            return;
        }
        skill.setSkillCategory(this);
        skillMap.put(skill.getNameID(), skill);
    }

    @Override
    public void unregisterSkill(String id) {
        RekindledArcaneAPI.LOG.info("Removing skill with ID: " + id);
        skillMap.remove(id);
    }

    @Override
    public ISkill getSkillFromID(String id) {
        return skillMap.get(id);
    }

    @Override
    public String getUnlocalizedName() {
        return "rekindledarcane.category." + nameID;
    }

    @Override
    public Collection<ISkill> getAllSkills() {
        return skillMap.values();
    }

    @Override
    public Set<String> getAllSkillIDs() {
        return skillMap.keySet();
    }

    @Override
    public Color getPrimaryColor() {
        return color;
    }

    @Override
    public int hashCode() {
        return nameID.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ISkillCategory))
            return false;
        ISkillCategory category = (ISkillCategory) obj;
        return nameID.equals(category.getNameID());
    }
}
