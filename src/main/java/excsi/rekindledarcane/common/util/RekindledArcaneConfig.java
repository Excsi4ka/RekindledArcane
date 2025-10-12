package excsi.rekindledarcane.common.util;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class RekindledArcaneConfig {

    public static Configuration CONFIG;

    public static String

            CATEGORY_CLIENT = "Client",
            CATEGORY_SKILLS = "Skills",
            CATEGORY_WORLDGEN = "Worldgen",
            CATEGORY_GENERAL = "General",
            CATEGORY_POTIONS = "Potions";

    public static int skillIconsTextureAtlasID;

    public static int maxSkillsCap;

    public static boolean shouldHealPlayerOnRespawn;

    public static boolean keepGearOnDeath;

    public static float magicResistanceCap;

    public static boolean extendPotionIDS;

    public static int vulnerabilityPotionId;

    public static void loadConfig(File file) {
        CONFIG = new Configuration(file);

        CONFIG.addCustomCategoryComment(CATEGORY_CLIENT, "Client related settings");
        skillIconsTextureAtlasID = CONFIG.getInt("TextureAtlasID", CATEGORY_CLIENT, 12, 3,
                Integer.MAX_VALUE, "The ID for the texture atlas onto which skill icons are stitched");

        CONFIG.addCustomCategoryComment(CATEGORY_GENERAL,"General mod settings");
        shouldHealPlayerOnRespawn = CONFIG.getBoolean("HealPlayerOnRespawn", CATEGORY_GENERAL,
                false, "Should players be healed to their maximum health on respawn");

        keepGearOnDeath = CONFIG.getBoolean("KeepGearOnDeath", CATEGORY_GENERAL,
                false, "Makes the player keep important items on death like armor and tools and only drops secondary items");

        CONFIG.addCustomCategoryComment(CATEGORY_SKILLS, "Skill system related settings");
        maxSkillsCap = CONFIG.getInt("MaxSkillsCap", CATEGORY_SKILLS, 50, 0,
                Integer.MAX_VALUE, "Maximum number of skills a player can unlock");

        magicResistanceCap = CONFIG.getFloat("MagicResistanceAttributeCap", CATEGORY_SKILLS, 100, 0,
                100, "The cap for magic resistance attribute value, 100 means full magic resistance is possible to achieve");

        CONFIG.addCustomCategoryComment(CATEGORY_POTIONS, "Potion related settins");
        extendPotionIDS = CONFIG.getBoolean("ExtendPotionIDS", CATEGORY_POTIONS,
                true,"Toggles the potion ids extension\nMany mods do this already so you can disable this if needed");
        vulnerabilityPotionId = CONFIG.getInt("VulnerabilityPotionID", CATEGORY_POTIONS, 50, 0,
                Integer.MAX_VALUE, "ID of the Vulnerability potion effect");

        CONFIG.save();
    }
}
