package excsi.rekindledarcane.api.skill;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;

import java.util.List;

public interface ISkill {

    String getNameID();

    String getUnlocalizedName();

    void addDescription(List<String> description);

    SkillType getSkillType();

    ISkill getPrerequisite();

    ISkill getAntiRequisite();

    ISkillCategory getSkillCategory();

    Point getPosition();

    int getSkillPointCost();

    boolean isCoreSkill();

    void unlockSkill(EntityPlayer player);

    void forgetSkill(EntityPlayer player);

    ISkill setCoreSkill();

    ISkill setSkillCategory(ISkillCategory category);

    ISkill setSkillPointCost(int pointCost);

    ISkill setPosition(Point point);

    @SideOnly(Side.CLIENT)
    void registerSkillIcon(IIconRegister textureAtlas);

    @SideOnly(Side.CLIENT)
    IIcon getIcon();
}
