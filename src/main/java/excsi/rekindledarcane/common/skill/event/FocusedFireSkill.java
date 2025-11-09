package excsi.rekindledarcane.common.skill.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import excsi.rekindledarcane.api.data.skill.SkillData;
import excsi.rekindledarcane.api.skill.templates.EventWithDataSkillBase;
import excsi.rekindledarcane.common.skill.event.FocusedFireSkill.FocusedFireData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class FocusedFireSkill extends EventWithDataSkillBase<FocusedFireData> {

    public FocusedFireSkill(String nameID) {
        super(nameID);
    }

    @Override
    public FocusedFireData createDefaultDataInstance() {
        return new FocusedFireData(getRegistryName());
    }

    @SubscribeEvent
    public void onDamage(LivingHurtEvent event) {
        if (!event.source.isProjectile())
            return;
        if (!(event.source.getEntity() instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) event.source.getEntity();
        if (!hasThisSkill(player))
            return;
        FocusedFireData data = getSkillData(player);
        int id = event.entityLiving.getEntityId();
        if (id == data.getEntityId()) {
            int stacks = data.getConsecutiveHits() + 1;
            data.setConsecutiveHits(stacks);
            event.ammount *= 1 + 0.1 * stacks;
        } else {
            data.setEntityId(id);
            data.setConsecutiveHits(0);
        }
    }

    public static class FocusedFireData extends SkillData {

        private int entityId = -1;

        private int consecutiveHits = 0;

        public FocusedFireData(String registryName) {
            super(registryName, false, false);
        }

        public int getEntityId() {
            return entityId;
        }

        public void setEntityId(int entityId) {
            this.entityId = entityId;
        }

        public int getConsecutiveHits() {
            return consecutiveHits;
        }

        public void setConsecutiveHits(int consecutiveHits) {
            this.consecutiveHits = consecutiveHits;
        }

        //Don't save anything to nbt and notifying client also isn't needed
        @Override
        public void writeToNBT(NBTTagCompound compound) {}

        @Override
        public void readFromNBT(NBTTagCompound compound) {}

        @Override
        public void writeToBuffer(ByteBuf buf) {}

        @Override
        public void readFromBuffer(ByteBuf buf) {}
    }
}
