package excsi.rekindledarcane.core.transformers;

import cpw.mods.fml.relauncher.Side;
import excsi.rekindledarcane.core.ASMUtils;
import excsi.rekindledarcane.core.SubTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

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
        MethodNode mn = ASMUtils.getMethodNodeByName(node, "livingTick", "livingTick");
//        AbstractInsnNode insNode = getFirstMethodCallAfter(mn,
//                "baubles/api/BaublesApi",
//                "getBaubles",
//                "getBaubles",
//                "(Lnet/minecraft/entity/player/EntityPlayer;)Lnet/minecraft/inventory/IInventory;",
//                0)
//                .getNext();
//        mn.instructions.insert(insNode,new VarInsnNode(Opcodes.ISTORE,3));
//        mn.instructions.insert(insNode,new MethodInsnNode(Opcodes.INVOKESTATIC,
//                "hellfirepvp/astralsorcery/common/world/util/WorldEventNotifier",
//                "runicShieldCallback",
//                "(Lnet/minecraft/entity/player/EntityPlayer;I)I",
//                false));
//        mn.instructions.insert(insNode,new VarInsnNode(Opcodes.ILOAD,3));
//        mn.instructions.insert(insNode,new VarInsnNode(Opcodes.ALOAD,2));
    }
}
