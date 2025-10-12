package excsi.rekindledarcane.common.items;

import com.google.common.collect.Multimap;
import cpw.mods.fml.common.registry.GameRegistry;
import excsi.rekindledarcane.api.RekindledArcaneAPI;
import excsi.rekindledarcane.client.util.ScreenShakeManager;
import excsi.rekindledarcane.common.registry.RekindledArcaneItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

import java.util.UUID;

public class ForsakenGreatsword extends ItemSword {

    public static final UUID toolReachID = UUID.fromString("fb021258-7c7f-4deb-a5f5-febdb6725f42");

    public ForsakenGreatsword() {
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

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if(player.worldObj.isRemote) {
            ScreenShakeManager.INSTANCE.startShaking(4);
        }
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Multimap getAttributeModifiers(ItemStack stack) {
        Multimap multimap = super.getAttributeModifiers(stack);
        multimap.put(RekindledArcaneAPI.REACH_DISTANCE.getAttributeUnlocalizedName(), new AttributeModifier(toolReachID, "WeaponReach", 1, 0));
        return multimap;
    }
}
