package excsi.rekindledarcane.api.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * Event which gets injected into Thaumcraft's {@link thaumcraft.common.lib.events.EventHandlerRunic} if Thaumcraft is present.
 * {@link #runicShieldAmount} determines the baseline amount of runic shield a player has, even if the player
 * doesn't have any armor/baubles that gives runic shielding.
 **/
public class RunicShieldAmountEvent extends PlayerEvent {

    public int runicShieldAmount;

    public RunicShieldAmountEvent(EntityPlayer player, int runicShieldAmount) {
        super(player);
        this.runicShieldAmount = runicShieldAmount;
    }

    public static int fireRunicShieldEvent(EntityPlayer player, int currentRunicShield) {
        RunicShieldAmountEvent event = new RunicShieldAmountEvent(player, currentRunicShield);
        MinecraftForge.EVENT_BUS.post(event);
        return event.runicShieldAmount;
    }
}
