package excsi.rekindledarcane.common.integration.twilightforest;

import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.api.skill.IUnlockCondition;
import excsi.rekindledarcane.common.data.skill.CooldownData;
import excsi.rekindledarcane.api.skill.templates.CastableAbilitySkillBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class LichShieldsSkill extends CastableAbilitySkillBase<CooldownData> implements IUnlockCondition {

    public LichShieldsSkill(String nameID) {
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

    @Override
    public boolean hasCondition(EntityPlayer player) {
        if (player.worldObj.isRemote)
            return false;
        EntityPlayerMP playerMP = (EntityPlayerMP) player;
        //return playerMP.func_147099_x().hasAchievementUnlocked();
        return false;
    }
}
