package excsi.rekindledarcane.common.skill.special;

import excsi.rekindledarcane.api.skill.SkillType;
import excsi.rekindledarcane.common.data.player.PlayerData;
import excsi.rekindledarcane.common.data.player.PlayerDataManager;
import excsi.rekindledarcane.common.skill.AbstractSkill;
import net.minecraft.entity.player.EntityPlayer;

public class UnlockActiveSlotSkill extends AbstractSkill {

    public UnlockActiveSlotSkill(String nameID) {
        super(nameID);
    }

    @Override
    public SkillType getSkillType() {
        return SkillType.PASSIVE;
    }

    @Override
    public void unlockSkill(EntityPlayer player) {
        PlayerData data = PlayerDataManager.getPlayerData(player);
        data.unlockActiveSlot();
    }

    @Override
    public void forgetSkill(EntityPlayer player) {
        PlayerData data = PlayerDataManager.getPlayerData(player);
        data.lockActiveSlot();
    }

    @Override
    public boolean reapplyOnRestart() {
        return false;
    }
}
