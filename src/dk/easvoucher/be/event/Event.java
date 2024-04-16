package dk.easvoucher.be.event;

import dk.easvoucher.be.user.Employee;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event implements IEvent{
    private int id;
    private String name;
    private String location;
    private List<Note> notes;
    private Date startDate;
    private Time startTime;
    private Date endDate;
    private Time endTime;
    private Employee createdBy;
    private List<Employee> coordinators;

    /**
     * Default constructor method
     */
    public Event(){
        notes = new ArrayList<>();

    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public List<Note> getNotes() {
        return notes;
    }

    @Override
    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public Time getStartTime() {
        return startTime;
    }

    @Override
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Time getEndTime() {
        return endTime;
    }

    @Override
    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    @Override
    public Employee getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(Employee createdBy) {
        this.createdBy = createdBy;
    }

    public List<Employee> getCoordinators() {
        return coordinators;
    }

    @Override
    public void setCoordinators(List<Employee> coordinators) {
        this.coordinators = coordinators;
    }

    public void setNote(Note note){
        this.notes.add(note);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", notes=" + notes +
                ", startDate=" + startDate +
                ", startTime=" + startTime +
                ", endDate=" + endDate +
                ", endTime=" + endTime +
                ", createdBy=" + createdBy +
                ", coordinators=" + coordinators +
                '}';
    }
}
