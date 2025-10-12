package excsi.rekindledarcane.client.util;

import org.lwjgl.opengl.GL11;

public class ScreenShakeManager {

    public static final ScreenShakeManager INSTANCE = new ScreenShakeManager();

    private int elapsedTime, totalTime;

    private boolean isShaking;

    private ScreenShakeManager() {
        isShaking = false;
        elapsedTime = 0;
        totalTime = 0;
    }

    public void tick() {
        if(elapsedTime++ >= totalTime) {
            isShaking = false;
            elapsedTime = 0;
            totalTime = 0;
        }
    }

    public void startShaking(int time) {
        isShaking = true;
        totalTime = time;
        elapsedTime = 0;
    }

    public static void handleScreenShake(float partialTicks) {
        ScreenShakeManager manager = ScreenShakeManager.INSTANCE;
        if(!manager.isShaking)
            return;
        GL11.glRotated(Math.sin(manager.elapsedTime * 2.5 + partialTicks), 0, 0, 1);
    }
}
