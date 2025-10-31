package excsi.rekindledarcane.common.registry;

import excsi.rekindledarcane.common.effects.FrailEffect;
import excsi.rekindledarcane.common.util.RekindledArcaneConfig;
import net.minecraft.potion.Potion;

public class RekindledArcaneEffects {

    public static Potion frailPotion;

    public static void register() {
        frailPotion = new FrailEffect(RekindledArcaneConfig.vulnerabilityPotionId);
    }
}
