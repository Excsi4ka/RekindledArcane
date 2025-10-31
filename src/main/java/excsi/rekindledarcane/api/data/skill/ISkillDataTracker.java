package excsi.rekindledarcane.api.data.skill;

public interface ISkillDataTracker {

    void trackData(AbstractData data);

    void stopTrackingData(AbstractData data);

    void queueDataToSync(AbstractData data);
}
