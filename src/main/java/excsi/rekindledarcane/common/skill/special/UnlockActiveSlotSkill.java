package excsi.rekindledarcane.common.skill.special;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import excsi.rekindledarcane.api.skill.SkillType;
import excsi.rekindledarcane.common.data.player.PlayerData;
import excsi.rekindledarcane.common.data.player.PlayerDataManager;
import excsi.rekindledarcane.api.skill.templates.AbstractSkill;
import net.minecraft.client.renderer.texture.IIconRegister;
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

    //---------------------- Same name and icon no matter the skill id --------------------------------------
    @Override
    public String getUnlocalizedName() {
        return "rekindledarcane.skill.slot";
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerSkillIcon(IIconRegister textureAtlas) {
        skillIcon = textureAtlas.registerIcon("rekindledarcane:slot");
    }
}
