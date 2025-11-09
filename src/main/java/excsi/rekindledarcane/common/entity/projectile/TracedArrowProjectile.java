package excsi.rekindledarcane.common.entity.projectile;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import excsi.rekindledarcane.RekindledArcane;
import excsi.rekindledarcane.common.util.MathUtil;
import excsi.rekindledarcane.common.util.ParticleType;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TracedArrowProjectile extends EntityArrow implements IEntityAdditionalSpawnData {
    public TracedArrowProjectile(World p_i1753_1_) {
        super(p_i1753_1_);
    }

    public TracedArrowProjectile(World p_i1754_1_, double p_i1754_2_, double p_i1754_4_, double p_i1754_6_) {
        super(p_i1754_1_, p_i1754_2_, p_i1754_4_, p_i1754_6_);
    }

    public TracedArrowProjectile(World p_i1755_1_, EntityLivingBase p_i1755_2_, EntityLivingBase p_i1755_3_, float p_i1755_4_, float p_i1755_5_) {
        super(p_i1755_1_, p_i1755_2_, p_i1755_3_, p_i1755_4_, p_i1755_5_);
    }

    public TracedArrowProjectile(World p_i1756_1_, EntityLivingBase p_i1756_2_, float p_i1756_3_) {
        super(p_i1756_1_, p_i1756_2_, p_i1756_3_);
    }

    public void writeSpawnData(ByteBuf data) {
        data.writeDouble(this.motionX);
        data.writeDouble(this.motionY);
        data.writeDouble(this.motionZ);
        data.writeFloat(this.rotationYaw);
        data.writeFloat(this.rotationPitch);
    }

    public void readSpawnData(ByteBuf data) {
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
        if(!worldObj.isRemote && ticksExisted > 200) setDead();
        if(worldObj.isRemote && !onGround) {
            Vec3 vec3 = Vec3.createVectorHelper(motionX, motionY, motionZ).normalize();
            MathUtil.scale(vec3, -0.02500000037252903);
            for(int i = 0; i < 6; ++i) {
                double lerpX = MathUtil.lerp(i / 5.0D, prevPosX, posX);
                double lerpY = MathUtil.lerp(i / 5.0D, prevPosY, posY);
                double lerpZ = MathUtil.lerp(i / 5.0D, prevPosZ, posZ);
                RekindledArcane.proxy.addEffect(ParticleType.PARTICLE_ORB, worldObj, lerpX, lerpY, lerpZ,
                        50,
                        50,
                        50,
                        32,
                        vec3.xCoord, vec3.yCoord, vec3.zCoord,
                        5.0f,
                        0.95f,
                        15);
            }
        }
    }
}
