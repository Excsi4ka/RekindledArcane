package excsi.rekindledarcane.common.skill.ability;

import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.api.client.ISkillCastAnimation;
import excsi.rekindledarcane.common.data.skill.CooldownData;
import excsi.rekindledarcane.common.entity.projectile.MagicSliceProjectile;
import excsi.rekindledarcane.common.util.MathUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class MagicSliceSkill extends CastableAbilitySkill<CooldownData> {

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
        //HomingOrbProjectileEntity entity = new HomingOrbProjectileEntity(player.worldObj, player.posX, player.posY, player.posZ, 0,0,0, player, 200, 64);
        //player.worldObj.spawnEntityInWorld(entity);
//        TracedArrowProjectile projectile = new TracedArrowProjectile(player.worldObj, player, 1.3f);
//        projectile.setPosition(player.posX + vector.xCoord,player.posY + vector.yCoord + 1.5,player.posZ + vector.zCoord);
//        projectile.setDamage(30);
//        player.worldObj.spawnEntityInWorld(projectile);
//        AoeEntity entity = new AoeEntity(player.worldObj, new Color(50, 241, 0, 218).getRGB(), player);
//        entity.setPosition(player.posX, player.posY, player.posZ);
//        player.worldObj.spawnEntityInWorld(entity);
        getSkillData(player).setSkillCooldown(100);
    }

    @Override
    public CooldownData createDefaultDataInstance() {
        return new CooldownData(getRegistryName(), 1,true);
    }

    @Override
    public void onCastingStart(EntityPlayer player, Side side) {}

    @Override
    public void onCastTick(EntityPlayer player, Side side) {
        if(side == Side.CLIENT)
            return;
        if(player.ticksExisted % 10 != 0)
            return;
        Vec3 vector = player.getLookVec().normalize();
        MathUtil.scale(vector, 1f);
        player.worldObj.spawnEntityInWorld(new MagicSliceProjectile(player.worldObj, player, 500)
                .setCoordinates(player.posX, player.posY + 1.5, player.posZ)
                .launch(vector.xCoord, vector.yCoord, vector.zCoord));
    }

    @Override
    public int getCastingTickAmount() {
        return 100;
    }

    @Override
    public ISkillCastAnimation getAnimation() {
        return (player, modelBiped, timeElapsed, partialTicks) -> {
            //modelBiped.bipedRightArm.rotateAngleY = MathHelper.sin((player.ticksExisted + partialTicks) * 0.65f) * 0.33f;
            //modelBiped.bipedRightArm.rotateAngleZ = -MathHelper.sin((player.ticksExisted + partialTicks) * 0.65f) * 0.33f;
            float move = -(timeElapsed + partialTicks) * 0.35f;
            modelBiped.bipedRightArm.rotateAngleZ = Math.max(move, -5);
            modelBiped.bipedLeftArm.rotateAngleZ = Math.max(move, 5);
//            modelBiped.bipedLeftArm.rotateAngleY = -MathHelper.sin((player.ticksExisted + partialTicks) * 0.65f) * 0.33f;
//            modelBiped.bipedLeftArm.rotateAngleZ = -MathHelper.sin((player.ticksExisted + partialTicks) * 0.65f) * 0.33f;
//            modelBiped.bipedLeftArm.rotateAngleX = 3f;
        };
    }
}