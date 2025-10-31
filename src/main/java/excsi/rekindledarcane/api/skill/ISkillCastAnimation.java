package excsi.rekindledarcane.api.skill;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;

@FunctionalInterface
public interface ISkillCastAnimation {

    ISkillCastAnimation NO_OP = (player, modelBiped, timeElapsed, partialTicks) -> {};

    void animateModel(EntityPlayer player, ModelBiped modelBiped, int timeElapsed, float partialTicks);
}
