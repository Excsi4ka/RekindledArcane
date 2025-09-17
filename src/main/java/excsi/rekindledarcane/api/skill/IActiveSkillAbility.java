package excsi.rekindledarcane.api.skill;

import net.minecraft.entity.player.EntityPlayer;

public interface IActiveSkillAbility extends ISkill, ISkillDataHandler {

    boolean canUse(EntityPlayer player);

    void activateAbility(EntityPlayer player);

    void afterActivate(EntityPlayer player);
}
