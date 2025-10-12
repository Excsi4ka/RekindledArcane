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

/**
 * {@link net.minecraftforge.common.ISpecialArmor.ArmorProperties}
 */
public class ArmorPropertiesTransformer extends SubTransformer {

    public ArmorPropertiesTransformer() {
        super("net.minecraftforge.common.ISpecialArmor$ArmorProperties");
    }

    @Override
    public boolean shouldApplyOnSide(Side side) {
        return true;
    }

    @Override
    public void transformClass(ClassNode node) {
        MethodNode mn = AsmHelper.getMethodNodeByName(node, "ApplyArmor", "ApplyArmor");
        AbstractInsnNode abstractInsnNode = AsmHelper.getFirstMatchingMethodInsNode(mn,
                "net/minecraftforge/common/ISpecialArmor$ArmorProperties",
                "StandardizeList",
                "StandardizeList",
                "([Lnet/minecraftforge/common/ISpecialArmor$ArmorProperties;D)V");
        mn.instructions.insert(abstractInsnNode, new MethodInsnNode(Opcodes.INVOKESTATIC,
                "excsi/rekindledarcane/api/event/ArmorPropertiesCalculateEvent",
                "fireArmorRecalculateEvent",
                "(Lnet/minecraft/entity/EntityLivingBase;[Lnet/minecraftforge/common/ISpecialArmor$ArmorProperties;)V",
                false));
        mn.instructions.insert(abstractInsnNode, new VarInsnNode(Opcodes.ALOAD, 6));
        mn.instructions.insert(abstractInsnNode, new VarInsnNode(Opcodes.ALOAD, 0));
    }
}
