package excsi.rekindledarcane.common.entity.util;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import excsi.rekindledarcane.RekindledArcane;
import excsi.rekindledarcane.common.util.ParticleType;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class AoeEntity extends Entity implements IEntityAdditionalSpawnData {

    public int rgba;

    public EntityPlayer owner;

    public AoeEntity(World world) {
        super(world);
    }

    public AoeEntity(World world, int rgb, EntityPlayer owner) {
        super(world);
        this.rgba = rgb;
        this.owner = owner;
    }

    @Override
    protected void entityInit() {

    }

    @Override
    public void onUpdate() {
        if(!worldObj.isRemote && ticksExisted > 500) setDead();
        int radius = 4;
        if(worldObj.isRemote) {
            for (double d = 0; d < Math.PI * 2; d += Math.PI / (radius*10)) {
                double x1 = posX + radius * Math.cos(d);
                double z1 = posZ + radius * Math.sin(d);
                double y1 = posY + 0.3;
                RekindledArcane.proxy.addEffect(ParticleType.PARTICLE_ORB, worldObj, x1, y1, z1,
                        170,
                        90,
                        0,
                        32,
                        5.0f,
                        0.99f,
                        15);
                if(Math.random() > 0.3) continue;
                RekindledArcane.proxy.addEffect(ParticleType.PARTICLE_ORB, worldObj, x1, y1 + 0.15, z1,
                        170,
                        90,
                        0,
                        70, //32
                        0,0.04,0,
                        7.0f,
                        0.95f,
                        30);
//                        (int) (100*Math.random()),
//                        (int) (90*Math.random()),
//                        (int) (170*Math.random()),
            }
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {

    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeInt(rgba);
    }

    @Override
    public void readSpawnData(ByteBuf additionalData) {
        rgba = additionalData.readInt();
    }
}
