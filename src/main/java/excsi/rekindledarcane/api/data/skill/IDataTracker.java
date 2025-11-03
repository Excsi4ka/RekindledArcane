package excsi.rekindledarcane.api.data.skill;

public interface IDataTracker {

    void trackData(SkillData data);

    void stopTrackingData(SkillData data);

    void queueDataToSync(SkillData data);
}
