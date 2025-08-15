package excsi.rekindledarcane.core.transformers;

import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.core.ASMUtils;
import excsi.rekindledarcane.core.SubTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class ApplyPotionEventTransformer extends SubTransformer {

    public ApplyPotionEventTransformer() {
        super("net.minecraft.entity.EntityLivingBase");
    }

    @Override
    public boolean shouldApplyOnSide(Side side) {
        return true;
    }

    @Override
    public void transformClass(ClassNode node) {
        MethodNode mn = ASMUtils.getMethodNodeByName(node, "addPotionEffect", "func_70690_d");
        AbstractInsnNode abstractInsnNode = mn.instructions.getFirst();
        mn.instructions.insertBefore(abstractInsnNode, new VarInsnNode(Opcodes.ALOAD, 0));
        mn.instructions.insertBefore(abstractInsnNode, new VarInsnNode(Opcodes.ALOAD, 1));
        mn.instructions.insertBefore(abstractInsnNode, new MethodInsnNode(Opcodes.INVOKESTATIC,
                "excsi/rekindledarcane/api/event/PotionApplyEvent",
                "firePotionApplyEvent",
                "(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/potion/PotionEffect;)Z",
                false));
        LabelNode labelNode = new LabelNode();
        mn.instructions.insertBefore(abstractInsnNode, new JumpInsnNode(Opcodes.IFEQ, labelNode));
        mn.instructions.insertBefore(abstractInsnNode, new InsnNode(Opcodes.RETURN));
        mn.instructions.insertBefore(abstractInsnNode, labelNode);
    }
}
