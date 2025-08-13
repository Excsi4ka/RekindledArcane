package excsi.rekindledarcane.common.skill;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import excsi.rekindledarcane.api.skill.ISkill;
import excsi.rekindledarcane.api.skill.ISkillCategory;
import excsi.rekindledarcane.api.skill.Point;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.List;

public abstract class BaseAbstractSkill implements ISkill {

    private final String nameID;

    private int skillPointCost = 1;

    private boolean isCoreSkill;

    private ISkillCategory skillCategory;

    private Point point;

    private static final Point fallbackPoint = new Point(0,0);

    @SideOnly(Side.CLIENT)
    public IIcon skillIcon;

    public BaseAbstractSkill(String nameID) {
        this.nameID = nameID;
    }

    @Override
    public String getNameID() {
        return nameID;
    }

    public String getUnlocalizedDescription() {
        return getUnlocalizedName() + ".desc";
    }

    @Override
    public String getUnlocalizedName() {
        return "rekindledarcane.skill." + nameID;
    }

    @Override
    public int getSkillPointCost() {
        return skillPointCost;
    }

    @Override
    public ISkill setSkillPointCost(int skillPointCost) {
        this.skillPointCost = skillPointCost;
        return this;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerSkillIcon(IIconRegister textureAtlas) {
        skillIcon = textureAtlas.registerIcon("rekindledarcane:" + nameID);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon() {
        return skillIcon;
    }

    @Override
    public boolean isCoreSkill() {
        return isCoreSkill;
    }

    @Override
    public ISkill setCoreSkill() {
        isCoreSkill = true;
        return this;
    }

    @Override
    public ISkill setSkillCategory(ISkillCategory skillCategory) {
        this.skillCategory = skillCategory;
        return this;
    }

    @Override
    public ISkillCategory getSkillCategory() {
        return skillCategory;
    }

    @Override
    public ISkill setPosition(Point point) {
        this.point = point;
        return this;
    }

    @Override
    public Point getPosition() {
        if(point == null)
            return fallbackPoint;
        return point;
    }

    @Override
    public void addDescription(List<String> description) {
        description.add(StatCollector.translateToLocal(getUnlocalizedName()));
        description.add(StatCollector.translateToLocal(getUnlocalizedDescription()));
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof ISkill))
            return false;
        ISkill skill = (ISkill) obj;
        return skill.getNameID().equals(nameID) && skill.getSkillCategory().equals(skillCategory);
    }
}
