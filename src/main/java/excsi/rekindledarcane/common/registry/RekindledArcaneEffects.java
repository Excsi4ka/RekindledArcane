package excsi.rekindledarcane.common.registry;

import excsi.rekindledarcane.common.effects.VulnerabilityEffect;
import excsi.rekindledarcane.common.util.RekindledArcaneConfig;
import net.minecraft.potion.Potion;

public class RekindledArcaneEffects {

    public static Potion vulnerabilityEffect;

    public static void register() {
        vulnerabilityEffect = new VulnerabilityEffect(RekindledArcaneConfig.vulnerabilityPotionId);
    }
}
