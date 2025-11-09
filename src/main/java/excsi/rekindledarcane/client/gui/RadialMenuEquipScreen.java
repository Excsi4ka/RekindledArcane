package excsi.rekindledarcane.client.gui;

import excsi.rekindledarcane.api.skill.IActiveAbilitySkill;
import excsi.rekindledarcane.client.AssetLib;
import excsi.rekindledarcane.client.gui.widgets.RadialMenuSlot;
import excsi.rekindledarcane.common.data.player.PlayerData;
import excsi.rekindledarcane.common.data.player.PlayerDataManager;
import excsi.rekindledarcane.common.network.PacketManager;
import excsi.rekindledarcane.common.network.client.ClientPacketSetActiveSlot;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;

import java.util.ArrayList;
import java.util.List;

public class RadialMenuEquipScreen extends GuiScreen {

    public int lastMouseX, lastMouseY;

    public List<RadialMenuSlot> radialMenuComponents = new ArrayList<>();

    public RadialMenuSlot currentlySelected;

    @Override
    public void initGui() {
        radialMenuComponents.clear();
        PlayerData playerData = PlayerDataManager.getPlayerData(mc.thePlayer);
        List<IActiveAbilitySkill> unlockedSkills = playerData.getEquippedActiveSkills();
        int slots = playerData.getActiveSlotCount();
        int x = width / 2 - 16;
        int y = height / 2 - 16;
        int radius = 60;
        for (int i = 0; i < slots; i++) {
            double theta = Math.PI / slots;
            double centerTheta = theta * 2 * i;
            int pointX = (int) (x + radius * Math.sin(centerTheta));
            int pointY = (int) (y - radius * Math.cos(centerTheta));
            radialMenuComponents.add(new RadialMenuSlot(i, pointX, pointY,
                    centerTheta - theta, centerTheta + theta, unlockedSkills.get(i)));
        }
        lastMouseX = x;
        lastMouseY = y;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        mc.getTextureManager().bindTexture(AssetLib.skillIconTextureAtlas);
        Tessellator tes = Tessellator.instance;
        tes.startDrawingQuads();
        radialMenuComponents.forEach(b -> b.drawButton(mc, mouseX, mouseY));
        tes.draw();

        if (currentlySelected != null) {
            String name = currentlySelected.skill.getUnlocalizedName();
            int offset = mc.fontRenderer.getStringWidth(name) / 2;
            mc.fontRenderer.drawStringWithShadow(name, width / 2 - offset, height / 2 - 3, 0xFFFFFF);
        }
        updateMouseMovement(mouseX, mouseY);
        lastMouseX = mouseX;
        lastMouseY = mouseY;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int clickType) {
        if (clickType == 0 && currentlySelected != null) {
            PacketManager.sendToServer(new ClientPacketSetActiveSlot(currentlySelected.id));
            mc.displayGuiScreen(null);
            mc.setIngameFocus();
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void keyTyped(char key, int index) {
        if (currentlySelected != null)
            PacketManager.sendToServer(new ClientPacketSetActiveSlot(currentlySelected.id));
        this.mc.displayGuiScreen(null);
        this.mc.setIngameFocus();
    }

    public RadialMenuSlot getRadialMenuWedge(double angle) {
        for (RadialMenuSlot component : radialMenuComponents) {
            if (angle >= component.startAngle && angle <= component.stopAngle)
                return component;
        }
        return radialMenuComponents.get(0);
    }

    public void updateMouseMovement(int mouseX, int mouseY) {
        if (mouseX == lastMouseX && mouseY == lastMouseY)
            return;
        int dX = mouseX - width / 2;
        int dY = height / 2 - mouseY;
        double angle = Math.toDegrees(Math.atan2(dX, dY));
        if (angle < 0) angle += 360;
        RadialMenuSlot widget = getRadialMenuWedge(angle);
        if (!widget.equals(currentlySelected)) {
            //widget.onSelect();
            currentlySelected = widget;
        }
    }
}
