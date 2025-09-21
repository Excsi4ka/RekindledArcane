package excsi.rekindledarcane.client.fx;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import excsi.rekindledarcane.common.util.ParticleType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import java.util.ArrayDeque;
import java.util.EnumMap;
import java.util.Queue;

public class ParticleBatchRender {

    public static final ParticleBatchRender INSTANCE = new ParticleBatchRender();

    public final EnumMap<ParticleType, Queue<BatchedEntityFX>> queuedEffects = new EnumMap<>(ParticleType.class);

    private ParticleBatchRender() {
        for (ParticleType type : ParticleType.values()) {
            queuedEffects.put(type, new ArrayDeque<>());
        }
    }

    public void addEffect(ParticleType type, BatchedEntityFX fx) {
        Queue<BatchedEntityFX> queue = queuedEffects.get(type);
        if(queue.size() >= 4000) {
            queue.poll();
        }
        queue.add(fx);
    }

    @SubscribeEvent
    public void render(RenderWorldLastEvent event) {
        queuedEffects.forEach((type, list) -> {
            if (list.isEmpty()) return;
            Minecraft.getMinecraft().renderEngine.bindTexture(type.getTexture());
            type.getActivateStates().run();
            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            list.forEach(particle -> particle.renderParticle(tessellator, event.partialTicks, ActiveRenderInfo.rotationX, ActiveRenderInfo.rotationXZ,
                    ActiveRenderInfo.rotationZ, ActiveRenderInfo.rotationYZ, ActiveRenderInfo.rotationXY));
            tessellator.draw();
            type.getDeactivateStates().run();
        });
    }

    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent event) {
        if(event.phase == TickEvent.Phase.START) return;
        queuedEffects.forEach((type, list) -> {
            if (list.isEmpty()) return;
            list.forEach(BatchedEntityFX::onUpdate);
            list.removeIf(fx -> fx == null || fx.isDead);
        });
    }
}
