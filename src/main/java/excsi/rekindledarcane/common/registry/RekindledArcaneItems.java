package excsi.rekindledarcane.common.registry;

import excsi.rekindledarcane.common.items.SkillPointItem;
import excsi.rekindledarcane.common.items.ForsakenGreatsword;
import excsi.rekindledarcane.common.items.baubles.EncrustedPendant;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

public class RekindledArcaneItems {

    public static Item skillPoint;

    public static Item pendant;

    public static Item forsakenGreatsword;

    public static EnumAction raiseSword = EnumHelper.addAction("RaiseSword");

    public static void register() {
        skillPoint = new SkillPointItem();
        pendant = new EncrustedPendant();
        forsakenGreatsword = new ForsakenGreatsword();
    }
}

