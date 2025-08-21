package excsi.rekindledarcane.common.data.player;

public class SkillData {

    private int skillCooldown;

    public SkillData() {
        skillCooldown = 0;
    }

    public int getSkillCooldown() {
        return skillCooldown;
    }

    public void setSkillCooldown(int skillCooldown) {
        this.skillCooldown = skillCooldown;
    }
}
