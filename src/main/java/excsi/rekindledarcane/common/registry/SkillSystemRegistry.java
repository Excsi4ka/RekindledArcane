package excsi.rekindledarcane.common.registry;

import excsi.rekindledarcane.api.RekindledArcaneAPI;
import excsi.rekindledarcane.api.skill.Point;
import excsi.rekindledarcane.common.skill.SkillCategory;
import excsi.rekindledarcane.common.skill.ability.CooldownSkill;
import excsi.rekindledarcane.common.skill.ability.ToggleableSkill;
import excsi.rekindledarcane.common.skill.attribute.AttributeOperation;
import excsi.rekindledarcane.common.skill.attribute.AttributeSkill;
import excsi.rekindledarcane.common.skill.event.LesserRunicShieldSkill;
import excsi.rekindledarcane.common.skill.event.TestEventSkill;
import net.minecraft.entity.SharedMonsterAttributes;

import java.awt.Color;

public class SkillSystemRegistry {

    public static void register() {
        SkillCategory skillCategory = new SkillCategory("COMBAT", new Color(0, 66, 110, 152));
        RekindledArcaneAPI.registerSkillCategory(skillCategory.getNameID(), skillCategory);

        skillCategory.registerSkill(new AttributeSkill("strength")
                .addSkillAttribute(SharedMonsterAttributes.maxHealth, AttributeOperation.ADDITIVE, 1)
                .addSkillAttribute(RekindledArcaneAPI.MAGIC_RESISTANCE, AttributeOperation.ADDITIVE, 3)
                .setPosition(Point.of(-11, 20)));

        skillCategory.registerSkill(new AttributeSkill("strength2")
                .addSkillAttribute(SharedMonsterAttributes.maxHealth, AttributeOperation.ADDITIVE, 5)
                .addSkillAttribute(RekindledArcaneAPI.MAGIC_RESISTANCE, AttributeOperation.ADDITIVE, 2)
                .setPreRequisite("strength")
                .setPosition(Point.of(30, 60)));

        skillCategory.registerSkill(new AttributeSkill("strength3")
                .addSkillAttribute(SharedMonsterAttributes.maxHealth, AttributeOperation.ADDITIVE, 5)
                .addSkillAttribute(RekindledArcaneAPI.MAGIC_RESISTANCE, AttributeOperation.ADDITIVE, 200)
                .setPreRequisite("strength2")
                .setPosition(Point.of(30, 90)));

        skillCategory.registerSkill(new AttributeSkill("strength4")
                .addSkillAttribute(SharedMonsterAttributes.maxHealth, AttributeOperation.ADDITIVE, 5)
                .addSkillAttribute(RekindledArcaneAPI.MAGIC_RESISTANCE, AttributeOperation.ADDITIVE, 200)
                .setPreRequisite("strength3")
                .setPosition(Point.of(30, 130)));

        skillCategory.registerSkill(new AttributeSkill("strength5")
                .addSkillAttribute(SharedMonsterAttributes.maxHealth, AttributeOperation.ADDITIVE, 5)
                .addSkillAttribute(RekindledArcaneAPI.MAGIC_RESISTANCE, AttributeOperation.ADDITIVE, 200)
                .setPreRequisite("strength3")
                .setPosition(Point.of(60, 120)));

        skillCategory.registerSkill(new AttributeSkill("magic1")
                .addSkillAttribute(SharedMonsterAttributes.maxHealth, AttributeOperation.MULTIPLY_BASE, 1)
                .addSkillAttribute(RekindledArcaneAPI.MAGIC_RESISTANCE, AttributeOperation.ADDITIVE, 1)
                .addSkillAttribute(SharedMonsterAttributes.maxHealth, AttributeOperation.ADDITIVE, 2)
                .setPreRequisite("strength3")
                .setPosition(Point.of(60, 90)));

        skillCategory.registerSkill(new TestEventSkill("event1")
                .setPreRequisite("strength3")
                .setPosition(Point.of(-20, 110)));

        skillCategory.registerSkill(new LesserRunicShieldSkill("event2")
                .setPreRequisite("event1")
                .setPosition(Point.of(-60, 125)));

        skillCategory.registerSkill(new CooldownSkill("tg2")
                .setPreRequisite("event1")
                .setPosition(Point.of(-100, 125)));

        skillCategory.registerSkill(new ToggleableSkill("tg1")
                .setPreRequisite("event1")
                .setPosition(Point.of(-100, 155)));

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
