package excsi.rekindledarcane.api.skill;

import net.minecraft.entity.player.EntityPlayer;

/**
 * A common interface to represent all summonables from this mod.
 **/
public interface ISummon {

    EntityPlayer getOwner();

    int getLifespan();

    /**
     * Since the amount of summons a player can have active at once is limited and controlled by {@link excsi.rekindledarcane.api.RekindledArcaneAPI#MAX_SUMMONS} attribute
     * @return the amount of space this summons takes away from your total summoning capacity.
     */
    int summoningWeight();
}
