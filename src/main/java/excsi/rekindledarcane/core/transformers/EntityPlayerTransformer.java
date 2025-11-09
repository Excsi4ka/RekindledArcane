package excsi.rekindledarcane.core.transformers;

import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.core.AsmHelper;
import excsi.rekindledarcane.core.SubTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

/**
 * {@link net.minecraft.entity.player.EntityPlayer}
 */
public class EntityPlayerTransformer extends SubTransformer {

    public EntityPlayerTransformer() {
        super("net.minecraft.entity.player.EntityPlayer");
    }

    @Override
    public boolean shouldApplyOnSide(Side side) {
        return true;
    }

    @Override
    public void transformClass(ClassNode node) {
        MethodNode mn = AsmHelper.getMethodNodeByName(node, "attackTargetEntityWithCurrentItem", "func_71059_n");
        AbstractInsnNode abstractInsnNode = AsmHelper.getFirstMatchingOpcode(mn,
                Opcodes.LDC,
                insnNode -> {
                    LdcInsnNode ldcNode = (LdcInsnNode) insnNode;
                    if (!(ldcNode.cst instanceof Float)) return false;
                    Float d = (Float) ldcNode.cst;
                    return d == 1.5;
                }).getNext();
        mn.instructions.insert(abstractInsnNode, new MethodInsnNode(Opcodes.INVOKESTATIC,
                "excsi/rekindledarcane/common/event/RekindledArcaneEvents",
                "handleCritDamage",
                "(Lnet/minecraft/entity/player/EntityPlayer;F)F",
                false));
        mn.instructions.insert(abstractInsnNode, new VarInsnNode(Opcodes.FLOAD, 3));
        mn.instructions.insert(abstractInsnNode, new VarInsnNode(Opcodes.ALOAD, 0));
        mn.instructions.insert(abstractInsnNode, new InsnNode(Opcodes.POP));

        abstractInsnNode = AsmHelper.getFirstMatchingOpcode(mn,
                Opcodes.ISTORE,
                insnNode -> ((VarInsnNode) insnNode).var == 6);

        //mn.instructions.insert(abstractInsnNode, new VarInsnNode(Opcodes.ISTORE, 6));
        mn.instructions.insertBefore(abstractInsnNode, new VarInsnNode(Opcodes.ALOAD, 0));
        mn.instructions.insertBefore(abstractInsnNode, new MethodInsnNode(Opcodes.INVOKESTATIC,
                "excsi/rekindledarcane/common/event/RekindledArcaneEvents",
                "isCriticalHit",
                "(ZLnet/minecraft/entity/player/EntityPlayer;)Z",
                false));
        //mn.instructions.insert(abstractInsnNode, new InsnNode(Opcodes.POP));
    }
}
