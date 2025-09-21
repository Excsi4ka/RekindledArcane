package excsi.rekindledarcane.api.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class ChangeHeldItemEvent extends PlayerEvent {

    public ItemStack equippedStack;

    public ChangeHeldItemEvent(EntityPlayer player, ItemStack equippedStack) {
        super(player);
        this.equippedStack = equippedStack;
    }

    public static void fireChangeItemEvent(EntityPlayer player) {
        MinecraftForge.EVENT_BUS.post(new ChangeHeldItemEvent(player, player.getHeldItem()));
    }
}
