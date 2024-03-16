package dk.easvoucher.be.event;

import java.time.LocalDate;
import java.util.Date;

public interface IEvent {
    int getId();
    String title();
    String note();
    String location();
    LocalDate startTime();
    LocalDate endTime();
}
