package excsi.rekindledarcane.api.skill;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import excsi.rekindledarcane.api.misc.Point;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;

import java.util.List;

public interface ISkill {

    String getNameID();

    String getUnlocalizedName();

    void addDescription(List<String> description);

    SkillType getSkillType();

    ISkillCategory getSkillCategory();

    Point getPosition();

    int getSkillPointCost();

    void unlockSkill(EntityPlayer player);

    void forgetSkill(EntityPlayer player);

    ISkill setSkillCategory(ISkillCategory category);

    ISkill setSkillPointCost(int pointCost);

    ISkill setPosition(Point point);

    ISkill setPreRequisite(String id);

    ISkill setAntiRequisite(String id);

    ISkill getPreRequisite();

    ISkill getAntiRequisite();

    @SideOnly(Side.CLIENT)
    void registerSkillIcon(IIconRegister textureAtlas);

    @SideOnly(Side.CLIENT)
    IIcon getIcon();

    /**
     * @return true if {@link #unlockSkill} should be called everytime the game restarts and the player data gets loaded.
     * Event based skills use this to cache the players that have those skills to avoid looking it up all the time.
     */
    default boolean reapplyOnRestart() {
        return false;
    }
}
