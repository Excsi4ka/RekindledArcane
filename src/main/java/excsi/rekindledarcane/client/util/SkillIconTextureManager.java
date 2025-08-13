package excsi.rekindledarcane.client.util;

import excsi.rekindledarcane.RekindledArcane;
import excsi.rekindledarcane.api.RekindledArcaneAPI;
import excsi.rekindledarcane.api.skill.SkillType;
import excsi.rekindledarcane.common.util.Config;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;

import java.util.HashMap;

public class SkillIconTextureManager {

    public static final ResourceLocation skillIconTextureAtlas = new ResourceLocation("textures/atlas/skill_icons.png");

    public static IIcon missingSkillTexture;

    public static void stitchSkillTextures(TextureStitchEvent.Pre event) {
        if (event.map.getTextureType() != Config.skillIconsTextureAtlasID)
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
