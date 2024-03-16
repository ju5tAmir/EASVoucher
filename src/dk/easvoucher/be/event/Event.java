package dk.easvoucher.be.event;

import java.time.LocalDate;
import java.util.Date;

public class Event implements IEvent{
    private int id;
    private String title;
    private String note;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;

    public Event(int id, String title, String note, String location, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    @Override
    public int getId() {
        return id;
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public String note() {
        return note;
    }

    @Override
    public String location() {
        return location;
    }

    @Override
    public LocalDate startTime() {
        return startDate;
    }

    @Override
    public LocalDate endTime() {
        return endDate;
    }
}
