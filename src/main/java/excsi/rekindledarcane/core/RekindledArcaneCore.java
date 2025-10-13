package excsi.rekindledarcane.core;

import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

@IFMLLoadingPlugin.Name(value = "RekindledArcaneCore")
@IFMLLoadingPlugin.TransformerExclusions({"excsi.rekindledarcane.core"})
@IFMLLoadingPlugin.SortingIndex(value = 1001)
@IFMLLoadingPlugin.MCVersion(value = "1.7.10")
public class RekindledArcaneCore implements IFMLLoadingPlugin {

    public static final Logger LOG = LogManager.getLogger("RekindledArcaneCore");

    public static boolean isDevEnvironment;

    public static Side SIDE = FMLLaunchHandler.side();

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{RekindledArcaneMainTransformer.class.getName()};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        isDevEnvironment = !(boolean)data.get("runtimeDeobfuscationEnabled");
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
