package excsi.rekindledarcane.client.util;

import org.lwjgl.opengl.GL11;

public enum BlendMode {

    DEFAULT(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA),

    ADDITIVE(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

    public final int sFactor, dFactor;

    BlendMode(int sFactor, int dFactor) {
        this.sFactor = sFactor;
        this.dFactor = dFactor;
    }
}
