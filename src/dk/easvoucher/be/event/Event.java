package dk.easvoucher.be.event;

import java.time.LocalDateTime;

import java.time.LocalDate;
import java.util.Date;

public class Event implements IEvent {
    private int id;
    private String name;
    private LocalDateTime time;
    private String location;
    private String notes;
    private int coordinatorId;
    private int adminId;

    public Event(int id, String name, LocalDateTime time, String location, String notes, int coordinatorId, int adminId) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.location = location;
        this.notes = notes;
        this.coordinatorId = coordinatorId;
        this.adminId = adminId;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public String getNotes() {
        return notes;
    }

    @Override
    public int getCoordinatorId() {
        return coordinatorId;
    }

    @Override
    public int getAdminId() {
        return adminId;
    }
}