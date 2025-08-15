package excsi.rekindledarcane.common.skill.event;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import excsi.rekindledarcane.api.skill.SkillType;
import excsi.rekindledarcane.common.skill.AdditionalDataSkill;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;

public abstract class EventBasedSkill<T extends Event> extends AdditionalDataSkill {

    public EventBasedSkill(String nameID) {
        super(nameID);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEvent(T event) {
        processEvent(event);
    }

    public abstract void processEvent(T event);

    @Override
    public SkillType getSkillType() {
        return SkillType.PASSIVE;
    }

    @Override
    public void unlockSkill(EntityPlayer player) {

    }

    @Override
    public void forgetSkill(EntityPlayer player) {

    }

    @Override
    public boolean reapplyOnRestart() {
        return true;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {

    }

    @Override
    public NBTTagCompound writeToNBT() {
        return null;
    }
}
