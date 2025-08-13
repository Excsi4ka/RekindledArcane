package excsi.rekindledarcane.common.skill.attribute;

import net.minecraft.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

//Subclass of AttributeModifier that gets reapplied when the player dies and respawns.
//The default behavior doesn't save attribute modifiers after death
public class PersistentAttributeModifier extends AttributeModifier {

    public PersistentAttributeModifier(UUID uuid, String name, double amount, AttributeOperation operation) {
        super(uuid,"PersistentModifier:" + name, amount, operation.getOperation());
    }

    //Should already have the "PersistentModifier:" prefix
    public PersistentAttributeModifier(UUID uuid, String name, double amount, int operation) {
        super(uuid, name, amount, operation);
    }
}
