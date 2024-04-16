package dk.easvoucher.gui.dashboard.coordinator.event.create;

import dk.easvoucher.be.event.Event;
import dk.easvoucher.be.event.Note;
import dk.easvoucher.be.user.Employee;
import dk.easvoucher.bll.CreateEventLogic;
import dk.easvoucher.exeptions.ExceptionHandler;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SingleSelectionModel;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CreateEventModel {
    private final ObservableList<Employee> coordinators = FXCollections.observableArrayList();
    private final SimpleObjectProperty<Employee> loggedInCoordinator = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Event> event = new SimpleObjectProperty<>();
    private final CreateEventLogic logic = new CreateEventLogic();

    public CreateEventModel() {
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
        this.coordinators.setAll(logic.getCoordinatorsList());
    }

    public List<Employee> getCoordinatorsList() throws ExceptionHandler{
        setAllowedECs();
        return coordinators;
    }

    public Employee getLoggedInCoordinator() {
        return loggedInCoordinator.get();
    }

    public void setLoggedInCoordinator(Employee coordinator) {
        this.loggedInCoordinator.set(coordinator);
    }

    public void setEventName(String name){
        event.get().setName(name);
    }

    public void setEventLocation(String location){
        event.get().setLocation(location);
    }

    public void setEventNotes(List<String> noteTitles){
        List<Note> notes = new ArrayList<>();
        for (String title : noteTitles){
            Note note = new Note();
            note.setNote(title);
            notes.add(note);
        }

        event.get().setNotes(notes);
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

    public void setAllowedCoordinators(List<Employee> coordinators){
        event.get().setCoordinators(coordinators);
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

    public void createEvent() throws ExceptionHandler{
        logic.createEvent(event.get());
    }

    public void setCreator(Employee coordinator){
        event.get().setCreatedBy(coordinator);
    }

    public void clear() {
        coordinators.clear();
        event.setValue(null);
        initEvent();
    }
}
