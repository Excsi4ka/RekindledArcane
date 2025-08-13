package excsi.rekindledarcane.core;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public class ASMUtils {

    public static MethodNode getMethodNodeByName(ClassNode classNode, String methodName, String obfMethodName) {
        for (MethodNode node : classNode.methods) {
            if (node.name.equals(methodName) || node.name.equals(obfMethodName)) {
                return node;
            }
        }
        throw new RuntimeException("Unable to find method named " + methodName + " in class " + classNode.name);
    }

    public static MethodNode getMethodNodeByDescriptor(ClassNode classNode, String methodName, String obfMethodName, String descriptor) {
        for (MethodNode node : classNode.methods) {
            if ((node.name.equals(methodName) || node.name.equals(obfMethodName)) && node.desc.equals(descriptor)) {
                return node;
            }
        }
        throw new RuntimeException("Unable to find method named " + methodName + " in class " + classNode.name);
    }
}
