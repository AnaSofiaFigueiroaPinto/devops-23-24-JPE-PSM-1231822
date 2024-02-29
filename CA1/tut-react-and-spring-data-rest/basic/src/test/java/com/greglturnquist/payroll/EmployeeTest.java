package com.greglturnquist.payroll;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {

    @Test
    public void createValidEmployee() throws InstantiationException {
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = "Fellowship of the Ring";
        String jobTitle = "Ring Bearer";
        int jobYears = 0;

        assertDoesNotThrow(() -> new Employee(firstName, lastName, description, jobTitle, jobYears));
    }

    @Test
    public void createInvalidEmployeeWithEmptyFirstName() {
        String firstName = "";
        String lastName = "Baggins";
        String description = "Fellowship of the Ring";
        String jobTitle = "Ring Bearer";
        int jobYears = 2;
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears));
    }

    @Test
    public void createInvalidEmployeeWithNullLastName() {
        String firstName = "Frodo";
        String lastName = null;
        String description = "Fellowship of the Ring";
        String jobTitle = "Ring Bearer";
        int jobYears = 2;
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears));
    }

    @Test
    public void createInvalidEmployeeWithEmptyDescription() {
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = "";
        String jobTitle = "Ring Bearer";
        int jobYears = 3;
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears));
    }

    @Test
    public void createInvalidEmployeeWithNullJobTitle() {
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = "Fellowship of the Ring";
        String jobTitle = null;
        int jobYears = 3;
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears));
    }

    @Test
    public void createInvalidEmployeeWithNegativeJobYears() {
        String firstName = "Frodo";
        String lastName = "Baggins";
        String description = "Fellowship of the Ring";
        String jobTitle = "Ring Bearer";
        int jobYears = -1;
        assertThrows(InstantiationException.class, () -> new Employee(firstName, lastName, description, jobTitle, jobYears));
    }


}