package excsi.rekindledarcane.common.skill.ability;

import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.api.client.ISkillCastAnimation;
import excsi.rekindledarcane.common.data.skill.CooldownData;
import excsi.rekindledarcane.common.entity.projectile.GenericProjectile;
import excsi.rekindledarcane.common.entity.projectile.MagicSliceProjectile;
import excsi.rekindledarcane.api.skill.templates.CastableAbilitySkillBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

public class MagicSlice extends CastableAbilitySkillBase<CooldownData> {

    public MagicSlice(String nameID) {
        super(nameID, false);
    }

    @Override
    public boolean canUse(EntityPlayer player) {
        if(!hasThisSkill(player))
            return false;
        int cooldown = getSkillData(player).getSkillCooldown();
        return cooldown == 0;
    }

    @Override
    public void resolveSkillCast(EntityPlayer player) {
        Vec3 vec = player.getLookVec();
        GenericProjectile entity = new MagicSliceProjectile(player.worldObj, player, 100)
                .setPos(player.posX + vec.xCoord, player.posY + 1.5 + vec.yCoord, vec.zCoord + player.posZ)
                .setSpeed(vec.xCoord, vec.yCoord, vec.zCoord);
        player.worldObj.spawnEntityInWorld(entity);
        getSkillData(player).setSkillCooldown(10);
    }

    @Override
    public CooldownData createDefaultDataInstance() {
        return new CooldownData(getRegistryName(), 1,true);
    }

    @Override
    public void onCastingStart(EntityPlayer player, Side side) {}

    @Override
    public void onCastTick(EntityPlayer player, int elapsedCastingTime, Side side) {}

    @Override
    public int getCastingTickAmount() {
        return 100;
    }

    @Override
    public ISkillCastAnimation getAnimation() {
        return (player, modelBiped, timeElapsed, partialTicks) -> {
//            float raising = (float) Math.max(0f, 2.75 - (timeElapsed + partialTicks) / 3);
//            modelBiped.bipedRightArm.rotateAngleZ = 1.5f;
//            modelBiped.bipedRightArm.rotateAngleX = -raising;
            modelBiped.bipedRightArm.rotateAngleZ = 2.5f;
            modelBiped.bipedLeftArm.rotateAngleZ = -2.5f;
        };
    }
}