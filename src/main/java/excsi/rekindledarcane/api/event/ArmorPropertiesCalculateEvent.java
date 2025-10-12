package excsi.rekindledarcane.api.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class ArmorPropertiesCalculateEvent extends PlayerEvent {

    public ISpecialArmor.ArmorProperties[] properties;

    public ArmorPropertiesCalculateEvent(EntityPlayer player, ISpecialArmor.ArmorProperties[] properties) {
        super(player);
        this.properties = properties;
    }

    public static void fireArmorRecalculateEvent(EntityLivingBase entityLivingBase, ISpecialArmor.ArmorProperties[] props) {
        if(!(entityLivingBase instanceof EntityPlayer))
            return;
        MinecraftForge.EVENT_BUS.post(new ArmorPropertiesCalculateEvent((EntityPlayer) entityLivingBase, props));
    }
}
