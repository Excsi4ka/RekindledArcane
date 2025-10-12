package excsi.rekindledarcane.common.entity.projectile;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import excsi.rekindledarcane.common.registry.RekindledArcaneEffects;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class HookChainProjectile extends GenericProjectile implements IEntityAdditionalSpawnData {

    public EntityLivingBase capturedEntity;

    public int retrieveTimer = 0;

    public HookChainProjectile(World world) {
        super(world);
        this.setSize(0.25f, 0.25f);
        this.ignoreFrustumCheck = true;
    }

    public HookChainProjectile(World world, EntityPlayer thrower) {
        super(world, thrower);
        this.setSize(0.25f, 0.25f);
        this.ignoreFrustumCheck = true;
    }

    @Override
    public void onImpact(MovingObjectPosition mop) {
        if (worldObj.isRemote || mop == null || capturedEntity != null)
            return;
        if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            setDead();
            return;
        }
        if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
            Entity entity = mop.entityHit;
            if (entity == getThrower())
                return;
            if (!(entity instanceof EntityLivingBase))
                return;
            capturedEntity = (EntityLivingBase) entity;
            motionX = motionY = motionZ = 0;
            setPosition(capturedEntity.posX, capturedEntity.posY + capturedEntity.getEyeHeight() / 2, capturedEntity.posZ);
            retrieveTimer = 20;
            //velocityChanged = true;
            capturedEntity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 600, 2));
            capturedEntity.addPotionEffect(new PotionEffect(RekindledArcaneEffects.vulnerabilityEffect.getId(), 100, 10));
        }
    }

    @Override
    protected void entityInit() {}

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbtTagCompound) {}

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbtTagCompound) {}

    @Override
    public void writeSpawnData(ByteBuf data) {
        data.writeInt(getThrower().getEntityId());
        data.writeDouble(this.motionX);
        data.writeDouble(this.motionY);
        data.writeDouble(this.motionZ);
        data.writeFloat(this.rotationYaw);
        data.writeFloat(this.rotationPitch);
    }

    public void readSpawnData(ByteBuf data) {
        Entity entity = worldObj.getEntityByID(data.readInt());
        if(entity instanceof EntityPlayer) {
            setThrower((EntityPlayer) entity);
        }
        this.motionX = data.readDouble();
        this.motionY = data.readDouble();
        this.motionZ = data.readDouble();
        this.rotationYaw = data.readFloat();
        this.rotationPitch = data.readFloat();
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if(getThrower() == null)
            setDead();
        if(!worldObj.isRemote && ticksExisted > 200)
            setDead();
        if(capturedEntity != null) {
            setPosition(capturedEntity.posX, capturedEntity.posY + capturedEntity.getEyeHeight() / 2, capturedEntity.posZ);
            if(retrieveTimer == 0) {
                worldObj.playSoundEffect(posX, posY, posZ, "rekindledarcane:chain.throw", 0.8f, 0.85f);
            }
            if(retrieveTimer-- < 0) {
                EntityPlayer player = getThrower();
                double dx = player.posX - posX;
                double dy = player.posY + 1.5 - posY;
                double dz = player.posZ - posZ;
                motionX = dx * 0.25;
                motionY = dy * 0.25;
                motionZ = dz * 0.25;
                //velocityChanged = true;
                capturedEntity.motionX = motionX;
                capturedEntity.motionY = motionY;
                capturedEntity.motionZ = motionZ;
                capturedEntity.velocityChanged = true;
                if(getDistanceToEntity(getThrower()) < 2)
                    setDead();
            }
        }
    }
}
