package excsi.rekindledarcane.common.registry;

import cpw.mods.fml.common.registry.EntityRegistry;
import excsi.rekindledarcane.RekindledArcane;
import excsi.rekindledarcane.common.entity.util.AoeEntity;
import excsi.rekindledarcane.common.entity.projectile.MagicSliceProjectile;
import excsi.rekindledarcane.common.entity.projectile.TracedArrowProjectile;

public class RekindledArcaneEntities {

    public static void register() {
        int id = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerModEntity(MagicSliceProjectile.class, "MagicSlice", id++, RekindledArcane.instance, 32, 20, false);
        EntityRegistry.registerModEntity(TracedArrowProjectile.class, "SpecialArrow", id++, RekindledArcane.instance, 65, 20, false);
        EntityRegistry.registerModEntity(AoeEntity.class, "AOEEntity", id++, RekindledArcane.instance, 65, 20, false);
    }
}
