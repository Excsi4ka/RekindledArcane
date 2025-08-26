package excsi.rekindledarcane.core.transformers;

import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.core.AsmHelper;
import excsi.rekindledarcane.core.RekindledArcaneCore;
import excsi.rekindledarcane.core.SubTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnNode;
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
        FieldNode fieldNode = new FieldNode(
                Opcodes.ACC_PUBLIC,
                "arcane_entityField",
                "Lnet/minecraft/entity/EntityLivingBase;",
                null,
                null);
        node.fields.add(fieldNode);
        String methodName = RekindledArcaneCore.isDevEnvironment ? "setLivingAnimations" : "func_78086_a";
        MethodNode mn = AsmHelper.getMethodNodeByNameNullable(node, methodName , methodName);
        if(mn == null) {
            mn = new MethodNode(Opcodes.ACC_PUBLIC, methodName, "(Lnet/minecraft/entity/EntityLivingBase;FFF)V", null, null);
            node.methods.add(mn);
            mn.instructions.add(new InsnNode(Opcodes.RETURN));
        }
        AbstractInsnNode abstractInsnNode = mn.instructions.getFirst();
        mn.instructions.insertBefore(abstractInsnNode, new VarInsnNode(Opcodes.ALOAD, 0));
        mn.instructions.insertBefore(abstractInsnNode, new VarInsnNode(Opcodes.ALOAD, 1));
        mn.instructions.insertBefore(abstractInsnNode, new FieldInsnNode(Opcodes.PUTFIELD,
                "net/minecraft/client/model/ModelBiped",
                "arcane_entityField",
                "Lnet/minecraft/entity/EntityLivingBase;"));

        mn = AsmHelper.getMethodNodeByName(node, "setRotationAngles", "func_78087_a");
        abstractInsnNode = AsmHelper.getFirstMatchingOpcode(mn, Opcodes.RETURN);
        mn.instructions.insertBefore(abstractInsnNode, new VarInsnNode(Opcodes.ALOAD, 0));
        mn.instructions.insertBefore(abstractInsnNode, new FieldInsnNode(Opcodes.GETFIELD,
                "net/minecraft/client/model/ModelBiped",
                "arcane_entityField",
                "Lnet/minecraft/entity/EntityLivingBase;"));
        mn.instructions.insertBefore(abstractInsnNode, new VarInsnNode(Opcodes.ALOAD,0));
        mn.instructions.insertBefore(abstractInsnNode, new MethodInsnNode(Opcodes.INVOKESTATIC,
                "excsi/rekindledarcane/client/event/ClientEventHandler",
                "renderCallback",
                "(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/client/model/ModelBiped;)V",
                false));
    }
}
