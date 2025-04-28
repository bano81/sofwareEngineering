package hellocucumber;

import static org.junit.jupiter.api.Assertions.*;

import dtu.ProjectManagementApp.businessLogic.BLController;
import dtu.ProjectManagementApp.businessLogic.SystemStorage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginLogoutSteps {

    BLController blController = new BLController();
    

    @Given("An employee with ID {string} exists")
    public void anEmployeeWithIDExists(String employeeId) {
        SystemStorage.addEmployee("John", "Doe", employeeId);
    }

    @When("I login with ID {string}")
    public void iLoginWithID(String employeeId) {
        blController.login(employeeId);
    }

    @Then("The employee with ID {string} should be logged in")
    public void theEmployeeWithIDShouldBeLoggedIn(String string) {
        assertTrue(SystemStorage.getLoggedInEmployee().equals(string));
    }
}
