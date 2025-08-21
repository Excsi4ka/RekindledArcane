package excsi.rekindledarcane.common.data.skill;

public class BasicSkillData implements ISkillData, ITickableData {

    private int skillCooldown;

    public BasicSkillData() {
        skillCooldown = 0;
    }

    public int getSkillCooldown() {
        return skillCooldown;
    }

    public void setSkillCooldown(int skillCooldown) {
        this.skillCooldown = skillCooldown;
    }

    @Override
    public boolean shouldTick() {
        return skillCooldown > 0;
    }

    @Override
    public void tick() {
        skillCooldown--;
    }
}
