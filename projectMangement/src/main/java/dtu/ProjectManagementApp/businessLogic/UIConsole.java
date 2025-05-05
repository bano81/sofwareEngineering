package dtu.ProjectManagementApp.businessLogic;

import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

public class UIConsole {
    BLController blController = new BLController(); // Create an instance of BLController
    //SystemStorage systemStorage = new SystemStorage(); // Create an instance of SystemStorage


    public void displayMessage(int messageNumber, boolean state) {
		if(state) {
			switch(messageNumber) {
			case 0: 
				System.out.println("Error: Each employee must have a unique ID; duplicate or multiple IDs per employee are not allowed!");
				break;
			case 1:
				break;
			case 2:
				break;
			default:
				break;
			}
		}
	}

    public void displayLogin(Scanner sc) {
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.println("Please enter your employee ID to log in:");
            String employeeId = sc.nextLine();
            if (blController.login(employeeId)) {
                System.out.println("Login successful! Welcome, " + SystemStorage.getLoggedInEmployee().getName() + " " + SystemStorage.getLoggedInEmployee().getSurname() + ".");
                loggedIn = true; // Exits loop if logged in true
            } else {
                System.out.println("Invalid employee ID. Please try again.");
            }
        }
    }

    public int start(Scanner sc) throws ParseException {
        int choice=2;        
        if (blController.getLoggedInEmployeeRole() == 0) {
            //System.out.println("Login as Admin successful! Welcome, " + SystemStorage.getLoggedInEmployee().getName() + " " + SystemStorage.getLoggedInEmployee().getSurname() + ".");
            choice = displayChoicesForManager(sc);
            System.out.println("");
            executeChoiceForManager(choice, sc);
        } else if (blController.getLoggedInEmployeeRole() == 1) {
            //System.out.println("Login as Manager successful! Welcome, " + SystemStorage.getLoggedInEmployee().getName() + " " + SystemStorage.getLoggedInEmployee().getSurname() + ".");
            choice = displayChoicesForManager(sc);
            System.out.println("");
            executeChoiceForManager(choice, sc);
        } else if (blController.getLoggedInEmployeeRole() == 2) {
            //System.out.println("Login as Employee successful! Welcome, " + SystemStorage.getLoggedInEmployee().getName() + " " + SystemStorage.getLoggedInEmployee().getSurname() + ".");
            choice = displayChoicesForEmployee(sc);
            System.out.println("");
            executeChoiceForEmployee(choice, sc);
        } else {
            System.out.println("Invalid employee ID. Please try again.");
        }
        return choice; // Return the choice made by the user
    }

    public void readNewEmployee(Scanner sc) {
        System.out.print("Please enter first name: ");
        String name = sc.nextLine();
        System.out.print("Please enter surname: ");
        String surname = sc.nextLine();
        System.out.print("Please enter employee ID: ");
        String emplyeeId = sc.nextLine();
        boolean employeeExists =  blController.createEmployee(name, surname, emplyeeId); //systemStorage.employeeExists(emplyeeId, name, surname); // Check if the employee already exists
        if (!employeeExists) {
            System.out.println("Error: Employee with the same ID or name already exists.");
        }   else {
            //blController.createEmployee(name, surname, emplyeeId); // Create a new employee
            System.out.println("Employee created successfully.");
        }
    }

    public void readNewActivity(Scanner sc){
                System.out.print("Please enter project name: ");
                String projectName = sc.nextLine();
                System.out.print("Please enter activity name: ");
                String activityName = sc.nextLine();
                System.out.print("Please enter activity ID: ");
                String activityIdStr = sc.nextLine();
                int activityId = Integer.parseInt(activityIdStr); // Convert activity ID to integer
                System.out.print("Please enter employee ID: ");
                blController.createNewActivity(projectName, activityId, activityName);
                //String employeeId = sc.nextLine();
                //blController.createNewActivity(projectName, activityId, activityName, employeeId); // Create a new activity
                
                /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                System.out.print("Please enter activity start week (yyyy-MM-dd): ");
                String startDateStr = sc.nextLine();
                Date startDate = sdf.parse(startDateStr);
                System.out.print("Please enter activity end date (yyyy-MM-dd): ");
                String endDateStr = sc.nextLine();
                Date endDate = sdf.parse(endDateStr);
                System.out.print("Please enter activity budgted hours: ");
                double activityBudgtedhour = sc.nextDouble();*/

                
                //int statusChoice = sc.nextInt();
                //addNewActivityToProject(employees, employeeId, activityId, activityName, startDate, endDate,
                //		activityBudgtedhour, activityStatus); // Create a new activity
    }

    public void assignEmployeeToActivity(Scanner sc) {
        String choice = "y";
        System.out.print("Please enter project name: ");
        String projectName = sc.nextLine();
        System.out.print("Please enter activity name: ");
        String activityName = sc.nextLine();
        while (choice.equalsIgnoreCase("y")) {    
            System.out.print("Please enter employee ID: ");
            String employeeId = sc.nextLine();
            blController.assignEmployeeToActivity(projectName, activityName, employeeId); // Assign an employee to an activity
            System.out.println("");
            System.out.print("Do you want to assign another employee to an activity? (y/n): ");
            choice = sc.nextLine();
            choice = choice.toLowerCase(); // Convert to lowercase for comparison
        }
    }

    public void readNewProject(Scanner sc){
                System.out.print("Please enter project name: ");
                String projectName = sc.nextLine();
                System.out.print("Please enter project ID: ");
                String projectId = sc.nextLine();
                blController.createProject(projectId, projectName); // Create a new project
                System.out.println("Project created successfully.");
    }

    public void displayAllActivities(){
       List<Activity> activities = blController.getActivities(); // Get the list of activities
       System.out.printf("%-12s %-15s %-15s %-15s %-16s%n",
               "Activity ID", "Name", "Start Date", "End Date", "Budgeted Hours");
       System.out.printf("%-12s %-15s %-15s %-15s %-16s%n",
               "-----------", "----", "----------", "--------", "---------------");
        for (Activity activity : activities) {
            System.out.printf("%-12d %-15s %-15s %-15s %-16.1f%n",
                    activity.getActivityId(),
                    activity.getActivityName(),
                    activity.getStartDate(),
                    activity.getEndDate(),
                    activity.getBudgetedHours());
        }
    }

    public void displayAllActivitiesForEmployee(String employeeId){
        List<Activity> activities = blController.getActivitiesByEmployee(employeeId); // Get the list of activities for a specific employee
        System.out.printf("%-12s %-15s %-15s %-15s %-16s %n",
                "Activity ID", "Name", "Start Date", "End Date", "Budgeted Hours");
        System.out.printf("%-12s %-15s %-15s %-15s %-16s %n",
                "-----------", "----", "----------", "--------", "---------------");
        for (Activity activity : activities) {
            System.out.printf("%-12d %-15s %-15s %-15s %-16.1f %n",
                    activity.getActivityId(),
                    activity.getActivityName(),
                    activity.getStartDate(),
                    activity.getEndDate(),
                    activity.getBudgetedHours());
        }
    }

    public void displayAllEmployees(){
        List<Employee> employees = blController.getEmployees(); // Get the list of employees
        System.out.printf("%-12s %-15s%n",
                "Employee ID", "Name");
        System.out.printf("%-12s %-15s%n",
                "-----------", "---- ");
        for (Employee employee : employees) {
            System.out.printf("%-12s %-15s%n",
                    employee.getEmployeeId(),
                    employee.getName() + " " + employee.getSurname());
        }
    }

    public void displayAllProjects(){
        List<Project> projects = blController.getProjects(); // Get the list of projects
        System.out.printf("%-12s %-15s%n",
                "Project ID", "Name");
        System.out.printf("%-12s %-15s%n",
                "-----------", "---- ");
        for (Project project : projects) {
            System.out.printf("%-12s %-15s%n",
                    project.getProjectId(),
                    project.getProjectName());
        }
    }

    public void displayAllProjectsForEmployee(String employeeId){
        List<Project> projects = blController.getEmployee(employeeId).getProjectList(); // Get the list of projects for a specific employee
        System.out.printf("%-12s %-15s%n",
                "Project ID", "Name");
        System.out.printf("%-12s %-15s%n",
                "-----------", "---- ");
        for (Project project : projects) {
            System.out.printf("%-12s %-15s%n",
                    project.getProjectId(),
                    project.getProjectName());
        }

    }


    public int displayChoicesForEmployee(Scanner sc) {
        System.out.println("Please choose an option:");
        System.out.println("\t1. Display my projects");
        System.out.println("\t2. Display my activities");
        System.out.println("\t3. Register used hours for an activity");
        System.out.println("\t4. Choose a project manager");
        System.out.println("\t0. Exit");
        System.out.print("# ");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume the newline character left by nextInt()
        return choice;
    }

    public void executeChoiceForEmployee(int choice, Scanner sc) throws ParseException {
        switch (choice) {
            case 1: 
                //System.out.print("List of my projects: ");
                displayAllProjectsForEmployee(SystemStorage.getLoggedInEmployee().getEmployeeId());
                break;
            case 2:
                // Display the list of activities for the logged-in employee
                displayAllActivitiesForEmployee(SystemStorage.getLoggedInEmployee().getEmployeeId()); 
                break;
            case 3:
                // retgister used hours for an activity
                System.out.print("Please enter project name: ");
                String projectName = sc.nextLine();
                System.out.print("Please enter activity ID: ");
                String activityId = sc.nextLine();
                System.out.print("Please enter activity budgted hours: ");
                double activityBudgtedhour = sc.nextDouble();
                //blController.registerUsedHours(projectName, activityId, activityBudgtedhour); // Register used hours for an activity
                System.out.println("Used hours registered successfully.");
                break;
            case 4:
                // Choose a project manager
                System.out.print("Please enter project ID: ");
                String projectId = sc.nextLine();
                // check if project exists
                /*blController.getProject(projectId); // Check if the project exists
                if (project == null) {    
                    System.out.println("Project with ID " + projectId + " does not exist.");
                    break;    
                }*/
                // Display the list of employees who are project managers
                System.out.print("Please enter project employee ID: ");
                String projectManagerId = sc.nextLine();    
                blController.getEmployee(projectManagerId).isProjectManager();
                //blController.chooseProjectManager(projectManagerId); // Choose a project manager
                System.out.println("Project manager chosen successfully.");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }

    }

    public int displayChoicesForManager(Scanner sc) {
        System.out.println("Please choose an option:");
        System.out.println("\t1. Create a new employee");
        System.out.println("\t2. Create a new project");
        System.out.println("\t3. Create a new activity");
        System.out.println("\t4. Display the number of activities assigned to an employee");
        System.out.println("\t5. Assign an employee to an activity");
        System.out.println("\t6. Display all employees");
        System.out.println("\t7. Display all projects");
        System.out.println("\t8. Display all activities for an employee");
        System.out.println("\t0. Exit");

        System.out.print("# ");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume the newline character left by nextInt()
        return choice;
    }

    public void executeChoiceForManager(int choice, Scanner sc) throws ParseException {
        switch (choice) {
            case 1:
                readNewEmployee(sc);
                break;
            case 2:
                readNewProject(sc);
                break;
            case 3:
                readNewActivity(sc);
                break;
            case 4: // Display the number of activities assigned to an employee
                System.out.print("Please enter employee ID: ");
                String employeeId = sc.nextLine();
                Employee employee = blController.getEmployee(employeeId); // Get the employee by ID
                if (employee != null) {
                    employee.countNumberOfActivities(); // Count the number of activities assigned to the employee
                    int numberOfActivities = employee.getNumberOfActivities(); // Get the number of activities assigned to the employee
                    System.out.println("Number of activities assigned to employee " + employee.getName() + ": " + numberOfActivities);
                } else {
                    System.out.println("Employee with ID " + employeeId + " does not exist.");
                }
                break;
            case 5:
                assignEmployeeToActivity(sc); // Assign an employee to an activity
                break;
            case 6:
                displayAllEmployees(); // Display all employees 
                break;
            case 7:
                displayAllProjects(); // Display all projects
                break;
            case 8:
                System.out.print("Please enter employee ID: ");
                employeeId = sc.nextLine();
                displayAllActivitiesForEmployee(employeeId); // Display all activities for a specific employee
                //displayActivites(employeeId, employees); // Display all activities for a specific employee
                break;
            case 0:
                System.out.println("Exiting the program.");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    public void executeChoiceForAdmin(int choice, Scanner sc) throws ParseException {
        switch (choice) {
            case 1:
                
                break;
            case 2:
                
                break;
            case 3:
                
                break;
            case 4:
                
                break;
            case 5:
                // Assign an employee to an activity
                break;
            case 6:
                // Display all employees
                break;
            case 7:
                /*for (Project project : projects) {
                    System.out.println("Project ID: " + project.getProjectId() + ", Project Name: " + project.getProjectName());
                } // Display all projects*/
                break;
            case 8:
                
                break;
            case 0:
                
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }



    /*public void displayActivites(String employeeId, Map<String, Employee> employees) {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
        String startDateStr;
        String endDateStr;
        Map<String, Activity> activities = employees.get(employeeId).getActivities();
        
        System.out.println("List of activities for employee " + employees.get(employeeId).getName() + ":");
        
        // Header with fixed column widths
        System.out.printf("%-12s %-15s %-15s %-15s %-16s %-15s%n",
                "Activity ID", "Name", "Start Date", "End Date", "Budgeted Hours");
        System.out.printf("%-12s %-15s %-15s %-15s %-16s %-15s%n",
                "-----------", "----", "----------", "--------", "---------------");
        
        // Data rows with fixed column widths
        for (Map.Entry<String, Activity> entry : activities.entrySet()) {
            String activityId = entry.getKey();
            Activity activity = entry.getValue();
            startDateStr = formatter.format(activity.getStartDate());
            endDateStr = formatter.format(activity.getEndDate());
            
            System.out.printf("%-12s %-15s %-15s %-15s %-16.1f %-15s%n",
                    activityId,
                    activity.getActivityName(),
                    startDateStr,
                    endDateStr,
                    activity.getBudgetedHours(),
                    activity.getActivityStatus());
        }
    }*/


}
