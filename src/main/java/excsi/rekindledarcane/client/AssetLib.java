package excsi.rekindledarcane.client;

import excsi.rekindledarcane.RekindledArcane;
import excsi.rekindledarcane.api.RekindledArcaneAPI;
import excsi.rekindledarcane.api.skill.SkillType;
import excsi.rekindledarcane.common.util.RekindledArcaneConfig;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;

public class AssetLib {

    public static final ResourceLocation backgroundTex = rl("textures/gui/background.png");

    public static final ResourceLocation skillIconTextureAtlas = new ResourceLocation("textures/atlas/skill_icons.png");

    public static final ResourceLocation menuDing = new ResourceLocation("rekindledarcane","menu.ding");

    public static final ResourceLocation skillSelectorGui = rl("textures/gui/gui.png");

    public static IIcon missingSkillTexture;

    public static ResourceLocation rl(String resourcePath) {
        return new ResourceLocation("rekindledarcane", resourcePath);
    }

    public static void stitchSkillTextures(TextureStitchEvent.Pre event) {
        if (event.map.getTextureType() != RekindledArcaneConfig.skillIconsTextureAtlasID)
            return;

        RekindledArcane.LOG.info("Creating a texture atlas for skill icons");
        TextureMap map = event.map;

        missingSkillTexture = map.registerIcon("rekindledarcane:missing_skill_icon");

        for (SkillType type : SkillType.values()) {
            type.frameIcon = map.registerIcon(type.typeTexName);
        }

        //the skills should have been loaded by now in preInit
        RekindledArcaneAPI.getAllCategories().forEach(category -> {
            category.getAllSkills().forEach(skill -> skill.registerSkillIcon(map));
        });
    }
}
