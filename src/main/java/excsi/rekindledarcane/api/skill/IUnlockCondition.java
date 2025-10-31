package excsi.rekindledarcane.api.skill;

import net.minecraft.entity.player.EntityPlayer;

/**
 * Skills that implement this interface can require an additional condition in order to unlock
 */
public interface IUnlockCondition {

    boolean hasCondition(EntityPlayer player);

}
