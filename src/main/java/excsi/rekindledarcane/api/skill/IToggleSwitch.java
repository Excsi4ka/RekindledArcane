package excsi.rekindledarcane.api.skill;

import net.minecraft.entity.player.EntityPlayer;

/**
 * Skills that implement this interface can be toggled in the skill menu
 * This only really makes sense for skills that work passively like event based skills
 */
public interface IToggleSwitch {

    /**
     * Do some action when the skill is toggled
     * Gets called on both server and client to synchronize
     * @param toggled is set to the opposite of what {@link #isToggled(EntityPlayer)} returns
     */
    void toggle(EntityPlayer player, boolean toggled);

    /**
     * @return true if the skill can be considered active
     */
    boolean isToggled(EntityPlayer player);

}
