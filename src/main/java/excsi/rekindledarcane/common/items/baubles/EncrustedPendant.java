package excsi.rekindledarcane.common.items.baubles;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class EncrustedPendant extends Item implements IBauble {

    @SideOnly(Side.CLIENT)
    public IIcon pendant, gem;

    public EncrustedPendant() {
        setUnlocalizedName("encrustedPendant");
        setCreativeTab(CreativeTabs.tabCombat);
        setMaxStackSize(1);
        GameRegistry.registerItem(this, "encrustedPendant");
    }

//    @Override
//    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
//        player.worldObj.spawnEntityInWorld(new EntityLightningBolt(entity.worldObj,entity.posX,entity.posY,entity.posZ));
//        return super.onLeftClickEntity(stack, player, entity);
//    }


    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister registry) {
        pendant = registry.registerIcon("rekindledarcane:encrustedPendant");
        gem = registry.registerIcon("rekindledarcane:encrustedPendantGem");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int meta, int pass) {
        if(pass == 0)
            return pendant;
        return gem;
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int pass) {
        if(pass == 1) {
            return -11603133;
        }
        return super.getColorFromItemStack(stack, pass);
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.AMULET;
    }

    @Override
    public void onWornTick(ItemStack itemStack, EntityLivingBase entityLivingBase) {

    }

    @Override
    public void onEquipped(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        System.out.println("equip");
    }

    @Override
    public void onUnequipped(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        System.out.println("unequip");

    }

    @Override
    public boolean canEquip(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return true;
    }
}
