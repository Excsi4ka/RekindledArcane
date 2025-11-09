package excsi.rekindledarcane.api.skill.templates;

import cpw.mods.fml.common.FMLCommonHandler;
import excsi.rekindledarcane.api.skill.SkillType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

import java.util.HashSet;
import java.util.UUID;

public abstract class EventSkillBase extends AbstractSkill {

    public final HashSet<UUID> playersWithSkill;

    public EventSkillBase(String nameID) {
        super(nameID);
        playersWithSkill = new HashSet<>();
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
    }

    public boolean hasThisSkill(EntityLivingBase entityLivingBase) {
        if(!(entityLivingBase instanceof EntityPlayer))
            return false;
        return playersWithSkill.contains(entityLivingBase.getUniqueID());
    }

    @Override
    public SkillType getSkillType() {
        return SkillType.PASSIVE;
    }

    @Override
    public void unlockSkill(EntityPlayer player) {
        playersWithSkill.add(player.getUniqueID());
    }

    @Override
    public void forgetSkill(EntityPlayer player) {
        playersWithSkill.remove(player.getUniqueID());
    }

    @Override
    public boolean reapplyOnRestart() {
        return true;
    }
}
