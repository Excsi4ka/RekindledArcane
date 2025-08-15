package excsi.rekindledarcane.common.registry;

import excsi.rekindledarcane.api.RekindledArcaneAPI;
import excsi.rekindledarcane.api.skill.Point;
import excsi.rekindledarcane.common.skill.SkillCategory;
import excsi.rekindledarcane.common.skill.attribute.AttributeOperation;
import excsi.rekindledarcane.common.skill.attribute.AttributeSkill;
import net.minecraft.entity.SharedMonsterAttributes;

import java.awt.Color;

public class SkillSystemRegistry {

    //public static ISkillCategory WARRIOR;

    //public static ISkillCategory SORCERY;

    public static void register() {
        SkillCategory skillCategory = new SkillCategory("COMBAT", new Color(0, 66, 110, 152));
        RekindledArcaneAPI.registerSkillCategory(skillCategory.getNameID(), skillCategory);

        skillCategory.registerSkill(new AttributeSkill("strength")
                .addSkillAttribute(SharedMonsterAttributes.maxHealth, AttributeOperation.ADDITIVE, 1)
                .addSkillAttribute(RekindledArcaneAPI.MAGIC_RESISTANCE, AttributeOperation.ADDITIVE, 3)
                .setPosition(new Point(-16,10))
                .setCoreSkill());

        skillCategory.registerSkill(new AttributeSkill("strength2")
                .addSkillAttribute(SharedMonsterAttributes.maxHealth, AttributeOperation.ADDITIVE, 5)
                .addSkillAttribute(RekindledArcaneAPI.MAGIC_RESISTANCE, AttributeOperation.ADDITIVE, 2)
                .setPosition(new Point(30,60)));

        skillCategory.registerSkill(new AttributeSkill("strength3")
                .addSkillAttribute(SharedMonsterAttributes.maxHealth, AttributeOperation.ADDITIVE, 5)
                .addSkillAttribute(RekindledArcaneAPI.MAGIC_RESISTANCE, AttributeOperation.ADDITIVE, 200)
                .setPosition(new Point(30,90)));

        skillCategory.registerSkill(new AttributeSkill("magic1")
                .addSkillAttribute(SharedMonsterAttributes.maxHealth, AttributeOperation.MULTIPLY_BASE, 1)
                .addSkillAttribute(RekindledArcaneAPI.MAGIC_RESISTANCE, AttributeOperation.ADDITIVE, 1)
                .setPosition(new Point(60,90)));

        skillCategory = new SkillCategory("SORCERY", new Color(50, 0, 134, 152));
        RekindledArcaneAPI.registerSkillCategory(skillCategory.getNameID(), skillCategory);

        skillCategory = new SkillCategory("SUMMONING", new Color(14, 138, 67, 152));
        RekindledArcaneAPI.registerSkillCategory(skillCategory.getNameID(), skillCategory);

        skillCategory = new SkillCategory("EXPLORATION", new Color(166, 136, 0, 171));
        RekindledArcaneAPI.registerSkillCategory(skillCategory.getNameID(), skillCategory);

        skillCategory = new SkillCategory("CRAFTSMANSHIP", new Color(136, 0, 0, 152));
        RekindledArcaneAPI.registerSkillCategory(skillCategory.getNameID(), skillCategory);
    }

}
