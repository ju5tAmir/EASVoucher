package dk.easvoucher.be;

public class DetailedAccessManagement {

    private int accessEntryId;
    private int eventId;
    private int coordinatorId;

    public DetailedAccessManagement(int accessEntryId, int eventId, int coordinatorId) {
        this.accessEntryId = accessEntryId;
        this.eventId = eventId;
        this.coordinatorId = coordinatorId;
    }

    public int getAccessEntryId() {
        return accessEntryId;
    }

    public void setAccessEntryId(int accessEntryId) {
        this.accessEntryId = accessEntryId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getCoordinatorId() {
        return coordinatorId;
    }

    public void setCoordinatorId(int coordinatorId) {
        this.coordinatorId = coordinatorId;
    }
}
