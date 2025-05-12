// package hellocucumber;

// import static org.junit.jupiter.api.Assertions.*;
// import java.time.LocalDate;
// import java.util.ArrayList;
// import java.util.List;
// import dtu.ProjectManagementApp.businessLogic.*;
// import io.cucumber.java.en.Given;
// import io.cucumber.java.en.When;
// import io.cucumber.java.en.Then;

// public class WB_getCurrentSpentHoursSteps {
//     private Activity activity;
//     private Project project;
//     private List<TimeRegistration> timeRegistrations;
//     private double result;
//     private Employee employee;

//     @Given("a project with name {string} and ID {string} exists")
//     public void aProjectWithNameAndIDExists(String name, String id) {
//         project = new Project(name, id);
//         timeRegistrations = new ArrayList<>();
//     }

//     @Given("an activity with name {string} and ID {string} exists")
//     public void anActivityWithNameAndIDExists(String name, String id) {
//         activity = new Activity(id, name);
//     }

//     @Given("an employee named {string} {string} exists")
//     public void anEmployeeNamedExists(String firstName, String lastName) {
//         employee = new Employee(firstName, lastName);
//     }

//     @When("the user checks the current spent hours with an empty list")
//     public void theUserChecksTheCurrentSpentHoursWithAnEmptyList() {
//         result = activity.getCurrentSpentHours(new ArrayList<>());
//     }

//     @Given("the time registrations contain one entry with null activity")
//     public void theTimeRegistrationsContainOneEntryWithNullActivity() {
//         timeRegistrations.add(new TimeRegistration(employee, project, null, 
//             LocalDate.now(), 5.0, "Test work"));
//     }

//     @When("the user checks the current spent hours")
//     public void theUserChecksTheCurrentSpentHours() {
//         result = activity.getCurrentSpentHours(timeRegistrations);
//     }

//     @Given("the time registrations contain one entry with activity ID {string}")
//     public void theTimeRegistrationsContainOneEntryWithActivityID(String activityId) {
//         Activity differentActivity = new Activity(activityId, "Different Activity");
//         timeRegistrations.add(new TimeRegistration(employee, project, differentActivity, 
//             LocalDate.now(), 5.0, "Test work"));
//     }

//     @Given("the time registrations contain two entries with activity ID {string} and hours {double} and {double}")
//     public void theTimeRegistrationsContainTwoEntriesWithActivityIDAndHours(String activityId, 
//             double hours1, double hours2) {
//         timeRegistrations.add(new TimeRegistration(employee, project, activity, 
//             LocalDate.now(), hours1, "First entry"));
//         timeRegistrations.add(new TimeRegistration(employee, project, activity, 
//             LocalDate.now(), hours2, "Second entry"));
//     }

//     @Given("the time registrations contain three entries: one with null activity, one with ID {string}, and one with ID {string} and {double} hours")
//     public void theTimeRegistrationsContainThreeEntries(String diffId, String matchId, double hours) {
//         Activity differentActivity = new Activity(diffId, "Different Activity");
//         timeRegistrations.add(new TimeRegistration(employee, project, null, 
//             LocalDate.now(), 1.0, "Null activity"));
//         timeRegistrations.add(new TimeRegistration(employee, project, differentActivity, 
//             LocalDate.now(), 2.0, "Different activity"));
//         timeRegistrations.add(new TimeRegistration(employee, project, activity, 
//             LocalDate.now(), hours, "Matching activity"));
//     }

//     @Then("the result should be {double}")
//     public void theResultShouldBe(double expected) {
//         assertEquals(expected, result);
//     }
// }