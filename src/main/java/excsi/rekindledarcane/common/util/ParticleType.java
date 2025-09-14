package excsi.rekindledarcane.common.util;

import excsi.rekindledarcane.client.util.BlendMode;
import excsi.rekindledarcane.client.util.StateRenderHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public enum ParticleType {

    PARTICLE_ORB(new ResourceLocation("rekindledarcane", "textures/particles/orbfx.png"),
            () -> {
                StateRenderHelper.enableBlend();
                StateRenderHelper.blendMode(BlendMode.ADDITIVE);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glDepthMask(false);
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
            }, () -> {
                StateRenderHelper.restoreStates();
                GL11.glDepthMask(true);
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            });

    private final ResourceLocation texture;

    private final Runnable activateStates, deactivateStates;

    ParticleType(ResourceLocation texture, Runnable activateStates, Runnable deactivateStates) {
        this.texture = texture;
        this.activateStates = activateStates;
        this.deactivateStates = deactivateStates;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public Runnable getActivateStates() {
        return activateStates;
    }

    public Runnable getDeactivateStates() {
        return deactivateStates;
    }
}
