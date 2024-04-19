package dk.easvoucher.bll;


import dk.easvoucher.be.event.Event;
import dk.easvoucher.be.user.UserRole;
import dk.easvoucher.exeptions.ExceptionMessage;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


import dk.easvoucher.be.user.Employee;
import dk.easvoucher.exeptions.ExceptionHandler;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;



public class CreateEventLogicTest {
    private CreateEventLogic logic;

    @Before
    public void setUp() {
        logic = new CreateEventLogic();
    }

    @Test
    @DisplayName("Test getCoordinatorsList method")
    public void testGetCoordinatorsList() throws ExceptionHandler {

        // Arrange
        Employee expectedCoordinator = new Employee(26, "Lorenzo", UserRole.COORDINATOR);

        // Act
        List<Employee> actualList = logic.getCoordinatorsList();

        // Assert
        assertTrue(actualList.contains(expectedCoordinator));
    }

    @Test
    @DisplayName("Test create an event")
    public void testCreateEvent() throws ExceptionHandler {

        // Arrange

        // Create new event object
        Event event = new Event();
        event.setName("TON-618");
        event.setLocation("Milky way alley");
        event.setStartDate(Date.valueOf("2024-04-04"));
        event.setStartTime(Time.valueOf("20:00:00"));
        // Head Coordinator
        Employee headCoordinator = new Employee();
        headCoordinator.setId(2);
        event.setCreatedBy(headCoordinator);
        // Assign zero coordinator to the event
        List<Employee> assignedCoordinators = new ArrayList<>();
        event.setCoordinators(assignedCoordinators);

        // Act
        logic.createEvent(event);

        // Assert
        // When event id is not 0, means event created and event object successfully updated with the retrieved id from the database
        assertNotEquals(event.getId(), 0);
    }



    @Test
    @DisplayName("Test failure in creating an event")
    public void testCreateEventFailure() throws ExceptionHandler {

        // Arrange
        //  We can get errors by not assigning required values for the event such as name, location , etc.

        // Create new event object
        Event event = new Event();

        // Head Coordinator
        Employee headCoordinator = new Employee();
        headCoordinator.setId(2);
        event.setCreatedBy(headCoordinator);

        // Assign zero coordinator to the event
        List<Employee> assignedCoordinators = new ArrayList<>();
        event.setCoordinators(assignedCoordinators);


        // Act and Assert

        // Act and Assert
        // Verify that the exception is thrown when trying to create the event
        assertThrows(ExceptionHandler.class, () -> {
            try {
                logic.createEvent(event);
            } catch (ExceptionHandler ex) {
                // You can add further assertions if necessary
                assertEquals(ExceptionMessage.INSERTION_FAILED.getValue(), ex.getMessage());
                throw ex;
            }
        });
    }
}
