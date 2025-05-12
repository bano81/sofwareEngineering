package hellocucumber;

import dtu.ProjectManagementApp.businessLogic.Project;
import dtu.ProjectManagementApp.businessLogic.ProjectManagementAppBL;
import dtu.ProjectManagementApp.businessLogic.SystemStorage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDate;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WB_weeksUntilDeadlineSteps {
    private String currentDate;

    private int calculatedWeeksUntilDeadline;
    private SystemStorage systemStorage = TestContext.getSystemStorage();
    private ProjectManagementAppBL blController = new ProjectManagementAppBL(systemStorage);

    @Given("a project with ID {string} exists")
    public void aProjectWithIDExists(String projectId) {
        Project project = new Project("Test Project", projectId);
        systemStorage.getProjects().add(project); // Simulate project creation
    }

    @Given("the project with ID {string} has deadline null")
    public void theProjectHasDeadlineNull(String projectId) {
        Project project = systemStorage.getProject(projectId);
        if (project != null) {
            project.setDeadline(null); // Set the deadline to null
        }
    }

    @When("the user checks the number of weeks until deadline for project with ID {string}")
    public void theUserChecksTheNumberOfWeeksUntilDeadlineForProject(String projectId) {
        Project project = systemStorage.getProject(projectId);
        if (project != null) {
            calculatedWeeksUntilDeadline = project.getWeeksUntilDeadline(currentDate);
        }
    }

    @Then("the result should be {int}")
    public void theResultShouldBe(int wantedWeeksUntilDeadline) {
        assertEquals(wantedWeeksUntilDeadline, calculatedWeeksUntilDeadline); // Check if the calculated weeks until deadline matches the expected value
    }

    @Given("the project with ID {string} has deadline {string}")
    public void theProjectHasDeadline(String projectId, String deadline) {
        Project project = systemStorage.getProject(projectId);
        if (project != null) {
            project.setDeadline(deadline); // Set the deadline to the specified value
        }
    }

    @Given("the current date is year {int} and week {int}")
    public void theCurrentDateIsYearAndWeek(int currentYear, int currentWeek) {
        String yearLastTwoDigits = String.valueOf(currentYear % 100);
        String weekFormatted = String.format("%02d", currentWeek);
        currentDate = yearLastTwoDigits + "-" + weekFormatted;
    }
}
