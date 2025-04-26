package dtu.example.ui;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;

public class CLIEngine {
	public boolean login (Map<String, Employee> employees) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter your username: ");
        String username = sc.nextLine();
        System.out.print("Please enter your password: ");
        String password = sc.nextLine();
        boolean isLoggedIn = false; // Flag to check if the user is logged in
        for (Map.Entry<String, Employee> entry : employees.entrySet()) {
            Employee employee = entry.getValue();
            if (employee.getUsername().equals(username) && employee.getPassword().equals(password)) {
                System.out.println("Login successful! Welcome " + employee.getName() + " " + employee.getSurname() + "!");
                isLoggedIn = true;
                break;
            }
        }
        if (!isLoggedIn) {
            System.out.println("Invalid username or password. Please try again.");
        }
        return isLoggedIn;        
    }
	
	public boolean checkIfEmployeeExists(String employeeId, Map<String, Employee> employees) {
        return employees.containsKey(employeeId);
    }

	public boolean creatNewEmployees(Map<String, Employee> employees, String name, String surname, String emplyeeId) {
		boolean employeeExists = checkIfEmployeeExists(emplyeeId, employees); 
		if(!employeeExists) {
			employees.put(emplyeeId, new Employee(name, surname));
		}
		else {
			System.out.println("Error: \"" +  emplyeeId + "\" is used!");
		}
		return employeeExists;
    }
	
	public void addNewActivityToProject( Map<String, Employee> employees, String employeeId, String activityId, 
			String activityName, Date startDate, Date endDate, double activityBudgtedhour, String activityStatus) {
        employees.get(employeeId).setActivity(activityId, new Activity(activityName, startDate, endDate, activityBudgtedhour));
        employees.get(employeeId).getActivity(activityId).setActivityStatus(activityStatus);
        employees.get(employeeId).sortActivitiesByDate();
    } // This method only adds the activity to the employee's list of activities. It does not assign the activity to a project.
	
	public int getNumberOfNotCompletedActivities(Map<String, Employee> employees, String employeeId) {
		employees.get(employeeId).countNumberOfActivities();
		return employees.get(employeeId).getNumberOfActivities();
	}

    public void displayActivityStatus(){
    	System.out.println("Please choose a status:");
        System.out.println("\t1. Not started");
        System.out.println("\t2. In progress");
        System.out.println("\t3. Completed");
        System.out.println("\t4. On hold");
        System.out.println("\t5. Cancelled");
    }
    
    public String returnStatus(int choice) {
    	String status;
    	switch(choice) {
    	case 1:
    		status = "Not started";
    		break;
    	case 2: 
    		status = "In progress";
    		break;
    	case 3:
    		status = "Completed";
    		break;
    	case 4:
    		status = "On hold";
    		break;
    	default:
    		status = "Cancelled";
    		break;
    	}
    	return status;
    }
    
    public void displayActivites(String employeeId, Map<String, Employee> employees) {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
        String startDateStr;
        String endDateStr;
        Map<String, Activity> activities = employees.get(employeeId).getActivities();
        
        System.out.println("List of activities for employee " + employees.get(employeeId).getName() + ":");
        
        // Header with fixed column widths
        System.out.printf("%-12s %-15s %-15s %-15s %-16s %-15s%n",
                "Activity ID", "Name", "Start Date", "End Date", "Budgeted Hours", "Status");
        System.out.printf("%-12s %-15s %-15s %-15s %-16s %-15s%n",
                "-----------", "----", "----------", "--------", "---------------", "------");
        
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
    }

    public int displayChoices() {
        System.out.println("Please choose an option:");
        System.out.println("\t1. Create a new employee");
        System.out.println("\t2. Create a new project");
        System.out.println("\t3. Create a new activity");
        System.out.println("\t4. Display the number of activities"); //4. Assign an employee to a project
        System.out.println("\t5. Assign an employee to an activity");
        System.out.println("\t6. Display all employees");
        System.out.println("\t7. Display all projects");
        System.out.println("\t8. Display all activities");
        System.out.println("\t9. Exit");

        System.out.print("# ");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        return choice;
    }
    
    public void getlistOfEmployees(Map<String, Employee> employees) {
        System.out.println("Employee ID \t Name ");
        System.out.println("----------- \t ----------- ");
        for (Map.Entry<String, Employee> entry : employees.entrySet()) {
            String employeeId = entry.getKey();
            Employee employee = entry.getValue();
            System.out.println(employeeId + " \t         " + employee.getName() + " " + employee.getSurname());
        }
    }

    public void executeChoice(int choice, Map<String, Employee> employees) throws ParseException {
        switch (choice) {
            case 1:
            	Scanner sc = new Scanner(System.in);
                System.out.print("Please enter first name: ");
                String name = sc.nextLine();
                System.out.print("Please enter surname: ");
                String surname = sc.nextLine();
                System.out.print("Please enter employee ID: ");
                String emplyeeId = sc.nextLine();
                creatNewEmployees(employees, name, surname, emplyeeId); // Create a new employee
                break;
            case 2:
                // Create a new project
                break;
            case 3:
            	sc = new Scanner(System.in);
                System.out.print("Please enter employee ID: ");
                String employeeId = sc.nextLine();
                System.out.print("Please enter activity ID: ");
                String activityId = sc.nextLine();
                System.out.print("Please enter activity name: ");
                String activityName = sc.nextLine();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                System.out.print("Please enter activity start date (yyyy-MM-dd): ");
                String startDateStr = sc.nextLine();
                Date startDate = sdf.parse(startDateStr);
                System.out.print("Please enter activity end date (yyyy-MM-dd): ");
                String endDateStr = sc.nextLine();
                Date endDate = sdf.parse(endDateStr);
                System.out.print("Please enter activity budgted hours: ");
                double activityBudgtedhour = sc.nextDouble();
                displayActivityStatus();
                System.out.print("# ");
                int statusChoice = sc.nextInt();
                String activityStatus = returnStatus(statusChoice);
                addNewActivityToProject(employees, employeeId, activityId, activityName, startDate, endDate,
                		activityBudgtedhour, activityStatus); // Create a new activity
                break;
            case 4:
                // Assign an employee to a project
                sc = new Scanner(System.in);
                System.out.print("Please enter employee ID: ");
                employeeId = sc.nextLine();
                //employees.get(employeeId).countNumberOfActivities();
                System.out.print("Number of activity assigned to "+ employees.get(employeeId).getName() + ": ");
                //System.out.println(employees.get(employeeId).getNumberOfActivities());
                System.out.println(getNumberOfNotCompletedActivities(employees,employeeId ));
                break;
            case 5:
                // Assign an employee to an activity
                break;
            case 6:
                // Display all employees
                break;
            case 7:
                // Display all projects
                break;
            case 8:
                System.out.print("Please enter employee ID: ");
                Scanner sc1 = new Scanner(System.in);
                employeeId = sc1.nextLine();
                displayActivites(employeeId, employees); // Display all activities for a specific employee
                break;
            case 9:
                System.out.println("Exiting the program.");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

}

