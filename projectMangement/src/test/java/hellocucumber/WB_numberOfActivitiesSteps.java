// package hellocucumber;

// import java.util.Map;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertFalse;

// import dtu.ProjectManagementApp.businessLogic.Activity;
// import dtu.ProjectManagementApp.businessLogic.Employee;
// import dtu.ProjectManagementApp.businessLogic.Project;
// import dtu.ProjectManagementApp.businessLogic.ProjectManagementAppBL;
// import dtu.ProjectManagementApp.businessLogic.SystemStorage;
// import io.cucumber.java.en.Given;
// import io.cucumber.java.en.Then;
// import io.cucumber.java.en.When;

// public class WB_numberOfActivitiesSteps {
// 	Exception e;
//     private SystemStorage systemStorage = TestContext.getSystemStorage();
//     private ProjectManagementAppBL blController = new ProjectManagementAppBL(systemStorage);
//     int weekActivities=0;
    
//     @Given("a user with name {string}, surname {string} and ID {string} exists")
//     public void aUserWithIDExists(String name, String surname, String userId) {
//         systemStorage.addEmployee(new Employee(name, surname, userId)); // Simulate user creation
//     }
//     @Given("the user {string} is logged in")
//     public void theUserWithIDIsLoggedIn(String userId) {
//         blController.login(userId); // Simulate user login
//     }	
// 	@Given("a project named {string} with id {string} exists")
// 	public void aProjectNamedWithIdExists(String projectName, String projectId) {
// 		systemStorage.addProject(new Project(projectName, projectId));
// 	}
	
// 	@Given("the user with ID {string} is assigned to activity {string} to project with id {string}")
// 	public void theUserWithIDIsAssignedToActivitiesAndInProjectWithId(String employeeId, String activityName, String projectId) {
// 		systemStorage.getProject(projectId).addActivity(new Activity(activityName));
// 		try {
//             systemStorage.getProject(projectId).getActivity(activityName).assignEmployee(employeeId);
//         } catch (Exception e) {
//             this.e = e; // Store the exception for later verification
//         }
// 	}

// 	@Given("the activity {string} from project with ID {string} with the following details exits:")
// 	public void theActivityFromProjectWithIDWithTheFollowingDetailsExits(String activityName, String projectId, io.cucumber.datatable.DataTable dataTable) {
// 		Project project = systemStorage.getProject(projectId);
//         if (project == null) {
//             throw new IllegalArgumentException("Project with ID " + projectId + " does not exist.");
//         }

//         Activity activity = project.getActivity(activityName);
//         if (activity == null) {
//             throw new IllegalArgumentException("Activity with name " + activityName + " does not exist in project " + projectId + ".");
//         }

//         Map<String, String> details = dataTable.asMap(String.class, String.class);

//         if (details.containsKey("name")) {
//             activity.setActivityName(details.get("name"));
//         }
//         if (details.containsKey("startWeek")) {
//             activity.setStartDate(details.get("startWeek"));
//         }
//         if (details.containsKey("endWeek")) {
//             activity.setEndDate(details.get("endWeek"));
//         }
	    
// 	}

// 	@When("the user tests ID {string} to count the number of aktivities in project with ID {string} at week {string}")
// 	public void theUserTastsIDToCountTheNumberOfAktivitiesAtWeek(String employeeId,String projectId , String weekNumber) {
// 		weekActivities = systemStorage.getProject(projectId).getNumberOfWeekActivityForEmployee(employeeId, weekNumber);	
// 	}

// 	@Then("the number of activities for the ID {string} will be {int}")
// 	public void theNumberOfActivitiesForTheIDWillBe(String string, Integer int1) {
// 		assertEquals(weekActivities, int1);
	    
// 	}

// 	@Given("a user with ID {string} does not exists")
// 	public void aUserWithIDDoesNotExists(String employeeId) {
// 	    assertFalse(blController.employeeExists(employeeId));
// 	}


// }
