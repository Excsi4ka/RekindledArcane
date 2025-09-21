package excsi.rekindledarcane.client.gui.widgets;

import excsi.rekindledarcane.api.skill.IActiveAbilitySkill;
import excsi.rekindledarcane.client.util.StateRenderHelper;
import excsi.rekindledarcane.common.network.PacketManager;
import excsi.rekindledarcane.common.network.client.ClientPacketEquipSkill;
import excsi.rekindledarcane.common.network.client.ClientPacketUnEquipSkill;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;

public class EquippedSlotWidget extends Widget {

    public IActiveAbilitySkill ability;

    public boolean locked;

    public EquippedSlotWidget(int id, int x, int y, int width, int height, IActiveAbilitySkill ability, boolean locked) {
        super(id, x, y, width, height);
        this.ability = ability;
        this.locked = locked;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if(!locked && ability != null) {
            StateRenderHelper.batchDrawIcon(Tessellator.instance, xPosition, yPosition, width, height, 0, ability.getIcon());
        }
    }

    public void onEquip(IActiveAbilitySkill ability) {
        PacketManager.sendToServer(new ClientPacketEquipSkill(ability, id));
    }

    public void onUnEquip() {
        PacketManager.sendToServer(new ClientPacketUnEquipSkill(id));
    }
}
