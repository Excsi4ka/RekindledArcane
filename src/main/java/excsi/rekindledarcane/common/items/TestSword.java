package excsi.rekindledarcane.common.items;

import cpw.mods.fml.common.registry.GameRegistry;
import excsi.rekindledarcane.common.registry.RekindledArcaneItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class TestSword extends ItemSword {
    public TestSword() {
        super(ToolMaterial.EMERALD);
        setCreativeTab(CreativeTabs.tabCombat);
        setTextureName("minecraft:diamond_sword");
        setUnlocalizedName("testSword");
        GameRegistry.registerItem(this,"testSword");
    }

    @Override
    public EnumAction getItemUseAction(ItemStack p_77661_1_) {
        return RekindledArcaneItems.raiseSword;
    }

}
