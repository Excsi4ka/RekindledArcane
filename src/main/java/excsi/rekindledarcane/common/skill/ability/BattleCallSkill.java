package excsi.rekindledarcane.common.skill.ability;

import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.RekindledArcane;
import excsi.rekindledarcane.api.client.ISkillCastAnimation;
import excsi.rekindledarcane.common.data.skill.CooldownData;
import excsi.rekindledarcane.api.skill.templates.CastableAbilitySkillBase;
import excsi.rekindledarcane.common.util.MathUtil;
import excsi.rekindledarcane.common.util.ParticleType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.util.List;

public class BattleCallSkill extends CastableAbilitySkillBase<CooldownData> {

    public int radius;

    public BattleCallSkill(String nameID, int radius) {
        super(nameID, false);
        this.radius = radius;
    }

    @Override
    public boolean canUse(EntityPlayer player) {
        if(!hasThisSkill(player))
            return false;
        int cooldown = getSkillData(player).getSkillCooldown();
        return cooldown == 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void resolveSkillCast(EntityPlayer player) {
        List<EntityPlayer> players = player.worldObj.getEntitiesWithinAABB(EntityPlayer.class, MathUtil.createAABB(player, radius));
        for (EntityPlayer pl : players) {
            pl.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 100, 2));
        }
        getSkillData(player).setSkillCooldown(10);
    }

    @Override
    public void onCastingStart(EntityPlayer player, Side side) {
        if(side == Side.SERVER) return;
        RekindledArcane.proxy.addBeam(ParticleType.LIGHT_BEAM, player.worldObj, player.posX, player.posY - 1.6, player.posZ,
                (int) (255*Math.random()),
                (int) (255*Math.random()),
                (int) (170*Math.random()),
                30.0f,
                500);
    }

    @Override
    public void onCastTick(EntityPlayer player, int elapsedCastingTime, Side side) {
        if(elapsedCastingTime == getCastingTickAmount() && side == Side.CLIENT) {
            for (double d = 0; d < Math.PI * 2; d += Math.PI / 30) {
                if(player.worldObj.rand.nextDouble() < 0.70) continue;
                double x1 = player.posX + 1 * Math.cos(d);
                double z1 = player.posZ + 1 * Math.sin(d);
                double y1 = player.posY - 1.4;
                double dx = (x1 - player.posX) * 0.2;
                double dz = (z1 - player.posZ) * 0.2;
                RekindledArcane.proxy.addEffect(ParticleType.PARTICLE_ORB, player.worldObj, x1, y1, z1,
                        170, 30, 0, 200,
                        dx, 0, dz,
                        5.0f,
                        0.9f,
                        15);
            }
        }
    }

    @Override
    public int getCastingTickAmount() {
        return 60;
    }

    @Override
    public float getMovementSpeedMultiplier() {
        return 0.5f;
    }

    @Override
    public ISkillCastAnimation getAnimation() {
        return ((player, modelBiped, timeElapsed, partialTicks) -> {
            float motion = Math.min(0.65f, (timeElapsed + partialTicks) / 7f);
            modelBiped.bipedRightArm.rotateAngleX = motion;
            modelBiped.bipedRightArm.rotateAngleZ = motion;
            if(timeElapsed > 22 && timeElapsed <= 29) {
                float newTime = Math.min(timeElapsed - 22 + partialTicks, 7);
                motion = (-1.5f - modelBiped.bipedRightArm.rotateAngleX) / 7f * newTime;
                modelBiped.bipedRightArm.rotateAngleX += motion;
                motion = (-1.5f - modelBiped.bipedLeftArm.rotateAngleX) / 7f * newTime;
                modelBiped.bipedLeftArm.rotateAngleX += motion;
                motion = -modelBiped.bipedRightArm.rotateAngleZ / 7f * newTime;
                modelBiped.bipedRightArm.rotateAngleZ += motion;
                motion = 0.5f / 7 * newTime;
                modelBiped.bipedLeftArm.rotateAngleY += motion;
                modelBiped.bipedRightArm.rotateAngleY -= motion;
            }
            if(timeElapsed > 29) {
                modelBiped.bipedRightArm.rotateAngleZ = 0;
                modelBiped.bipedRightArm.rotateAngleX = -1.5f;
                modelBiped.bipedRightArm.rotateAngleY = -0.5f;
                modelBiped.bipedLeftArm.rotateAngleX = -1.5f;
                modelBiped.bipedLeftArm.rotateAngleY = 0.5f;
            }
        });
    }

    @Override
    public CooldownData createDefaultDataInstance() {
        return new CooldownData(getRegistryName(), 1, true);
    }
}
