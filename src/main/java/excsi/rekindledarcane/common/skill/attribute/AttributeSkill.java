package excsi.rekindledarcane.common.skill.attribute;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import excsi.rekindledarcane.api.skill.ISkill;
import excsi.rekindledarcane.api.skill.SkillType;
import excsi.rekindledarcane.common.skill.BaseAbstractSkill;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Collection;
import java.util.List;

public class AttributeSkill extends BaseAbstractSkill {

    public final Multimap<String, AttributeDataHolder> modifierMultimap = HashMultimap.create();

    public AttributeSkill(String nameID) {
        super(nameID);
    }

    public AttributeSkill addSkillAttribute(IAttribute attribute, AttributeOperation operation, double amount) {
        modifierMultimap.put(attribute.getAttributeUnlocalizedName(), new AttributeDataHolder(operation, amount));
        return this;
    }

    @Override
    public void addDescription(List<String> description) {
        super.addDescription(description);
        //add attributes
    }

    @Override
    public SkillType getSkillType() {
        return SkillType.PASSIVE;
    }

    @Override
    public ISkill getPrerequisite() {
        return null;
    }

    @Override
    public ISkill getAntiRequisite() {
        return null;
    }

    @Override
    public void unlockSkill(EntityPlayer player) {
        BaseAttributeMap attributeMap = player.getAttributeMap();
        modifierMultimap.keySet().forEach(key -> {
            Collection<AttributeDataHolder> attributeDataHolders = modifierMultimap.get(key);
            IAttributeInstance attributeInstance = attributeMap.getAttributeInstanceByName(key);
            attributeDataHolders.forEach(attributeDataHolder -> {
                AttributeModifier currentModifier = attributeInstance.getModifier(attributeDataHolder.getOperation().getUUID());
                double currentValue = 0.0;
                if(currentModifier != null) {
                    currentValue = currentModifier.getAmount();
                    attributeInstance.removeModifier(currentModifier);
                }
                attributeInstance.applyModifier(new PersistentAttributeModifier(attributeDataHolder.getOperation().getUUID(),
                        key, attributeDataHolder.getAmount() + currentValue, attributeDataHolder.getOperation()));
            });
        });
    }

    @Override
    public void forgetSkill(EntityPlayer player) {
        BaseAttributeMap attributeMap = player.getAttributeMap();
        modifierMultimap.keySet().forEach(key -> {
            Collection<AttributeDataHolder> attributeDataHolders = modifierMultimap.get(key);
            IAttributeInstance attributeInstance = attributeMap.getAttributeInstanceByName(key);
            attributeDataHolders.forEach(attributeDataHolder -> {
                AttributeModifier currentModifier = attributeInstance.getModifier(attributeDataHolder.getOperation().getUUID());
                double currentValue = 0.0;
                if(currentModifier != null) {
                    currentValue = currentModifier.getAmount();
                    attributeInstance.removeModifier(currentModifier);
                }
                if(currentValue - attributeDataHolder.getAmount() == 0.0)
                    return;
                attributeInstance.applyModifier(new PersistentAttributeModifier(attributeDataHolder.getOperation().getUUID(),
                        key, currentValue - attributeDataHolder.getAmount(), attributeDataHolder.getOperation()));
            });
        });
    }

    @Override
    public boolean reapplyOnRestart() {
        return false;
    }

    public static class AttributeDataHolder {

        private final AttributeOperation operation;

        private final double amount;

        public AttributeDataHolder(AttributeOperation operation, double amount) {
            this.operation = operation;
            this.amount = amount;
        }

        public AttributeOperation getOperation() {
            return operation;
        }

        public double getAmount() {
            return amount;
        }
    }
}
