package excsi.rekindledarcane.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import excsi.rekindledarcane.RekindledArcane;
import excsi.rekindledarcane.client.event.ClientEventHandler;
import excsi.rekindledarcane.client.fx.ParticleBatchRender;
import excsi.rekindledarcane.common.CommonProxy;
import excsi.rekindledarcane.common.util.RekindledArcaneConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

public class ClientProxy extends CommonProxy {

    public static KeyBinding activateAbilityKey = new KeyBinding("arcane.castAbility", Keyboard.KEY_NONE, RekindledArcane.MODID);

    public static KeyBinding abilityChooseScreen = new KeyBinding("arcane.openSelection", Keyboard.KEY_NONE, RekindledArcane.MODID);

    public static KeyBinding switchLeft = new KeyBinding("arcane.abilityCycleLeft", Keyboard.KEY_NONE, RekindledArcane.MODID);

    public static KeyBinding switchRight = new KeyBinding("arcane.abilityCycleRight", Keyboard.KEY_NONE, RekindledArcane.MODID);

    public static KeyBinding temp = new KeyBinding("arcane.temp", Keyboard.KEY_NONE, RekindledArcane.MODID);

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        ClientRegistry.registerKeyBinding(activateAbilityKey);
        ClientRegistry.registerKeyBinding(abilityChooseScreen);
        ClientRegistry.registerKeyBinding(switchLeft);
        ClientRegistry.registerKeyBinding(switchRight);
        ClientRegistry.registerKeyBinding(temp);

        FMLCommonHandler.instance().bus().register(new ClientEventHandler());
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        FMLCommonHandler.instance().bus().register(ParticleBatchRender.INSTANCE);
        MinecraftForge.EVENT_BUS.register(ParticleBatchRender.INSTANCE);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        Minecraft.getMinecraft()
                .getTextureManager()
                .loadTextureMap(AssetLib.skillIconTextureAtlas,
                        new TextureMap(RekindledArcaneConfig.skillIconsTextureAtlasID,"textures/skills"));
    }
}
