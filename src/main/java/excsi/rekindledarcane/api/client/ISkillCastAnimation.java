package excsi.rekindledarcane.api.client;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

@FunctionalInterface
public interface ISkillCastAnimation {

    ISkillCastAnimation NO_OP = (player, modelBiped, partialTicks) -> {};

    void animateModel(EntityPlayer player, ModelBiped modelBiped, float partialTicks);
}
