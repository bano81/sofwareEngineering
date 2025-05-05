package hellocucumber;

import static org.junit.jupiter.api.Assertions.assertTrue;

import dtu.ProjectManagementApp.businessLogic.BLController;
import dtu.ProjectManagementApp.businessLogic.SystemStorage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class EmployeeSteps {

    private SystemStorage systemStorage = TestContext.getSystemStorage();
    private BLController blController = new BLController(systemStorage);

    @When("the user creates an employee with name {string}, surname {string} and ID {string}")
    public void theUserCreatesAnEmployeeWithNameSurname(String firstName, String surName, String employeeId) {
        assertTrue(blController.createEmployee(firstName, surName, employeeId));
    }

    @Then("a user with id {string} is added to the list of employees")
    public void theUserIsAddedToTheListOfEmployees(String employeeId) {
        assertTrue(blController.employeeExists(employeeId)); // Check if the employee exists in the list
    }
}
