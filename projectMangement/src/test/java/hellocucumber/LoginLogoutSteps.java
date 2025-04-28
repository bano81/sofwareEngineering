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
        assertTrue(SystemStorage.employeeExists(employeeId));
    }

    @When("I login with ID {string}")
    public void iLoginWithID(String employeeId) {
        blController.login(employeeId);
    }

    @Then("The employee with ID {string} should be logged in")
    public void theEmployeeWithIDShouldBeLoggedIn(String string) {
        assertEquals(SystemStorage.getLoggedInEmployee().getEmployeeId(), string);
    }

    @Given("No employee with ID {string} exists")
    public void noEmployeeWithIDExists(String arg0) {
        assertFalse(SystemStorage.employeeExists(arg0));
    }


    @Then("The login attempt should fail")
    public void theLoginAttemptShouldFail() {
    }
}
