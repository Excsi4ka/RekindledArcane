package excsi.rekindledarcane.api.skill.templates;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import excsi.rekindledarcane.api.skill.ISkill;
import excsi.rekindledarcane.api.skill.ISkillCategory;
import excsi.rekindledarcane.api.misc.Point;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.List;

public abstract class AbstractSkill implements ISkill {

    private final String nameID;

    private int skillPointCost = 1;

    private ISkillCategory skillCategory;

    private Point point;

    private ISkill cachedPreRequisite = null, cachedAntiRequisite = null;

    private String preRequisiteID, antiRequisiteID;

    public static final Point fallbackPoint = new Point(0, 0);

    @SideOnly(Side.CLIENT)
    private IIcon skillIcon;

    public AbstractSkill(String nameID) {
        this.nameID = nameID;
    }

    @Override
    public String getNameID() {
        return nameID;
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
        if (point == null)
            return fallbackPoint;
        return point;
    }

    @Override
    public void addDescription(List<String> description) {
        description.add(StatCollector.translateToLocal(getUnlocalizedName()));
    }

    @Override
    public ISkill setPreRequisite(String id) {
        this.preRequisiteID = id;
        return this;
    }

    @Override
    public ISkill setAntiRequisite(String id) {
        this.antiRequisiteID = id;
        return this;
    }

    @Override
    public ISkill getPreRequisite() {
        if (cachedPreRequisite == null)
            cachedPreRequisite = skillCategory.getSkillFromID(preRequisiteID);
        return cachedPreRequisite;
    }

    @Override
    public ISkill getAntiRequisite() {
        if (cachedAntiRequisite == null)
            cachedAntiRequisite = skillCategory.getSkillFromID(antiRequisiteID);
        return cachedAntiRequisite;
    }

    @Override
    public int hashCode() {
        return nameID.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ISkill))
            return false;
        ISkill skill = (ISkill) obj;
        return skill.getNameID().equals(nameID) && skillCategory.equals(skill.getSkillCategory());
    }
}
