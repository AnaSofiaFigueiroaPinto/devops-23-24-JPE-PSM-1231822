package com.greglturnquist.payroll;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {

    @Test
    public void createValidEmployee() {
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = "Fellowship of the Ring";
        String jobTitle = "Ring Bearer";
        int jobYears = 0;
        String emailField = "frodo_Baggins123@gmail.com";

        assertDoesNotThrow(() -> new Employee(firstName, lastName, description, jobTitle, jobYears, emailField));
    }

    @Test
    public void createInvalidEmployeeWithEmptyFirstName() {
        String firstName = "";
        String lastName = "Baggins";
        String description = "Fellowship of the Ring";
        String jobTitle = "Ring Bearer";
        int jobYears = 2;
        String emailField = "frodo_Baggins123@gmail.com";

        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears, emailField));
    }

    @Test
    public void createInvalidEmployeeWithNullLastName() {
        String firstName = "Frodo";
        String lastName = null;
        String description = "Fellowship of the Ring";
        String jobTitle = "Ring Bearer";
        int jobYears = 2;
        String emailField = "frodo_Baggins123@gmail.com";

        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears, emailField));
    }

    @Test
    public void createInvalidEmployeeWithEmptyDescription() {
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = "";
        String jobTitle = "Ring Bearer";
        int jobYears = 3;
        String emailField = "frodo_Baggins123@gmail.com";

        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears, emailField));
    }

    @Test
    public void createInvalidEmployeeWithNullJobTitle() {
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = "Fellowship of the Ring";
        String jobTitle = null;
        int jobYears = 3;
        String emailField = "frodo_Baggins123@gmail.com";

        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears, emailField));
    }

    @Test
    public void createInvalidEmployeeWithNegativeJobYears() {
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = "Fellowship of the Ring";
        String jobTitle = "Ring Bearer";
        int jobYears = -1;
        String emailField = "frodo_Baggins123@gmail.com";

        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears, emailField));
    }
    @Test
    public void createInvalidEmployeeWithEmptyEmailField() {
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = "Fellowship of the Ring";
        String jobTitle = "Ring Bearer";
        int jobYears = 2;
        String emailField = "";

        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears, emailField));
    }

    @Test
    public void createInvalidEmployeeWithNullEmailField() {
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = "Fellowship of the Ring";
        String jobTitle = "Ring Bearer";
        int jobYears = 2;
        String emailField = null;

        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears, emailField));
    }

    @Test
    public void createInvalidEmployeeWithInvalidEmailField() {
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = "Fellowship of the Ring";
        String jobTitle = "Ring Bearer";
        int jobYears = 2;
        String emailField = "frodo_Baggins123gmail.com";

        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears, emailField));
    }


}