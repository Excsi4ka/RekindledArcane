package excsi.rekindledarcane.api.event;

import cpw.mods.fml.common.eventhandler.Cancelable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;

@Cancelable
public class PotionApplyEvent extends LivingEvent {

    public PotionEffect potionEffect;

    public PotionApplyEvent(EntityLivingBase entity, PotionEffect potionEffect) {
        super(entity);
        this.potionEffect = potionEffect;
    }

    public static boolean firePotionApplyEvent(EntityLivingBase entityLivingBase, PotionEffect potionEffect) {
        return MinecraftForge.EVENT_BUS.post(new PotionApplyEvent(entityLivingBase, potionEffect));
    }
}
