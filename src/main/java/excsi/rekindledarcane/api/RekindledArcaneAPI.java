package excsi.rekindledarcane.api;

import excsi.rekindledarcane.api.skill.ISkill;
import excsi.rekindledarcane.api.skill.ISkillCategory;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class RekindledArcaneAPI {

    public static final IAttribute MAGIC_RESISTANCE = new RangedAttribute("rekindledarcane.magicResistance", 1, 0, 2)
            .setShouldWatch(true); //as percentage. value - 1 = percent reduction i.e. 1.7 - 1 is 70% magic damage reduction

    public static final IAttribute SUMMONING_STRENGTH = new RangedAttribute("rekindledarcane.summoningStrength", 1, 0, Double.MAX_VALUE)
            .setShouldWatch(true); //as percentage

    public static final IAttribute MAX_SUMMONS = new RangedAttribute("rekindledarcane.maxSummons", 0, 0, 15)
            .setShouldWatch(true);

    public static final IAttribute SPELL_POWER = new RangedAttribute("rekindledarcane.spellPower", 1, 0, Double.MAX_VALUE)
            .setShouldWatch(true); //as percentage

    public static final IAttribute REACH_DISTANCE = new RangedAttribute("rekindledarcane.reachDistance", 0, 0, 10)
            .setShouldWatch(true);

    public static final IAttribute DODGE_CHANCE = new RangedAttribute("rekindledarcane.dodgeChance", 1, 0, 2)
            .setShouldWatch(true); //as percentage

    public static final IAttribute CRIT_CHANCE = new RangedAttribute("rekindledarcane.critChance", 1, 0, 2)
            .setShouldWatch(true); //as percentage

    public static final IAttribute CRIT_DAMAGE = new RangedAttribute("rekindledarcane.critDamage", 1, 0, Double.MAX_VALUE)
            .setShouldWatch(true); //as percentage

    private static final HashMap<String, ISkillCategory> skillCategoryMap = new HashMap<>();

    public static Logger LOG = LogManager.getLogger("RekindledArcaneAPI");

    public static ISkillCategory registerSkillCategory(String id, ISkillCategory category) {
        if (skillCategoryMap.containsKey(id)) {
            LOG.error("Duplicate category ID: " + id + " can not register");
            return null;
        }
        skillCategoryMap.put(id, category);
        return category;
    }

    public static ISkillCategory getCategory(String id) {
        return skillCategoryMap.get(id);
    }

    public static Collection<ISkillCategory> getAllCategories() {
        return skillCategoryMap.values();
    }

    public static ISkill getSkillByRegistryName(String name) {
        if(name.isEmpty()) return null;
        int i = name.indexOf(':');
        ISkillCategory category = skillCategoryMap.get(name.substring(0, i));
        if(category == null) return null;
        return category.getSkillFromID(name.substring(i + 1));
    }

    public static Set<String> getCategoryNames() {
        return skillCategoryMap.keySet();
    }
}
