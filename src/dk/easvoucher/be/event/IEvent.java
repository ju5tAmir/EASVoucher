package dk.easvoucher.be.event;

import dk.easvoucher.be.user.Employee;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface IEvent {
    int getId();
    void setId(int id);

    String getName();
    void setName(String name);

    List<Note> getNotes();
    void setNotes(List<Note> notes);

    String getLocation();
    void setLocation(String location);

    Date getStartDate();
    void setStartDate(Date startDate);

    Time getStartTime();
    void setStartTime(Time startTime);

    Date getEndDate();
    void setEndDate(Date startDate);

    Time getEndTime();
    void setEndTime(Time startTime);

    Employee getCreatedBy();
    void setCreatedBy(Employee employee);

    List<Employee> getCoordinators();
    void setCoordinators(List<Employee> coordinators);
}
