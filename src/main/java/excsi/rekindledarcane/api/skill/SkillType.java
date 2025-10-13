package excsi.rekindledarcane.api.skill;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;

import java.awt.Color;

public enum SkillType {

    PASSIVE("passive_icon_frame", new Color(246, 202, 2, 255)),

    ABILITY("ability_icon_frame", new Color(1, 155, 73, 255)),

    REKINDLED("rekindled_icon_frame", new Color(238, 91, 2, 255));

    public final String typeTexName;

    public final Color typeColor;

    @SideOnly(Side.CLIENT)
    public IIcon frameIcon;

    SkillType(String texName, Color typeColor) {
        this.typeTexName = "rekindledarcane:" + texName;
        this.typeColor = typeColor;
    }
}
