package excsi.rekindledarcane.common.entity.projectile;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import excsi.rekindledarcane.RekindledArcane;
import excsi.rekindledarcane.common.util.MathUtil;
import excsi.rekindledarcane.common.util.ParticleType;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class MagicSliceProjectile extends GenericProjectile implements IEntityAdditionalSpawnData {

    public EntityPlayer shooter;

    public int age = 0, maxAge;

    public MagicSliceProjectile(World world) {
        super(world);
        setSize(1f, 0.5f);
    }

    @Override
    public void onImpact(MovingObjectPosition mop) {

    }

    @Override
    protected void entityInit() {}

    public MagicSliceProjectile(World world, EntityPlayer player, int maxAge) {
        this(world);
        setSize(1f, 0.5f);
        this.maxAge = maxAge;
        this.shooter = player;

    }

    @Override
    public void onUpdate() {
        lastTickPosX = posX;
        lastTickPosY = posY;
        lastTickPosZ = posZ;
        super.onUpdate();
        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        if(worldObj.isRemote) {
            Vec3 vec3 = Vec3.createVectorHelper(motionX, motionY, motionZ).normalize();
            MathUtil.scale(vec3, -0.02500000037252903);
            for (int i = 0; i < 6; ++i) {
                double lerpX = MathUtil.lerp(i / 5.0D, prevPosX, posX);
                double lerpY = MathUtil.lerp(i / 5.0D, prevPosY, posY);
                double lerpZ = MathUtil.lerp(i / 5.0D, prevPosZ, posZ);
                RekindledArcane.proxy.addEffect(ParticleType.PARTICLE_ORB, worldObj, lerpX, lerpY, lerpZ,
                        170,
                        90,
                        0,
                        32,
                        vec3.xCoord, vec3.yCoord, vec3.zCoord,
                        4.0f,
                        0.95f,
                        30);
            }
        }
//        if (!worldObj.isRemote && shooter != null) {
//            Vec3 vector = shooter.getLookVec().normalize();
//            MathUtil.scale(vector, 0.6f);
//            launch(vector.xCoord, vector.yCoord, vector.zCoord);
//            velocityChanged = true;
//        }
        if(!worldObj.isRemote && age++ >= maxAge) {
            setDead();
        }
        setPosition(this.posX, this.posY, this.posZ);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {

    }

    @Override
    public void setVelocity(double p_70016_1_, double p_70016_3_, double p_70016_5_) {
        super.setVelocity(p_70016_1_, p_70016_3_, p_70016_5_);
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeDouble(motionX);
        buffer.writeDouble(motionY);
        buffer.writeDouble(motionZ);
    }

    @Override
    public void readSpawnData(ByteBuf additionalData) {
        motionX = additionalData.readDouble();
        motionY = additionalData.readDouble();
        motionZ = additionalData.readDouble();
    }
}
