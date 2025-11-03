package excsi.rekindledarcane.common.skill.ability;

import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.common.data.skill.CooldownData;
import excsi.rekindledarcane.api.skill.templates.CastableAbilitySkillBase;
import net.minecraft.entity.player.EntityPlayer;

public class CelestialGreatSwordStrike extends CastableAbilitySkillBase<CooldownData> {

    public CelestialGreatSwordStrike(String nameID) {
        super(nameID);
    }

    @Override
    public boolean canUse(EntityPlayer player) {
        return false;
    }

    @Override
    public void resolveSkillCast(EntityPlayer player) {

    }

    @Override
    public void onCastingStart(EntityPlayer player, Side side) {

    }

    @Override
    public void onCastTick(EntityPlayer player, int elapsedCastingTime, Side side) {

    }

    @Override
    public int getCastingTickAmount() {
        return 0;
    }

    @Override
    public CooldownData createDefaultDataInstance() {
        return null;
    }
}
