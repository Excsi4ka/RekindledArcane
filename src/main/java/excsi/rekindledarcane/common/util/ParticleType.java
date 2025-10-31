package excsi.rekindledarcane.common.util;

import excsi.rekindledarcane.client.util.BlendMode;
import excsi.rekindledarcane.client.util.RenderHelperWrapper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public enum ParticleType {

    PARTICLE_ORB(new ResourceLocation("rekindledarcane", "textures/particles/orbfx.png"),
            () -> {
                RenderHelperWrapper.enableBlend();
                RenderHelperWrapper.blendMode(BlendMode.ADDITIVE);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glDepthMask(false);
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
            }, () -> {
                RenderHelperWrapper.restoreStates();
                GL11.glDepthMask(true);
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            }),
    LIGHT_BEAM(new ResourceLocation("rekindledarcane", "textures/particles/beam.png"),
            () -> {
                RenderHelperWrapper.enableBlend();
                RenderHelperWrapper.blendMode(BlendMode.ADDITIVE);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glDepthMask(false);
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
                GL11.glDisable(GL11.GL_CULL_FACE);
            }, () -> {
                RenderHelperWrapper.restoreStates();
                GL11.glDepthMask(true);
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
                GL11.glEnable(GL11.GL_CULL_FACE);
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
