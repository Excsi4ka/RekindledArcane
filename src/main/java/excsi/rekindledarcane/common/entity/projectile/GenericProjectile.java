package excsi.rekindledarcane.common.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

public abstract class GenericProjectile extends Entity {

    private EntityPlayer thrower;

    public GenericProjectile(World world) {
        super(world);
    }

    public GenericProjectile(World world, EntityPlayer thrower) {
        this(world);
        this.thrower = thrower;
    }

    public abstract void onImpact(MovingObjectPosition mop);

    protected void entityInit() {}

    protected void readEntityFromNBT(NBTTagCompound nbtTagCompound) {}

    protected void writeEntityToNBT(NBTTagCompound nbtTagCompound) {}

    @Override
    public void onUpdate() {
        super.onUpdate();
//        posX += motionX;
//        posZ += motionZ;
//        posY += motionY;
        setPosition(posX + motionX, posY + motionY, posZ + motionZ);
        checkCollision();
    }

    //terrible mojank code
    public void checkCollision() {
        Vec3 vec3 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        Vec3 vec31 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec3, vec31);
        vec3 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        vec31 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

        if (movingobjectposition != null)
        {
            vec31 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
        }

        if (!this.worldObj.isRemote)
        {
            Entity entity = null;
            List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
            double d0 = 0.0D;
            EntityLivingBase entitylivingbase = getThrower();

            for (int j = 0; j < list.size(); ++j)
            {
                Entity entity1 = (Entity)list.get(j);

                if (entity1.canBeCollidedWith() && (entity1 != entitylivingbase))
                {
                    float f = 0.3F;
                    AxisAlignedBB axisalignedbb = entity1.boundingBox.expand(f, f, f);
                    MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3, vec31);
                    if (movingobjectposition1 != null)
                    {
                        double d1 = vec3.distanceTo(movingobjectposition1.hitVec);

                        if (d1 < d0 || d0 == 0.0D)
                        {
                            entity = entity1;
                            d0 = d1;
                        }
                    }
                }
            }

            if (entity != null)
            {
                movingobjectposition = new MovingObjectPosition(entity);
            }
        }

        if (movingobjectposition != null)
        {
            if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.worldObj.getBlock(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ) == Blocks.portal)
            {
                this.setInPortal();
            }
            else
            {
                this.onImpact(movingobjectposition);
            }
        }
    }

    public EntityPlayer getThrower() {
        return thrower;
    }

    public void setThrower(EntityPlayer player) {
        thrower = player;
    }

    public GenericProjectile setSpeed(double x, double y, double z) {
        motionX = x;
        motionY = y;
        motionZ = z;
        return this;
    }

    public GenericProjectile setPos(double x, double y, double z) {
        setPosition(x, y, z);
        return this;
    }
}
