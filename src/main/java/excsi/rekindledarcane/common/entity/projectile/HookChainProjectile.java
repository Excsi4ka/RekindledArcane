package excsi.rekindledarcane.common.entity.projectile;

import excsi.rekindledarcane.common.registry.RekindledArcaneEffects;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class HookChainProjectile extends GenericProjectile {

    public EntityLivingBase capturedEntity;

    public int retrieveTimer = 0;

    public HookChainProjectile(World world) {
        super(world);
        this.setSize(0.25f, 0.25f);
        this.ignoreFrustumCheck = true;
        this.renderDistanceWeight = 2;
    }

    public HookChainProjectile(World world, EntityPlayer thrower) {
        super(world, thrower);
        this.setSize(0.25f, 0.25f);
        this.ignoreFrustumCheck = true;
        this.renderDistanceWeight = 2;
    }

    @Override
    public void onImpact(MovingObjectPosition mop) {
        if (worldObj.isRemote)
            return;
        if (mop == null || capturedEntity != null)
            return;
        if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            handleBlockImpact(mop);
        }
        if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
            handleEntityImpact(mop);
        }
    }

    @Override
    public boolean writeToNBTOptional(NBTTagCompound tagCompound) {
        return false;  //returns false to not save entity at all
    }

    @Override
    public void onUpdate() {
        if (getThrower() == null) {
            setDead();
            return;
        }
        super.onUpdate();
        if (worldObj.isRemote)
            return;
        if (getDistanceToEntity(getThrower()) > 20 && capturedEntity == null || ticksExisted > 200) {
            setDead();
            return;
        }
        if (capturedEntity == null)
            return;
        EntityPlayer player = getThrower();
        setPosition(capturedEntity.posX, capturedEntity.posY + capturedEntity.getEyeHeight() / 2, capturedEntity.posZ);
        if (retrieveTimer == 0) {
            worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "rekindledarcane:chain.throw", 0.35f, 0.85f);
        }
        if (retrieveTimer-- < 0) {
            double dx = player.posX - posX;
            double dy = player.posY + 1.5 - posY;
            double dz = player.posZ - posZ;
//            motionX = dx * 0.25;
//            motionY = dy * 0.25;
//            motionZ = dz * 0.25;
            capturedEntity.motionX = dx * 0.25;
            capturedEntity.motionY = dy * 0.25;
            capturedEntity.motionZ = dz * 0.25;
            capturedEntity.velocityChanged = true;
            if (getDistanceToEntity(getThrower()) < 2) {
                setDead();
            }
        }
    }

    public void handleBlockImpact(MovingObjectPosition mop) {
        Block block = worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
        Material material = block.getMaterial();
        if (material == Material.air || material == Material.grass || material == Material.water || material == Material.plants || material == Material.vine)
            return;
        setDead();
    }

    public void handleEntityImpact(MovingObjectPosition mop) {
        Entity entity = mop.entityHit;
        if (entity == getThrower())
            return;
        if (!(entity instanceof EntityLivingBase))
            return;
        EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
        if (entityLivingBase.isDead || entityLivingBase.getHealth() <= 0)
            return;
        capturedEntity = entityLivingBase;
        motionX = motionY = motionZ = 0;
        setPosition(capturedEntity.posX, capturedEntity.posY + capturedEntity.getEyeHeight() / 2, capturedEntity.posZ);
        retrieveTimer = 20;
        capturedEntity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 600, 2));
        capturedEntity.addPotionEffect(new PotionEffect(RekindledArcaneEffects.frailPotion.getId(), 100, 10));
    }
}
