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
 * {@link net.minecraft.network.NetHandlerPlayServer}
 */
public class NetHandlerServerTransformer extends SubTransformer {

    public NetHandlerServerTransformer() {
        super("net.minecraft.network.NetHandlerPlayServer");
    }

    @Override
    public boolean shouldApplyOnSide(Side side) {
        return true;
    }

    @Override
    public void transformClass(ClassNode node) {
        MethodNode mn = AsmHelper.getMethodNodeByName(node, "processHeldItemChange", "func_147355_a");
        AbstractInsnNode abstractInsnNode = AsmHelper.getFirstMatchingMethodInsNode(mn,
                "net/minecraft/entity/player/EntityPlayerMP",
                "func_143004_u",
                "func_143004_u",
                "()V");
        mn.instructions.insert(abstractInsnNode, new MethodInsnNode(Opcodes.INVOKESTATIC,
                "excsi/rekindledarcane/api/event/ChangeHeldItemEvent",
                "fireChangeItemEvent",
                "(Lnet/minecraft/entity/player/EntityPlayer;)V",
                false));
        mn.instructions.insert(abstractInsnNode, new FieldInsnNode(Opcodes.GETFIELD,
                "net/minecraft/network/NetHandlerPlayServer",
                RekindledArcaneCore.isDevEnvironment ? "playerEntity" : "field_147369_b",
                "Lnet/minecraft/entity/player/EntityPlayerMP;"));
        mn.instructions.insert(abstractInsnNode, new VarInsnNode(Opcodes.ALOAD, 0));

        mn = AsmHelper.getMethodNodeByName(node, "processUseEntity", "func_147340_a");
        abstractInsnNode = AsmHelper.getFirstMatchingMethodInsNode(mn,
                "net/minecraft/entity/player/EntityPlayerMP",
                "getDistanceSqToEntity",
                "func_70068_e",
                "(Lnet/minecraft/entity/Entity;)D");
        mn.instructions.insertBefore(abstractInsnNode, new VarInsnNode(Opcodes.ALOAD, 0));
        mn.instructions.insertBefore(abstractInsnNode, new FieldInsnNode(Opcodes.GETFIELD,
                "net/minecraft/network/NetHandlerPlayServer",
                RekindledArcaneCore.isDevEnvironment ? "playerEntity" : "field_147369_b",
                "Lnet/minecraft/entity/player/EntityPlayerMP;"));
        mn.instructions.insertBefore(abstractInsnNode, new VarInsnNode(Opcodes.DLOAD, 5));
        mn.instructions.insertBefore(abstractInsnNode, new MethodInsnNode(Opcodes.INVOKESTATIC,
                "excsi/rekindledarcane/common/event/RekindledArcaneEvents",
                "handleEntityInteractReachSquared",
                "(Lnet/minecraft/entity/player/EntityPlayer;D)D",
                false));
        mn.instructions.insertBefore(abstractInsnNode, new VarInsnNode(Opcodes.DSTORE, 5));
    }
}
