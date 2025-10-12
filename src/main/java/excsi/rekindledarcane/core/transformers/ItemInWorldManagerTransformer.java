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
 * {@link net.minecraft.server.management.ItemInWorldManager}
 */
public class ItemInWorldManagerTransformer extends SubTransformer {

    public ItemInWorldManagerTransformer() {
        super("net.minecraft.server.management.ItemInWorldManager");
    }

    @Override
    public boolean shouldApplyOnSide(Side side) {
        return true;
    }

    @Override
    public void transformClass(ClassNode node) {
        MethodNode mn = AsmHelper.getMethodNodeByName(node, "getBlockReachDistance", "getBlockReachDistance");
        AbstractInsnNode abstractInsnNode = AsmHelper.getFirstMatchingOpcode(mn, Opcodes.DRETURN);
        mn.instructions.insertBefore(abstractInsnNode, new VarInsnNode(Opcodes.ALOAD, 0));
        mn.instructions.insertBefore(abstractInsnNode, new FieldInsnNode(Opcodes.GETFIELD,
                "net/minecraft/server/management/ItemInWorldManager",
                RekindledArcaneCore.isDevEnvironment ? "thisPlayerMP" : "field_73090_b",
                "Lnet/minecraft/entity/player/EntityPlayerMP;"));
        mn.instructions.insertBefore(abstractInsnNode, new MethodInsnNode(Opcodes.INVOKESTATIC,
                "excsi/rekindledarcane/common/event/RekindledArcaneEvents",
                "handleEntityInteract",
                "(DLnet/minecraft/entity/player/EntityPlayer;)D",
                false));
    }
}
