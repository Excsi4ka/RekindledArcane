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

public class ThaumcraftTransformer extends SubTransformer {

    public ThaumcraftTransformer() {
        super("thaumcraft.common.lib.events.EventHandlerRunic");
    }

    @Override
    public boolean shouldApplyOnSide(Side side) {
        return true;
    }

    @Override
    public void transformClass(ClassNode node) {
        MethodNode mn = AsmHelper.getMethodNodeByName(node, "livingTick", "livingTick");
        AbstractInsnNode insNode = AsmHelper.getFirstMatchingMethodInsNode(mn,
                "baubles/api/BaublesApi",
                "getBaubles",
                "getBaubles",
                "(Lnet/minecraft/entity/player/EntityPlayer;)Lnet/minecraft/inventory/IInventory;");
        mn.instructions.insertBefore(insNode, new VarInsnNode(Opcodes.ALOAD, 2));
        mn.instructions.insertBefore(insNode, new VarInsnNode(Opcodes.ILOAD, 3));
        mn.instructions.insertBefore(insNode, new MethodInsnNode(Opcodes.INVOKESTATIC,
                "excsi/rekindledarcane/api/event/RunicShieldAmountEvent",
                "fireRunicShieldEvent",
                "(Lnet/minecraft/entity/player/EntityPlayer;I)I",
                false));
        mn.instructions.insertBefore(insNode, new VarInsnNode(Opcodes.ISTORE, 3));
    }
}
