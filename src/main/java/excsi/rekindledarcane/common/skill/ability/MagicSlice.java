package excsi.rekindledarcane.common.skill.ability;

import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.api.client.ISkillCastAnimation;
import excsi.rekindledarcane.common.data.skill.CooldownData;
import excsi.rekindledarcane.common.entity.util.AoeEntity;
import excsi.rekindledarcane.api.skill.templates.CastableAbilitySkillBase;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.Color;

public class MagicSlice extends CastableAbilitySkillBase<CooldownData> {

    public MagicSlice(String nameID) {
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
    public void resolveSkillCast(EntityPlayer player) {
        player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "fire.ignite", 1f, 1f);
        AoeEntity entity = new AoeEntity(player.worldObj, new Color(50, 241, 0, 218).getRGB(), player);
        entity.setPosition(player.posX, player.posY, player.posZ);
        player.worldObj.spawnEntityInWorld(entity);
        getSkillData(player).setSkillCooldown(100);
    }

    @Override
    public CooldownData createDefaultDataInstance() {
        return new CooldownData(getRegistryName(), 1,true);
    }

    @Override
    public void onCastingStart(EntityPlayer player, Side side) {
        player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "fire.fire", 1f, 1f);
    }

    @Override
    public void onCastTick(EntityPlayer player, int elapsedCastingTime, Side side) {
        if(side == Side.SERVER)
            return;
//        RekindledArcane.proxy.addEffect(ParticleType.PARTICLE_ORB, player.worldObj, player.posX, player.posY, player.posZ,
//                170,
//                90,
//                0,
//                160,
//                Math.random() * (player.worldObj.rand.nextBoolean() ? -0.2f : 0.2f),
//                Math.random() * (player.worldObj.rand.nextBoolean() ? -0.2f : 0.2f),
//                Math.random() * (player.worldObj.rand.nextBoolean() ? -0.2f : 0.2f),
//                5.0f,
//                0.90f,
//                15);
    }

    @Override
    public int getCastingTickAmount() {
        return 100;
    }

    @Override
    public float getMovementSpeedMultiplier() {
        return 0.1f;
    }

    @Override
    public ISkillCastAnimation getAnimation() {
        return (player, modelBiped, timeElapsed, partialTicks) -> {
            float raising = Math.min(1f, (timeElapsed + partialTicks) / 10f);
            modelBiped.bipedRightArm.rotateAngleX = -1.75f * raising;
            modelBiped.bipedRightArm.rotateAngleY = -0.5f * raising;
            modelBiped.bipedLeftArm.rotateAngleX = -1.75f * raising;
            modelBiped.bipedLeftArm.rotateAngleY = 0.5f * raising;
        };
    }
}