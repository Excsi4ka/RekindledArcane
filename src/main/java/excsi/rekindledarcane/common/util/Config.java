package excsi.rekindledarcane.common.util;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Config {

    public static Configuration CONFIG;

    public static String

            CATEGORY_CLIENT = "Client",
            CATEGORY_SKILLS = "Skills",
            CATEGORY_WORLDGEN = "Worldgen",
            CATEGORY_GENERAL = "General";

    public static int skillIconsTextureAtlasID;

    public static boolean shouldHealPlayerOnRespawn;

    public static void loadConfig(File file) {
        CONFIG = new Configuration(file);

        CONFIG.addCustomCategoryComment(CATEGORY_CLIENT, "Client related settings");
        skillIconsTextureAtlasID = CONFIG.getInt("TextureAtlasID", CATEGORY_CLIENT, 12, 3,
                Integer.MAX_VALUE, "The ID for the texture atlas onto which skill icons are stitched");

        CONFIG.addCustomCategoryComment(CATEGORY_GENERAL,"General mod settings");
        shouldHealPlayerOnRespawn = CONFIG.getBoolean("HealPlayerOnRespawn", CATEGORY_GENERAL,
                false, "Should players be healed to their maximum health on respawn");

        CONFIG.save();
    }
}
