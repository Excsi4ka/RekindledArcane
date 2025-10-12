package excsi.rekindledarcane.api.skill;

import net.minecraft.entity.player.EntityPlayer;

/**
 * A skill that can be equipped and activated/cast
 */
public interface IActiveAbilitySkill extends ISkill, ISkillDataHandler {

    boolean canUse(EntityPlayer player);

    /**
     * Called when ability gets activated or if it's a {@link ICastableAbility} when casting is done.
     * Only server side.
     */
    void resolveSkillCast(EntityPlayer player);

}
