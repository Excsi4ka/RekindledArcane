package excsi.rekindledarcane.core.transformers;

import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.core.AsmHelper;
import excsi.rekindledarcane.core.SubTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * {@link net.minecraft.client.multiplayer.PlayerControllerMP}
 */
public class PlayerControllerMPTransformer extends SubTransformer {

    public PlayerControllerMPTransformer() {
        super("net.minecraft.client.multiplayer.PlayerControllerMP");
    }

    @Override
    public boolean shouldApplyOnSide(Side side) {
        return side == Side.CLIENT;
    }

    @Override
    public void transformClass(ClassNode node) {
        MethodNode mn = AsmHelper.getMethodNodeByName(node, "getBlockReachDistance", "func_78757_d");
        AbstractInsnNode abstractInsnNode = AsmHelper.getFirstMatchingOpcode(mn, Opcodes.FRETURN);
        mn.instructions.insertBefore(abstractInsnNode, new MethodInsnNode(Opcodes.INVOKESTATIC,
                "excsi/rekindledarcane/client/event/ClientEventHandler",
                "handleExtendedReach",
                "(F)F",
                false));
    }
}
