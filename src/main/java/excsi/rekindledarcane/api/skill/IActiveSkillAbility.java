package excsi.rekindledarcane.api.skill;

import net.minecraft.entity.player.EntityPlayer;

public interface IActiveSkillAbility {

    boolean canUse(EntityPlayer player);

    void activateAbility(EntityPlayer player);
}
