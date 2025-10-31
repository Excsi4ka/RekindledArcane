package excsi.rekindledarcane.common.effects;

import net.minecraft.potion.Potion;

public class FrailEffect extends Potion {

    public FrailEffect(int id) {
        super(id, true, 16711680);
        setIconIndex(0,0);
        setPotionName("rekindledarcane:frail_potion");
    }
}
