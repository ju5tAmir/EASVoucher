package dk.easvoucher.bll;


import dk.easvoucher.be.event.Event;
import dk.easvoucher.be.user.Employee;
import dk.easvoucher.be.user.UserRole;
import dk.easvoucher.dal.AdminDAO;
import dk.easvoucher.exeptions.ExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class AdminLogicTest {

    @Mock
    private AdminDAO adminDAO;

    @InjectMocks
    private AdminLogic adminLogic;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllEmployees_ShouldReturnListOfEmployees_WhenEmployeesExist() throws ExceptionHandler {
        // Arrange
        List<Employee> expectedEmployees = new ArrayList<>();
        expectedEmployees.add(new Employee(100,"John", UserRole.ADMIN));
        expectedEmployees.add(new Employee(200, "Alice", UserRole.COORDINATOR));
        expectedEmployees.add(new Employee(201, "Ali", UserRole.ADMIN));
        expectedEmployees.add(new Employee(220, "Bea", UserRole.COORDINATOR));

        when(adminDAO.getAllEmployees()).thenReturn(expectedEmployees);

        // Act
        List<Employee> actualEmployees = adminLogic.getAllEmployees();

        // Assert
        assertEquals(expectedEmployees.size(), actualEmployees.size());
        assertEquals(expectedEmployees.get(0), actualEmployees.get(0));
        assertEquals(expectedEmployees.get(1), actualEmployees.get(1));
    }

    @Test
    void createNewEmployee_ShouldReturnNewEmployee() throws ExceptionHandler {
        // Arrange
        String username = "testUser..";
        String password = "testPassword";
        UserRole role = UserRole.ADMIN;

        Employee expectedEmployee = new Employee(41, username, role);

        when(adminDAO.createEmployee(username, password, role)).thenReturn(expectedEmployee);

        AdminLogic adminLogic = new AdminLogic();

        // Act
        Employee createdEmployee = adminLogic.createNewEmployee(username, password, role);

        // Assert
        assertEquals(expectedEmployee, createdEmployee);
    }

    @Test
    void removeUser() throws ExceptionHandler {
        // Arrange
        Employee employeeToRemove = new Employee(41, "testUser..", UserRole.ADMIN);

        // Mock the behavior of the AdminDAO
        doNothing().when(adminDAO).removeEmployee(employeeToRemove);

        // Act
        adminLogic.removeUser(employeeToRemove);

        verify(adminDAO).removeEmployee(employeeToRemove);
    }

    @Test
    void updateUser() throws ExceptionHandler {
        //Arrange
        Employee employeeToUpdate = new Employee(42, "NewUser", UserRole.COORDINATOR);
        String newPassword = "newPassword";
        // Mock the behavior of the AdminDAO
        doNothing().when(adminDAO).updateEmployee(employeeToUpdate, newPassword);

        // Act
        adminLogic.updateUser(employeeToUpdate, newPassword);
        // Assert
        verify(adminDAO).updateEmployee(employeeToUpdate, newPassword);

    }

    @Test
    void getAllEvents() throws ExceptionHandler {
        // Arrange
        List<Event> expectedEvents = new ArrayList<>();
        // Add some events to the list...
        expectedEvents.add(new Event(/* event details */));
        expectedEvents.add(new Event(/* event details */));

        when(adminDAO.getAllEvents()).thenReturn(expectedEvents);

        // Act
        List<Event> actualEvents = adminLogic.getAllEvents();

        // Assert
        assertEquals(expectedEvents.size(), actualEvents.size());
        for (int i = 0; i < expectedEvents.size(); i++) {
            assertEquals(expectedEvents.get(i), actualEvents.get(i));
        }    }

    @Test
    void removeEvent() throws ExceptionHandler {
        // Arrange
        Event eventToRemove = new Event();
        eventToRemove.setName("Birthday of no one");
        doNothing().when(adminDAO).removeEvent(eventToRemove);

        // Act
        adminLogic.removeEvent(eventToRemove);

        // Assert
        // Check if the event was removed successfully by verifying it does not exist in the list of events
        List<Event> remainingEvents = adminLogic.getAllEvents();
        assertFalse(remainingEvents.contains(eventToRemove));
    }

}