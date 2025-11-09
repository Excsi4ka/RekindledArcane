package excsi.rekindledarcane.client.gui.widgets;

import excsi.rekindledarcane.api.skill.IActiveAbilitySkill;
import excsi.rekindledarcane.client.AssetLib;
import excsi.rekindledarcane.client.gui.SkillTreeScreen;
import excsi.rekindledarcane.client.util.RenderHelperWrapper;
import excsi.rekindledarcane.common.network.PacketManager;
import excsi.rekindledarcane.common.network.client.ClientPacketEquipSkill;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class QuickEquipWidget extends Widget {

    public final IActiveAbilitySkill ability;

    public final boolean locked;

    public final SkillTreeScreen parentScreen;

    public QuickEquipWidget(int id, int x, int y, IActiveAbilitySkill ability, boolean locked, SkillTreeScreen screen) {
        super(id, x, y, 22, 22);
        this.ability = ability;
        this.locked = locked;
        this.parentScreen = screen;
        if (ability != null)
            descriptionTooltip.add(StatCollector.translateToLocal(ability.getUnlocalizedName()));
        if (locked)
            descriptionTooltip.add(StatCollector.translateToLocal("rekindledarcane.skill.slot.locked"));
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        Tessellator tes = Tessellator.instance;
        RenderHelperWrapper.batchDrawIcon(tes, xPosition, yPosition, 22, 22, 0, AssetLib.slot);
        if (locked) {
            RenderHelperWrapper.batchDrawIcon(tes, xPosition, yPosition, 22, 22, 0, AssetLib.lock);
        }
        if (ability != null) {
            RenderHelperWrapper.batchDrawIcon(tes, xPosition + 3, yPosition + 3, 16, 16, 0, ability.getIcon());
        }
        if (isMouseOver(mouseX, mouseY) && parentScreen.equipmentMenuToggled()) {
            parentScreen.setHoveringSlot(this);
        }
    }

    @Override
    public void func_146113_a(SoundHandler soundHandler) {
        if (!locked) soundHandler.playSound(PositionedSoundRecord.func_147673_a(new ResourceLocation("rekindledarcane:slot.equip")));
    }

    public void onEquip(IActiveAbilitySkill skill) {
        if (!locked) PacketManager.sendToServer(new ClientPacketEquipSkill(skill, id));
    }
}
