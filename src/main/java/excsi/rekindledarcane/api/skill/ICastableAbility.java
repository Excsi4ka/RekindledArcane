package excsi.rekindledarcane.api.skill;

import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.api.client.ISkillCastAnimation;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Denotes an ability skill that should take time to cast and have some animation and tick functionality if needed
 */
public interface ICastableAbility extends IActiveAbilitySkill {

    void onCastingStart(EntityPlayer player, Side side);

    void onCastTick(EntityPlayer player, Side side);

    int getCastingTickAmount();

    ISkillCastAnimation getAnimation();

}
