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
        String fieldName = RekindledArcaneCore.isDevEnvironment ? "playerEntity" : "field_147369_b";
        mn.instructions.insert(abstractInsnNode, new FieldInsnNode(Opcodes.GETFIELD,
                "net/minecraft/network/NetHandlerPlayServer",
                fieldName,
                "Lnet/minecraft/entity/player/EntityPlayerMP;"));
        mn.instructions.insert(abstractInsnNode, new VarInsnNode(Opcodes.ALOAD, 0));
    }
}
