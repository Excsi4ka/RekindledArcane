package excsi.rekindledarcane.core.transformers;

import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.core.AsmHelper;
import excsi.rekindledarcane.core.RekindledArcaneCore;
import excsi.rekindledarcane.core.SubTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

/**
 * {@link net.minecraft.client.renderer.EntityRenderer}
 */
public class EntityRendererTransformer extends SubTransformer {

    public EntityRendererTransformer() {
        super("net.minecraft.client.renderer.EntityRenderer");
    }

    @Override
    public boolean shouldApplyOnSide(Side side) {
        return side == Side.CLIENT;
    }

    @Override
    public void transformClass(ClassNode node) {
        MethodNode mn = AsmHelper.getMethodNodeByName(node, "getMouseOver", "func_78473_a");
        AbstractInsnNode abstractInsnNode = AsmHelper.getFirstMatchingMethodInsNode(mn,
                "net/minecraft/entity/EntityLivingBase",
                "getLook",
                "func_70676_i",
                "(F)Lnet/minecraft/util/Vec3;")
                .getNext();
        mn.instructions.insert(abstractInsnNode, new VarInsnNode(Opcodes.DSTORE, 2));
        mn.instructions.insert(abstractInsnNode, new MethodInsnNode(Opcodes.INVOKESTATIC,
                "excsi/rekindledarcane/common/event/RekindledArcaneEvents",
                "handleEntityInteract",
                "(DLnet/minecraft/entity/player/EntityPlayer;)D",
                false));
        mn.instructions.insert(abstractInsnNode, new FieldInsnNode(Opcodes.GETFIELD,
                "net/minecraft/client/Minecraft",
                RekindledArcaneCore.isDevEnvironment ? "thePlayer" : "field_71439_g",
                "Lnet/minecraft/client/entity/EntityClientPlayerMP;"));
        mn.instructions.insert(abstractInsnNode, new FieldInsnNode(Opcodes.GETFIELD,
                "net/minecraft/client/renderer/EntityRenderer",
                RekindledArcaneCore.isDevEnvironment ? "mc" : "field_78531_r",
                "Lnet/minecraft/client/Minecraft;"));
        mn.instructions.insert(abstractInsnNode, new VarInsnNode(Opcodes.ALOAD, 0));
        mn.instructions.insert(abstractInsnNode, new VarInsnNode(Opcodes.DLOAD, 2));

        //Screen Shake
        mn = AsmHelper.getMethodNodeByName(node, "hurtCameraEffect", "func_78482_e");
        abstractInsnNode = mn.instructions.getFirst();
        mn.instructions.insert(abstractInsnNode, new MethodInsnNode(Opcodes.INVOKESTATIC,
                "excsi/rekindledarcane/client/util/ScreenShakeManager",
                "handleScreenShake",
                "(F)V",
                false));
        mn.instructions.insert(abstractInsnNode, new VarInsnNode(Opcodes.FLOAD, 1));
    }
}
