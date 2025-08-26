package excsi.rekindledarcane.common.entity.summons;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import excsi.rekindledarcane.api.skill.ISummon;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public abstract class BaseSummonEntity extends EntityLiving implements ISummon, IEntityAdditionalSpawnData {

    public BaseSummonEntity(World world) {
        super(world);
    }

    @Override
    public EntityPlayer getOwner() {
        return null;
    }

    @Override
    public int summoningWeight() {
        return 1;
    }
}
