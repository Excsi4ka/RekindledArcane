package excsi.rekindledarcane.client;

import net.minecraft.util.ResourceLocation;

public class AssetLib {

    public static ResourceLocation backgroundTex = rl("textures/gui/background.png");

    public static ResourceLocation rl(String resourcePath) {
        return new ResourceLocation("rekindledarcane", resourcePath);
    }
}
