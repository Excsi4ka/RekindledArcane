package excsi.rekindledarcane.common.skill.ability;

import excsi.rekindledarcane.common.data.skill.ToggleAndCooldownData;
import net.minecraft.entity.player.EntityPlayer;

public class ArrowBarrageSkill extends InstantAbilitySkillBase<ToggleAndCooldownData> {

    public ArrowBarrageSkill(String nameID) {
        super(nameID);
    }

    @Override
    public ToggleAndCooldownData createDefaultDataInstance() {
        return new ToggleAndCooldownData(getRegistryName(), 100);
    }

    @Override
    public boolean canUse(EntityPlayer player) {
        if(!hasThisSkill(player))
            return false;
        ToggleAndCooldownData data = getSkillData(player);
        return !data.isToggled() && data.getSkillCooldown() == 0;
    }

    @Override
    public void resolveSkillCast(EntityPlayer player) {
        getSkillData(player).setToggled(true);
        getSkillData(player).setSkillCooldown(400);
    }
}
