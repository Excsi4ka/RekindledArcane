package excsi.rekindledarcane.common.skill.attribute;

import java.util.UUID;

public enum AttributeOperation {

    ADDITIVE(0, "e743c11a-e907-4d43-b461-2ae9efa27051"),

    MULTIPLY_BASE(1, "20340c16-24f3-47a4-8616-c2910dbe03c6"),

    MULTIPLY_TOTAL(2, "5efe8d35-0832-46ef-8e1e-d2ec6d7dfb13");

    private final int operation;

    private final UUID uuid;

    AttributeOperation(int operation, String uuid) {
        this.operation = operation;
        this.uuid = UUID.fromString(uuid);
    }

    public int getOperation() {
        return operation;
    }

    public UUID getUUID() {
        return uuid;
    }
}
