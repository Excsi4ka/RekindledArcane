package excsi.rekindledarcane.common.registry;

import cpw.mods.fml.common.registry.EntityRegistry;
import excsi.rekindledarcane.RekindledArcane;
import excsi.rekindledarcane.common.entity.projectiles.MagicSliceProjectile;

public class RekindledArcaneEntities {

    public static void register() {
        int id = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerModEntity(MagicSliceProjectile.class, "MagicSlice", id++, RekindledArcane.instance, 32, 20, false);
    }
}
