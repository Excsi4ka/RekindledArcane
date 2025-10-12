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

    public static IAttribute MAGIC_RESISTANCE = new RangedAttribute("rekindledarcane.magicResistance", 0, 0, 100).setShouldWatch(true);

    public static IAttribute SUMMONING_STRENGTH = new RangedAttribute("rekindledarcane.summoningStrength", 0, 0, 10).setShouldWatch(true);

    public static IAttribute MAX_SUMMONS = new RangedAttribute("rekindledarcane.maxSummons", 0, 0, Double.MAX_VALUE).setShouldWatch(true);

    public static IAttribute SPELL_POWER = new RangedAttribute("rekindledarcane.spellPower", 0, 0, Double.MAX_VALUE).setShouldWatch(true);

    public static IAttribute REACH_DISTANCE = new RangedAttribute("rekindledarcane.reachDistance", 0, 0, 10).setShouldWatch(true);

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
