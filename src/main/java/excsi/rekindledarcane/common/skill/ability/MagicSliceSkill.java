package excsi.rekindledarcane.common.skill.ability;

import excsi.rekindledarcane.common.data.skill.CooldownData;
import excsi.rekindledarcane.common.entity.projectiles.MagicSliceProjectile;
import excsi.rekindledarcane.common.util.MathUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

public class MagicSliceSkill extends AbilitySkillBase<CooldownData> {

    public MagicSliceSkill(String nameID) {
        super(nameID);
    }

    @Override
    public boolean canUse(EntityPlayer player) {
        if(!hasThisSkill(player))
            return false;
        int cooldown = getSkillData(player).getSkillCooldown();
        return cooldown == 0;
    }

    @Override
    public void activateAbility(EntityPlayer player) {
        Vec3 vector = player.getLookVec().normalize();
        MathUtil.scale(vector, 0.6f);
        //HomingOrbProjectileEntity entity = new HomingOrbProjectileEntity(player.worldObj, player.posX, player.posY, player.posZ, 0,0,0, player, 200, 64);
        //player.worldObj.spawnEntityInWorld(entity);
        player.worldObj.spawnEntityInWorld(new MagicSliceProjectile(player.worldObj, player, 500)
                .setCoordinates(player.posX, player.posY + 2.5, player.posZ)
                .launch(vector.xCoord, vector.yCoord, vector.zCoord));
    }

    @Override
    public void afterActivate(EntityPlayer player) {
        getSkillData(player).setSkillCooldown(10);
    }

    @Override
    public CooldownData createDefaultDataInstance() {
        return new CooldownData(getRegistryName(), 1,true);
    }
}
