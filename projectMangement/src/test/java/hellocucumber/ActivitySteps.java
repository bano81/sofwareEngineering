package hellocucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import dtu.ProjectManagementApp.businessLogic.SystemStorage;
import dtu.ProjectManagementApp.businessLogic.Activity;
import dtu.ProjectManagementApp.businessLogic.ProjectManagementAppBL;
import dtu.ProjectManagementApp.businessLogic.Employee;
import dtu.ProjectManagementApp.businessLogic.Project;


public class ActivitySteps {

    Exception e;
    private SystemStorage systemStorage = TestContext.getSystemStorage();
    private ProjectManagementAppBL blController = new ProjectManagementAppBL(systemStorage);

    @Given("a user with ID {string} exists")
    public void aUserWithIDExists(String userId) {
        systemStorage.addEmployee(new Employee("test", "test", userId)); // Simulate user creation
    }

    @Given("the user with ID {string} is logged in")
    public void theUserWithIDIsLoggedIn(String userId) {
        blController.login(userId); // Simulate user login
    }
    @Given("a project named {string} with id {string} and deadline {string} exists")
    public void aProjectNamedExists(String projectName, String ID, String deadline) {
        systemStorage.getProjects().add(new Project(ID,projectName,deadline)); // Simulate project creation
    }

    @When("an activity named {string} is created for the project with ID {string}")
    public void anActivityNamedIsCreatedForTheProjectWithID(String activityName, String projectId) {
        systemStorage.getProject(projectId).addActivity(new Activity(activityName)); // Simulate activity creation
    }

    @Then("the list of activities in the project with ID {string} should include {string}")
    public void theListOfActivitiesInTheProjectWithIDShouldInclude(String projectId, String activityName) {
        assertTrue(systemStorage.getProject(projectId).getActivities().stream().anyMatch(a -> a.getActivityName().equals(activityName))); // Check if the activity is in the project
    }

    @Given("an activity named {string} exists in project with ID {string}")
    public void anActivityNamedExistsInProjectWithID(String activityName, String projectId) {
        systemStorage.getProject(projectId).addActivity(new Activity(activityName)); // Simulate activity creation
    }

    @When("the user adds the employee with ID {string} to the activity {string} from project with ID {string}")
    public void theUserAddsTheEmployeeWithIDToTheActivityFromProjectWithID(String employeeId, String activityName, String projectId) {
        // Simulate adding the employee to the activity
        try {
            systemStorage.getProject(projectId).getActivity(activityName).assignEmployee(employeeId);
        } catch (Exception e) {
            this.e = e; // Store the exception for later verification
        }
    }

    @Then("the activity {string} from project with ID {string} should have the employee with ID {string} assigned")
    public void theActivityFromProjectWithIDShouldHaveTheEmployeeWithIDAssigned(String activityName, String projectId, String employeeId) {
        assertTrue(systemStorage.getProject(projectId).getActivity(activityName).getAssignedEmployees().contains(employeeId)); // Check if the activity has the employee assigned
    }

    @When("the logged in employee assigns the activity with name {string} from project with ID {string} to the employee with ID {string}")
    public void theLoggedInEmployeeAssignsTheActivityWithNameFromProjectWithIDToTheEmployeeWithID(String activityName, String projectId, String employeeId) {
        Project project = systemStorage.getProject(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project with ID " + projectId + " does not exist.");
        }

        Activity activity = project.getActivity(activityName);
        if (activity == null) {
            throw new IllegalArgumentException("Activity with name " + activityName + " does not exist in project " + projectId + ".");
        }

        if (!systemStorage.employeeExists(employeeId)) {
            throw new IllegalArgumentException("Employee with ID " + employeeId + " does not exist.");
        }

        activity.assignEmployee(employeeId);
    }

    @Then("the activity {string} from project with ID {string} should be assigned to the employee with ID {string}")
    public void theActivityFromProjectWithIDShouldBeAssignedToTheEmployeeWithID(String activityName, String projectId, String employeeId) {
        Project project = systemStorage.getProject(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project with ID " + projectId + " does not exist.");
        }

        Activity activity = project.getActivity(activityName);
        if (activity == null) {
            throw new IllegalArgumentException("Activity with name " + activityName + " does not exist in project " + projectId + ".");
        }

        assertTrue(activity.getAssignedEmployees().contains(employeeId),
            "Employee with ID " + employeeId + " is not assigned to activity " + activityName + ".");
    }

    @When("the user edits the activity {string} from project with ID {string} with the following details:")
    public void theUserEditsTheActivityFromProjectWithIDWithTheFollowingDetails(String activityName, String projectId, io.cucumber.datatable.DataTable dataTable) {
        Project project = systemStorage.getProject(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project with ID " + projectId + " does not exist.");
        }

        Activity activity = project.getActivity(activityName);
        if (activity == null) {
            throw new IllegalArgumentException("Activity with name " + activityName + " does not exist in project " + projectId + ".");
        }

        Map<String, String> details = dataTable.asMap(String.class, String.class);

        if (details.containsKey("name")) {
            activity.setActivityName(details.get("name"));
        }
        if (details.containsKey("startWeek")) {
            activity.setStartDate(details.get("startWeek"));
        }
        if (details.containsKey("endWeek")) {
            activity.setEndDate(details.get("endWeek"));
        }
        if (details.containsKey("budgetedHours")) {
            activity.setBudgetedHours(Double.parseDouble(details.get("budgetedHours")));
        }
    }

    @Then("the activity {string} from project with ID {string} should have the following details:")
    public void theActivityFromProjectWithIDShouldHaveTheFollowingDetails(String activityName, String projectId, io.cucumber.datatable.DataTable dataTable) {
        Project project = systemStorage.getProject(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project with ID " + projectId + " does not exist.");
        }

        Activity activity = project.getActivity(activityName);
        if (activity == null) {
            throw new IllegalArgumentException("Activity with name " + activityName + " does not exist in project " + projectId + ".");
        }

        Map<String, String> expectedDetails = dataTable.asMap(String.class, String.class);

        if (expectedDetails.containsKey("name")) {
            assertEquals(expectedDetails.get("name"), activity.getActivityName(), "Activity name does not match.");
        }
        if (expectedDetails.containsKey("startWeek")) {
            assertEquals(expectedDetails.get("startWeek"), activity.getStartDate(), "Start week does not match.");
        }
        if (expectedDetails.containsKey("endWeek")) {
            assertEquals(expectedDetails.get("endWeek"), activity.getEndDate(), "End week does not match.");
        }
        if (expectedDetails.containsKey("budgetedHours")) {
            assertEquals(Double.parseDouble(expectedDetails.get("budgetedHours")), activity.getBudgetedHours(), "Budgeted hours do not match.");
        }
    }

    @When("the user removes the employee with ID {string} from the activity {string} from project with ID {string}")
    public void theUserRemovesTheEmployeeWithIDFromTheActivityFromProjectWithID(String employeeId, String activityName, String projectId) {
        Project project = systemStorage.getProject(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project with ID " + projectId + " does not exist.");
        }

        Activity activity = project.getActivity(activityName);
        if (activity == null) {
            throw new IllegalArgumentException("Activity with name " + activityName + " does not exist in project " + projectId + ".");
        }

        activity.removeEmployee(employeeId);
    }

    @Then("the activity {string} from project with ID {string} should not have the employee with ID {string} assigned")
    public void theActivityFromProjectWithIDShouldNotHaveTheEmployeeWithIDAssigned(String activityName, String projectId, String employeeId) {
        Project project = systemStorage.getProject(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project with ID " + projectId + " does not exist.");
        }

        Activity activity = project.getActivity(activityName);
        if (activity == null) {
            throw new IllegalArgumentException("Activity with name " + activityName + " does not exist in project " + projectId + ".");
        }

        assertFalse(activity.getAssignedEmployees().contains(employeeId),
            "Employee with ID " + employeeId + " is still assigned to activity " + activityName + ".");
    }

    //@When("the user records {double} hours spent on activity {string} from project with ID {string}")
    //public void theUserRecordsHoursSpentOnActivityFromProjectWithID(double hours, String activityName, String projectId) {
    //    Project project = systemStorage.getProject(projectId);
    //    if (project == null) {
    //        throw new IllegalArgumentException("Project with ID " + projectId + " does not exist.");
    //    }
//
    //    Activity activity = project.getActivity(activityName);
    //    if (activity == null) {
    //        throw new IllegalArgumentException("Activity with name " + activityName + " does not exist in project " + projectId + ".");
    //    }
//
    //    activity.setCurrentSpentHours(hours);
    //}

    //@When("the user records {double} more hours spent on activity {string} from project with ID {string}")
    //public void theUserRecordsMoreHoursSpentOnActivityFromProjectWithID(double additionalHours, String activityName, String projectId) {
    //    Project project = systemStorage.getProject(projectId);
    //    if (project == null) {
    //        throw new IllegalArgumentException("Project with ID " + projectId + " does not exist.");
    //    }
//
    //    Activity activity = project.getActivity(activityName);
    //    if (activity == null) {
    //        throw new IllegalArgumentException("Activity with name " + activityName + " does not exist in project " + projectId + ".");
    //    }
//
    //    double currentHours = activity.getCurrentSpentHours();
    //    activity.setCurrentSpentHours(currentHours + additionalHours);
    //}

    //@Then("the activity {string} from project with ID {string} should have {double} hours spent")
    //public void theActivityFromProjectWithIDShouldHaveHoursSpent(String activityName, String projectId, double expectedHours) {
    //    Project project = systemStorage.getProject(projectId);
    //    if (project == null) {
    //        throw new IllegalArgumentException("Project with ID " + projectId + " does not exist.");
    //    }
//
    //    Activity activity = project.getActivity(activityName);
    //    if (activity == null) {
    //        throw new IllegalArgumentException("Activity with name " + activityName + " does not exist in project " + projectId + ".");
    //    }
//
    //    assertEquals(expectedHours, activity.getCurrentSpentHours(), 0.01,
    //        "Current spent hours do not match expected hours.");
    //}
    
    @Then("the activities {string} and {string} from project with ID {string} should have different IDs")
    public void theActivitiesShouldHaveDifferentIDs(String activity1Name, String activity2Name, String projectId) {
        Project project = systemStorage.getProject(projectId);
        if (project == null) {
            throw new IllegalArgumentException("Project with ID " + projectId + " does not exist.");
        }

        Activity activity1 = project.getActivity(activity1Name);
        Activity activity2 = project.getActivity(activity2Name);
        
        assertNotNull(activity1, "First activity not found");
        assertNotNull(activity2, "Second activity not found");
        assertNotEquals(activity1.getActivityId(), activity2.getActivityId(), 
            "Activities should have different IDs");
    }
}
