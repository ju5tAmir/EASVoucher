package dk.easvoucher.gui.dashboard.coordinator.event.update;

import dk.easvoucher.be.event.Event;
import dk.easvoucher.be.event.Note;
import dk.easvoucher.be.user.Employee;
import dk.easvoucher.bll.CreateEventLogic;
import dk.easvoucher.bll.UpdateEventLogic;
import dk.easvoucher.exeptions.ExceptionHandler;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UpdateEventModel {
    private final ObservableList<Employee> coordinators = FXCollections.observableArrayList();
    private final SimpleObjectProperty<Event> event = new SimpleObjectProperty<>();
    private final UpdateEventLogic logic = new UpdateEventLogic();
    private final CreateEventLogic createLogic = new CreateEventLogic();

    public UpdateEventModel() {
        initEvent();
    }


    private void initEvent() {
        Event e = new Event();
        List<Employee> es = new ArrayList<>();
        List<Note> ns = new ArrayList<>();
        e.setCoordinators(es);
        e.setNotes(ns);
        event.set(e);
    }


    private void setAllowedECs() throws ExceptionHandler {
        this.coordinators.setAll(createLogic.getCoordinatorsList());
    }

    public List<Employee> getCoordinatorsList() throws ExceptionHandler{
        setAllowedECs();
        return coordinators;
    }

    public void setEventName(String name){
        event.get().setName(name);
    }

    public void setEventLocation(String location){
        event.get().setLocation(location);
    }


    public void setStartDate(LocalDate date){
        java.sql.Date d = java.sql.Date.valueOf(date);
        event.get().setStartDate(d);
    }

    public void setStartTime(String t){
        LocalTime localTime = LocalTime.parse(t, DateTimeFormatter.ofPattern("HH:mm"));
        Time time = Time.valueOf(localTime);
        event.get().setStartTime(time);
    }

    public void setEndDate(LocalDate date){
        if (date != null){
            java.sql.Date d = java.sql.Date.valueOf(date);
            event.get().setEndDate(d);
        }
    }

    public void setEndTime(String t){
        if (!t.isEmpty()) {
            LocalTime localTime = LocalTime.parse(t, DateTimeFormatter.ofPattern("HH:mm"));
            Time time = Time.valueOf(localTime);
            event.get().setEndTime(time);
        }
    }

    public void addCoordinator(Employee coordinator){
        event.get().getCoordinators().add(coordinator);
    }
    public void removeCoordinator(Employee coordinator){
        event.get().getCoordinators().remove(coordinator);
    }
    public Event getEvent(){
        return event.get();
    }


    public void clear() {
        coordinators.clear();
        event.setValue(null);
        initEvent();
    }

    public void setEvent(Event event){
        this.event.set(event);
    }

    public void updateEvent() throws ExceptionHandler {
        logic.updateEvent(event.get());
    }

    public void addNote(Note note){
        event.get().getNotes().add(note);
    }
    public void removeNote(String note){
        System.out.println("Before:" + event.get().getNotes());
        event.get().getNotes().removeIf(n -> Objects.equals(n.getNote(), note));
//        Note noteToRemove = new Note();
//        for (Note n: event.get().getNotes()){
//            if (n.getNote() == note){
//                noteToRemove = n;
//            }
//        }
//
//        event.get().getNotes().remove(noteToRemove);
        System.out.println("After:" + event.get().getNotes());

    }
}
