package excsi.rekindledarcane.common.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public class MathUtil {

    public static Vec3 scale(Vec3 vec3, double scale) {
        vec3.xCoord *= scale;
        vec3.yCoord *= scale;
        vec3.zCoord *= scale;
        return vec3;
    }

    public static double lerp(double pct, double start, double end) {
        return start + pct * (end - start);
    }

    public static AxisAlignedBB createAABB(Entity e, int radius) {
        return AxisAlignedBB.getBoundingBox(e.posX - radius,e.posY - radius,e.posZ - radius,
                e.posX + radius,e.posY + radius,e.posZ + radius);
    }
}
