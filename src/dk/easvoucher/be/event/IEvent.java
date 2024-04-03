package dk.easvoucher.be.event;

import java.time.LocalDateTime;

public interface IEvent {
    int getId();
    String getName();
    String getNotes();
    String getLocation();
    LocalDateTime getTime();
    int getCoordinatorId();
}
