package excsi.rekindledarcane.core;

import excsi.rekindledarcane.core.transformers.ApplyPotionEventTransformer;
import excsi.rekindledarcane.core.transformers.ArmorPropertiesTransformer;
import excsi.rekindledarcane.core.transformers.EntityRendererTransformer;
import excsi.rekindledarcane.core.transformers.ItemInWorldManagerTransformer;
import excsi.rekindledarcane.core.transformers.ModelBipedTransformer;
import excsi.rekindledarcane.core.transformers.NetHandlerServerTransformer;
import excsi.rekindledarcane.core.transformers.PlayerControllerMPTransformer;
import excsi.rekindledarcane.core.transformers.ThaumcraftTransformer;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RekindledArcaneMainTransformer implements IClassTransformer {

    public HashMap<String, List<SubTransformer>> classTransformers = new HashMap<>();

    public RekindledArcaneMainTransformer() {
        register(new ThaumcraftTransformer());
        register(new ApplyPotionEventTransformer());
        register(new ModelBipedTransformer());
        register(new NetHandlerServerTransformer());
        register(new PlayerControllerMPTransformer());
        register(new EntityRendererTransformer());
        register(new ItemInWorldManagerTransformer());
        register(new ArmorPropertiesTransformer());
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (!classTransformers.containsKey(transformedName))
            return basicClass;

        List<SubTransformer> transformers = classTransformers.get(transformedName);

        for (SubTransformer subTransformer : transformers) {
            try {
                if(!subTransformer.shouldApplyOnSide(RekindledArcaneCore.SIDE))
                    continue;
                RekindledArcaneCore.LOG.info("Transforming " + subTransformer.className);
                ClassNode classNode = new ClassNode();
                ClassReader reader = new ClassReader(basicClass);
                reader.accept(classNode, 0);

                subTransformer.transformClass(classNode);

                ClassWriter writer = new ClassWriter(subTransformer.flag);
                classNode.accept(writer);
                basicClass = writer.toByteArray();
            } catch (Exception e) {
                RekindledArcaneCore.LOG.error("Error while transforming " + subTransformer.className);
                e.printStackTrace();
            }
            if (true) {
                try {
                    File f = new File("outputClasses/" + transformedName + ".class");
                    f.getParentFile().mkdirs();
                    f.createNewFile();
                    FileOutputStream out = new FileOutputStream(f);
                    out.write(basicClass);
                    out.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return basicClass;
    }

    public void register(SubTransformer transformer) {
        if(!classTransformers.containsKey(transformer.className))
            classTransformers.put(transformer.className,new ArrayList<>());
        classTransformers.get(transformer.className).add(transformer);
    }
}
