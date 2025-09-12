package excsi.rekindledarcane.client.gui;

import excsi.rekindledarcane.client.AssetLib;
import excsi.rekindledarcane.common.data.player.PlayerData;
import excsi.rekindledarcane.common.data.player.PlayerDataManager;
import net.minecraft.client.gui.GuiScreen;

public class SkillSelectionScreen extends GuiScreen {

    public PlayerData playerData;

    @Override
    public void initGui() {
        playerData = PlayerDataManager.getPlayerData(mc.thePlayer);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        int y = height / 2 - 86;
        int x = width / 2 - 92;
        mc.getTextureManager().bindTexture(AssetLib.skillSelectorGui);
        drawTexturedModalRect(x,y,0,0,194,166);
        for (int i = playerData.getActiveSlots(); i < 5; i++) {
            drawTexturedModalRect(x + 16,y + 15 + 31 * i,0, 166, 20, 20);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
