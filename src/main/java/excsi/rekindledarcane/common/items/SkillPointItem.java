package excsi.rekindledarcane.common.items;

import cpw.mods.fml.common.registry.GameRegistry;
import excsi.rekindledarcane.common.data.player.PlayerData;
import excsi.rekindledarcane.common.data.player.PlayerDataManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SkillPointItem extends Item {

    public SkillPointItem() {
        setTextureName("minecraft:stick");
        setCreativeTab(CreativeTabs.tabRedstone);
        GameRegistry.registerItem(this, "skillPoint");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer player) {
        if(p_77659_2_.isRemote)
            return p_77659_1_;
        PlayerData data = PlayerDataManager.getPlayerData(player);
        data.addSkillPoints(player,1,true);

        return p_77659_1_;
    }
}
