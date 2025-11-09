package excsi.rekindledarcane.common.entity.projectile;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import excsi.rekindledarcane.RekindledArcane;
import excsi.rekindledarcane.common.util.MathUtil;
import excsi.rekindledarcane.common.util.ParticleType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class MagicSliceProjectile extends GenericProjectile implements IEntityAdditionalSpawnData {

    public int age = 0, maxAge;

    public MagicSliceProjectile(World world) {
        super(world);
        setSize(1f, 0.5f);
    }

    public MagicSliceProjectile(World world, EntityPlayer player, int maxAge) {
        super(world, player);
        setSize(1f, 0.5f);
        this.maxAge = maxAge;
    }

    @Override
    public void onImpact(MovingObjectPosition mop) {}

    @Override
    public void onUpdate() {
        if (getThrower() == null) {
            setDead();
            return;
        }
        if (!worldObj.isRemote && age++ >= maxAge) {
            setDead();
        }
        super.onUpdate();
        double rightX = motionZ;
        double rightY = 0;
        double rightZ = -motionX;
        if (worldObj.isRemote) {
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
            for (int i = 0; i < 6; ++i) {
                double lerpX = MathUtil.lerp(i / 5.0D, prevPosX, posX);
                double lerpY = MathUtil.lerp(i / 5.0D, prevPosY, posY);
                double lerpZ = MathUtil.lerp(i / 5.0D, prevPosZ, posZ);
                RekindledArcane.proxy.addEffect(ParticleType.PARTICLE_ORB, worldObj,
                        lerpX + rightX + vec3.xCoord * 200, lerpY + rightY + vec3.yCoord * 200, lerpZ + rightZ + vec3.zCoord * 200,
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
    }
}
