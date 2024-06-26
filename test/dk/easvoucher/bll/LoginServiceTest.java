package dk.easvoucher.bll;

import dk.easvoucher.be.user.Employee;
import dk.easvoucher.be.user.UserRole;
import dk.easvoucher.dal.LoginDAO;
import dk.easvoucher.exeptions.ExceptionHandler;
import dk.easvoucher.exeptions.ExceptionMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class LoginServiceTest {
    @Mock
    private LoginDAO loginDAO;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getEmployee_ShouldReturnAuthenticatedEmployee_WhenCredentialsAreCorrect() throws ExceptionHandler {
        // Arrange
        String username = "admin";
        String password = "admin";
        UserRole userRole = UserRole.ADMIN;
        Employee expectedEmployee = new Employee();
        expectedEmployee.setId(66);
        expectedEmployee.setUsername(username);
        expectedEmployee.setRole(userRole);
        when(loginDAO.loginAuth(username, password)).thenReturn(expectedEmployee);
        // Act
        Employee authenticatedEmployee = loginService.getEmployee(username, password);
        // Assert
        assertEquals(expectedEmployee, authenticatedEmployee);
    }
    @Test
    void getEmployee_ShouldThrowExceptionHandler_WhenCredentialsAreIncorrect() throws ExceptionHandler {
        // Arrange
        String username = "invalidUser";
        String password = "invalidPassword";
        String errorMessage = "The username or password you entered is not correct.";

        when(loginDAO.loginAuth(username, password)).thenThrow(new ExceptionHandler(errorMessage));

        // Act & Assert
        assertThrows(ExceptionHandler.class, () -> {
            try {
                loginService.getEmployee(username, password);
            } catch (ExceptionHandler ex) {
                // You can add further assertions if necessary
                assertEquals(ExceptionMessage.INCORRECT_CREDENTIALS.getValue(), ex.getMessage());
                throw ex;
            }
        });
    }
}