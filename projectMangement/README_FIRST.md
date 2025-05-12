# General
The project can be run with any installed JDK, starting with JDK 17.  

It contains all the necessary libraries to run Cucumber tests, JUnit 5 tests, and JUnit 4 tests. In addition, it contains the reference to the Mockito libraries.

# Running the tests

The tests can  be run by running the `RunCucumberTest` class in test folder package <hellocucumber>, which is a JUnit 5 test. This class will run all the Cucumber tests in the project.

The tests can also be run through Maven, e.g., `mvn clean test`, Eclipse (select the project and then run as JUnit test), and IntelliJ (select the project and then run all tests).


# Running the application
The application can be run by running the `Main` class in the src folder package <dtu.ProjectManagementApp.businessLogic> This class will start the application and run the main method.

# Using the application
The application is accessible through the terminal, and the user can enter prompts to interact with the application. The user can add, edit, delete projects, activities, employees, time registrations, generate reports, find available employees and mor . The application will respond with messages indicating the success or failure of the commands.

## TEST USERS
To login as a test user, we have provided two test users with the following credentials:
- `huba`
- `sike`

After login the user sees there main menu from where the user can enter a number to select the desired path from the menu.