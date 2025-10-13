package excsi.rekindledarcane.common.skill.ability;

import excsi.rekindledarcane.common.data.skill.CooldownData;
import excsi.rekindledarcane.common.entity.projectile.HookChainProjectile;
import excsi.rekindledarcane.common.util.MathUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

public class HookChainSkill extends InstantAbilitySkillBase<CooldownData> {

    public HookChainSkill(String nameID) {
        super(nameID);
    }

    @Override
    public boolean canUse(EntityPlayer player) {
        if (!hasThisSkill(player))
            return false;
        int cooldown = getSkillData(player).getSkillCooldown();
        return cooldown == 0;
    }

    @Override
    public void resolveSkillCast(EntityPlayer player) {
        Vec3 vec = player.getLookVec();
        MathUtil.scale(vec, 0.8);
        Entity projectile = new HookChainProjectile(player.worldObj, player)
                .setPos(player.posX + vec.xCoord * 2, player.posY + 1.5 + vec.yCoord * 2, player.posZ + vec.zCoord * 2)
                .setSpeed(vec.xCoord, vec.yCoord, vec.zCoord);
        player.worldObj.spawnEntityInWorld(projectile);
        player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "rekindledarcane:chain.throw", 0.8f, 0.65f);
        getSkillData(player).setSkillCooldown(60);
    }

    @Override
    public CooldownData createDefaultDataInstance() {
        return new CooldownData(getRegistryName(), 1, true);
    }
}
