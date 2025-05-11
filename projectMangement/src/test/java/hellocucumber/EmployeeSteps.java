package hellocucumber;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dtu.ProjectManagementApp.businessLogic.ProjectManagementAppBL;
import dtu.ProjectManagementApp.businessLogic.SystemStorage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class EmployeeSteps {

    private SystemStorage systemStorage = TestContext.getSystemStorage();
    private ProjectManagementAppBL blController = new ProjectManagementAppBL(systemStorage);
    String e = "";

    @When("the user creates an employee with name {string}, surname {string} and ID {string}")
    public void theUserCreatesAnEmployeeWithNameSurname(String firstName, String surName, String employeeId) {
        try {
            blController.createEmployee(firstName, surName, employeeId); // Simulate employee creation
        } catch (Exception e) {
            this.e = e.getMessage(); // Capture the exception if thrown
        }
    }

    @Then("a user with id {string} is added to the list of employees")
    public void theUserIsAddedToTheListOfEmployees(String employeeId) {
        assertTrue(blController.employeeExists(employeeId)); // Check if the employee exists in the list
    }

    @Then("the system will return the error {string}")
    public void theSystemWillReturnTheError(String error) {
        assertEquals(error, e);
    }

}
