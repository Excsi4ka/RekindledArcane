package excsi.rekindledarcane.core.transformers;

import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.core.AsmHelper;
import excsi.rekindledarcane.core.SubTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class ModelBipedTransformer extends SubTransformer {

    public ModelBipedTransformer() {
        super("net.minecraft.client.model.ModelBiped");
    }

    @Override
    public boolean shouldApplyOnSide(Side side) {
        return side == Side.CLIENT;
    }

    @Override
    public void transformClass(ClassNode node) {
        MethodNode mn = AsmHelper.getMethodNodeByName(node, "setRotationAngles", "func_78087_a");
        AbstractInsnNode abstractInsnNode = AsmHelper.getFirstMatchingOpcode(mn, Opcodes.RETURN);
        mn.instructions.insertBefore(abstractInsnNode, new VarInsnNode(Opcodes.ALOAD, 7)); //entity
        mn.instructions.insertBefore(abstractInsnNode, new VarInsnNode(Opcodes.ALOAD, 0));
        mn.instructions.insertBefore(abstractInsnNode, new MethodInsnNode(Opcodes.INVOKESTATIC,
                "excsi/rekindledarcane/client/util/ClientSkillCastingManager",
                "renderCallback",
                "(Lnet/minecraft/entity/Entity;Lnet/minecraft/client/model/ModelBiped;)V",
                false));
    }
}
