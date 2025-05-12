package dtu.ProjectManagementApp.businessLogic;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class UIConsole {
    private final ProjectManagementAppBL blController;
    private final SystemStorage systemStorage;

    public UIConsole(ProjectManagementAppBL blController, SystemStorage systemStorage) {
        this.blController = blController;
        this.systemStorage = systemStorage;
    }

    public void run() {
        Scanner sc = new Scanner(System.in);

        // Login process
        boolean isLoggedIn = false;
        System.out.println("Welcome to the Project Management System!");

        while (!isLoggedIn) {
            isLoggedIn = login(sc);
        }

        // After successful login, proceed to the main menu
        String username = systemStorage.getLoggedInEmployee().getName();
        mainMenu(username, sc);
        sc.close();
    }

    private boolean login(Scanner sc) {
        System.out.println("Existing users in the system:");
        for (Employee employee : systemStorage.getEmployees()) {
            System.out.println("- " + employee.getEmployeeId());
        }

        System.out.print("Please enter your employee ID: ");
        String employeeId = sc.nextLine();

        try {
            blController.login(employeeId);
            System.out.println("Login successful! Welcome " + systemStorage.getLoggedInEmployee().getName() + " " + systemStorage.getLoggedInEmployee().getSurname() + "!");
            return true;
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private void mainMenu(String username, Scanner sc) {
        int choice;
        do {
            clearConsole();
            System.out.println("-- Main Menu (User: " + username + ") --");
            System.out.println("1. My View: Time & Activities");
            System.out.println("2. Project Management");
            System.out.println("3. Employee Availability Overview");
            System.out.println("4. Generate Reports");
            System.out.println("5. Create New Employee");
            System.out.println("0. Exit");
            System.out.print("# ");
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline
            handleMainMenuChoice(choice, username, sc);
        } while (choice != 0);
    }

    private void handleMainMenuChoice(int choice, String username, Scanner sc) {
        switch (choice) {
            case 1 -> timeAndActivitiesMenu(username, sc);
            case 2 -> projectManagementMenu(sc);
            case 3 -> employeeAvailabilityMenu(sc);
            case 4 -> generateProjectStatusReport(sc);
            case 5 -> createNewEmployee(sc);
            case 0 -> System.out.println("Exiting the program.");
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    private void createNewEmployee(Scanner sc) {
		System.out.print("Enter employee's full name (first name and surname): ");
		String fullName = sc.nextLine();
		String[] nameParts = fullName.split(" ");
		String firstName = nameParts[0];
		String surName = nameParts[nameParts.length - 1];
		System.out.print("Enter employee's ID: ");
		String ID = sc.nextLine();
		blController.createEmployee(firstName, surName, ID);		
	}

	private void timeAndActivitiesMenu(String username, Scanner sc) {
        int choice;
        do {
            clearConsole();
            System.out.println("-- Time & Activities (User: " + username + ") --");
            System.out.println("My Assigned Activities:");
            List<Activity> activities = systemStorage.getActivitiesForUser(systemStorage.getLoggedInEmployee().getEmployeeId());
            for (Activity activity : activities) {
                // Find the project that contains this activity
                String projectName = "Unknown";
                for (Project project : systemStorage.getProjects()) {
                    if (project.getActivities().contains(activity) ||
                            project.getActivityById(activity.getActivityId()) != null) {
                        projectName = project.getProjectName();
                        break;
                    }
                }

                System.out.println("- [" + activity.getActivityId() + "]" +
                        ", Activity: " + activity.getActivityName() +", Project: " + projectName +
                        ", Start: " + activity.getStartDate() +
                        ", End: " + activity.getEndDate() +
                        ", Budgeted hours: " + activity.getBudgetedHours() +
                        ", Current hours spent: " + activity.getCurrentSpentHours(systemStorage.getTimeRegistrations()));
            }
            System.out.println("Options:");
            System.out.println("1. Register Time");
            System.out.println("2. View/Edit My Registered Time");
            System.out.println("3. Register Future Fixed Activity Time (Vacation, etc.)");
            System.out.println("0. Back to Main Menu");
            System.out.print("# ");
            try {
                choice = Integer.parseInt(sc.nextLine());
                handleTimeAndActivitiesChoice(choice, sc);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid menu option (0-3).");
                choice = -1; // Invalid choice to continue the loop
            }
        } while (choice != 0);
    }

    private void handleTimeAndActivitiesChoice(int choice, Scanner sc) {
        switch (choice) {
            case 1 -> registerTime(sc);
            case 2 -> editRegisteredTime(sc);
            case 3 -> registerFixedActivity(sc);
            case 0 -> System.out.println("Returning to Main Menu.");
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    private void registerTime(Scanner sc) {
        int choice;  // Start with an option to enter data

        while (true) {  // Continue only if the user wants to try again
            clearConsole();
            System.out.println("Available Activities:");
            List<Activity> activities = systemStorage.getActivitiesForUser(systemStorage.getLoggedInEmployee().getEmployeeId()); // Fetch activities from SystemStorage
            int i = 0;
            for (Activity activity : activities) {
                System.out.println((i + 1) + ". [" + activity.getActivityId() + "] " + activity.getActivityName());
                i++;
            }
            
            System.out.print("Select Activity (Number in list): ");
            String activityChoice = sc.nextLine();

            System.out.print("Enter Date (YYYY-MM-DD, default Today): ");
            String date = sc.nextLine();
            if (date.isEmpty()) {
                date = java.time.LocalDate.now().toString();
            }

            System.out.print("Enter Hours (0.5 increments): ");
            String hours = sc.nextLine();

            System.out.print("Enter description of work done: ");
            String description = sc.nextLine();

            System.out.println("Options:");
            System.out.println("1. Confirm Registration");
            System.out.println("2. Try Again");
            System.out.println("0. Go back to My View");
            System.out.print("# ");

            try {
                choice = Integer.parseInt(sc.nextLine());

                if (choice == 1) {
                    try {
                        int activityIndex = Integer.parseInt(activityChoice) - 1;
                        String activityId = activities.get(activityIndex).getActivityId();
                        blController.registerTime(activityId, date, hours, description);
                        System.out.println("TimeRegistration registered successfully");
                        break;
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                        System.out.println("Press Enter to try again...");
                        sc.nextLine();
                    }
                } else if (choice == 0) {
                    break;
                } else if (choice != 2) {
                    System.out.println("Invalid option. Press Enter to try again...");
                    sc.nextLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number (0-2). Press Enter to try again...");
                sc.nextLine();
            }
        }
    }

    private void editRegisteredTime(Scanner sc) {
        while (true) {
            clearConsole();
            System.out.println("Your past 10 time registrations:");
            List<TimeRegistration> timeRegistrations = systemStorage.getTimeRegistrationsForUser(systemStorage.getLoggedInEmployee().getEmployeeId());
            Collections.reverse(timeRegistrations);
            int counter = 0;
            for (TimeRegistration tr : timeRegistrations) {
                if (counter <= 10) {
                    System.out.println("- [" + tr.getTimeRegistrationId() + "] " + tr.getActivity().getActivityName() + " Date: " + tr.getDate() + " Hours: " + tr.getHoursSpent()+ " Description: " + tr.getDescription());
                    counter++;
                } else {
                    break;
                }
            }
            System.out.println("Options:");
            System.out.println("1. Edit Registered Time");
            System.out.println("2. Delete Registered Time");
            System.out.println("3. View All Registered Time");
            System.out.println("0. Go back to My View");
            System.out.print("# ");

            try {
                int choice = Integer.parseInt(sc.nextLine());

                if (choice == 1) {
                    System.out.print("Enter Time Registration ID to Edit: ");
                    String timeRegistrationId = sc.nextLine();
                    System.out.print("Enter New Hours: ");
                    String newHours = sc.nextLine();
                    System.out.print("Enter New Description: ");
                    String newDescription = sc.nextLine();
                    try {
                        blController.editTimeRegistration(timeRegistrationId, newHours, newDescription);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                        System.out.println("Press Enter to try again...");
                        sc.nextLine();
                        continue;
                    }
                    blController.editTimeRegistration(timeRegistrationId, newHours, newDescription);
                    System.out.println("Time registration updated successfully!");
                    break;
                } else if (choice == 2) {
                    System.out.print("Enter Time Registration ID to Delete: ");
                    String timeRegistrationId = sc.nextLine();
                    try {
                        blController.deleteTimeRegistration(timeRegistrationId);
                        System.out.println("Time registration deleted successfully!");
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                        System.out.println("Press Enter to try again...");
                        sc.nextLine();
                    }

                } else if (choice == 3) {
                    System.out.println("All Registered Time:");
                    for (TimeRegistration tr : systemStorage.getTimeRegistrations()) {
                        System.out.println("- [" + tr.getTimeRegistrationId() + "] " + tr.getActivity().getActivityName() + " Date: " + tr.getDate() + " Hours: " + tr.getHoursSpent()+ " Description: " + tr.getDescription());
                    }
                    System.out.println("Press Enter to go back...");
                    sc.nextLine();
                } else if (choice == 0) {
                    break;
                } else {
                    System.out.println("Invalid option. Press Enter to try again...");
                    sc.nextLine();
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number (0-2). Press Enter to try again...");
                sc.nextLine();
            }

        }
    }

    private void registerFixedActivity(Scanner sc) {
        int choice;
        do {
            clearConsole();
            System.out.println("Fixed Activity Types:");
            System.out.println("1: Vacation");
            System.out.println("2: Course");
            System.out.println("3: Sickness");
            System.out.println("0: Back to My View");
            System.out.print("Select Fixed Activity Type: ");
            try{
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number (0-3).");
                choice = -1; // Invalid choice to continue the loop
            }
            System.out.print("Enter Start Date (YYYY-MM-DD or YYYY-WW): ");
            String startDate = sc.nextLine();
            System.out.print("Enter End Date (YYYY-MM-DD or YYYY-WW): ");
            String endDate = sc.nextLine();
            // TODO: Implement logic to register fixed activity
            //System.out.println("Fixed activity registered successfully!");
            System.out.print("This feature is not implemented yet. Press Enter to go back...");
            sc.nextLine();
            break;
        } while (true);

    }

    private void projectManagementMenu(Scanner sc) {
        int choice;
        do {
            clearConsole();
            System.out.println("-- Project Management --");
            displayAllProjects();
            System.out.println("Options:");
            System.out.println("1. Create New Project");
            System.out.println("2. Manage Project");
            System.out.println("0. Back to Main Menu");
            System.out.print("# ");
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline
            handleProjectManagementChoice(choice, sc);
        } while (choice != 0);
    }

    private void handleProjectManagementChoice(int choice, Scanner sc) {
        switch (choice) {
            case 1 -> createNewProject(sc);
            case 2 -> {
                System.out.print("Enter Project ID: ");
                String projectId = sc.nextLine();
                manageProject(projectId, sc);
            }
            case 0 -> System.out.println("Returning to Main Menu.");
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    private void createNewProject(Scanner sc) {
        clearConsole();
        System.out.print("Enter project name: ");
        String projectName = sc.nextLine();

            blController.createProject(projectName);
            System.out.println("Project created successfully!");
            delay();
    }

    private void delay() {
        try {
            Thread.sleep(1000); // 1-second delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
        }
    }

    private void manageProject(String projectId, Scanner sc) {
        Project project = systemStorage.getProject(projectId); // Fetch project from SystemStorage
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
    
        int choice;
        do {
            clearConsole();
            System.out.println("-- Manage Project [" + project.getProjectId() + "] " + project.getProjectName() + " --");
            String projectManager = project.getProjectManagerId() != null ? project.getProjectManagerId() : "<None>";
            System.out.println("PM: " + projectManager);
            System.out.println("Activities:");
            for (Activity activity : project.getActivities()) {
                String staff = activity.getAssignedEmployees().isEmpty() 
                    ? "<None>" 
                    : String.join(", ", activity.getAssignedEmployees());
                System.out.println(activity.getActivityName() + " Start: " + activity.getStartDate() + " End: " + activity.getEndDate() + " Budgeted hours: " + activity.getBudgetedHours() +" Staff: " + staff);
            }
            System.out.println("---");
            System.out.println("Manage Project Options:");
            System.out.println("  1. Add Activity");
            System.out.println("  2. Edit Activity Details (Budget, Dates)");
            System.out.println("  3. Staff Activity (Add/Remove Employee)");
            System.out.println("  4. Assign/Change Project Manager");
            System.out.println("  5. Rename Project");
            System.out.println("  0. Back to Project Management Menu");
            System.out.print("Choose option: ");
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline
            handleManageProjectChoice(choice, project, sc);
        } while (choice != 0);
    }

    private void handleManageProjectChoice(int choice, Project project, Scanner sc) {
        switch (choice) {
            case 1 -> addActivityToProject(project, sc);
            case 2 -> editActivityDetails(project, sc);
            case 3 -> staffActivity(project, sc);
            case 4 -> assignProjectManager(project, sc);
            case 5 -> renameProject(project, sc);
            case 0 -> System.out.println("Returning to Project Management Menu.");
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    private void addActivityToProject(Project project, Scanner sc) {
        System.out.print("Enter Activity Name: ");
        String activityName = sc.nextLine();
        System.out.print("Enter Activity Start Week (YY-WW): ");
        String startWeek = sc.nextLine();
        System.out.print("Enter Activity End Week (YY-WW): ");
        String endWeek = sc.nextLine();
        System.out.print("Enter Budgeted Hours: ");
        double budgetedHours = sc.nextDouble();
        sc.nextLine(); // Consume newline
        blController.createNewActivity(project.getProjectId(),activityName, startWeek, endWeek, budgetedHours);
        System.out.println("Activity added successfully!");
        delay();
    }

    private void editActivityDetails(Project project, Scanner sc) {
        System.out.print("Enter Activity Name to Edit: ");
        String activityName = sc.nextLine();
        Activity activity = project.getActivity(activityName);
        if (activity == null) {
            System.out.println("Activity not found.");
            return;
        }
        
        int choice;
        do {
            clearConsole();
            System.out.println("-- Edit Activity [" + activity.getActivityName() + "] --");
            System.out.println("1. Edit Name");
            System.out.println("2. Edit Start Week");
            System.out.println("3. Edit End Week");
            System.out.println("4. Edit Budgeted Hours");
            System.out.println("5. Add Employee to Activity");
            System.out.println("0. Back to Manage Project Menu");
            System.out.print("Choose option: ");
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter New Name (leave blank to keep current): ");
                    String newName = sc.nextLine();
                    if (!newName.isBlank()) {
                        activity.setActivityName(newName);
                        System.out.println("Activity name updated successfully!");
                    } else {
                        System.out.println("No changes made to the activity name.");
                    }
                }
                case 2 -> {
                    System.out.print("Enter New Start Week (YY-WW, leave blank to keep current): ");
                    String newStartWeek = sc.nextLine();
                    if (!newStartWeek.isBlank()) {
                        activity.setStartDate(newStartWeek);
                        System.out.println("Start week updated successfully!");
                    } else {
                        System.out.println("No changes made to the start week.");
                    }
                }
                case 3 -> {
                    System.out.print("Enter New End Week (YY-WW, leave blank to keep current): ");
                    String newEndWeek = sc.nextLine();
                    if (!newEndWeek.isBlank()) {
                        activity.setEndDate(newEndWeek);
                        System.out.println("End week updated successfully!");
                    } else {
                        System.out.println("No changes made to the end week.");
                    }
                }
                case 4 -> {
                    System.out.print("Enter New Budgeted Hours (leave blank to keep current): ");
                    String newBudgetedHours = sc.nextLine();
                    if (!newBudgetedHours.isBlank()) {
                        activity.setBudgetedHours(Double.parseDouble(newBudgetedHours));
                        System.out.println("Budgeted hours updated successfully!");
                    } else {
                        System.out.println("No changes made to the budgeted hours.");
                    }
                }
                case 5 -> {
                    System.out.print("Add new Employee to Activity by ID (leave blank to skip): ");
                    String employeeId = sc.nextLine();
                    if (!employeeId.isBlank()) {
                        if (blController.employeeExists(employeeId)) {
                            activity.assignEmployee(employeeId);
                            System.out.println("Employee added to activity successfully!");
                        } else {
                            System.out.println("Employee not found.");
                        }
                    } else {
                        System.out.println("No employee added to the activity.");
                    }
                }
                case 0 -> System.out.println("Returning to Manage Project Menu.");
                default -> System.out.println("Invalid choice. Please try again.");
            }

            delay(); // Optional delay for better user experience
        } while (choice != 0);
    }

    private void staffActivity(Project project, Scanner sc) {
    	System.out.println("1. Add Employee");
    	System.out.println("2. Remove Employee");
    	System.out.println("0. Back to Project Options");
    	System.out.print("# ");
    	int choice = sc.nextInt();
    	sc.nextLine();
    	String employeeID;
    	String activityName;
    	Activity activity;// = project.getActivity(activityName);
    	switch(choice) {
    		case 1 -> {
    			System.out.print("Enter activity's name: ");
    			activityName = sc.nextLine();
    			activity = project.getActivity(activityName);
    			if (activity == null) {
    				 System.out.println("Activity not found.");
    				 return;
    			}
    			System.out.print("Enter employee's ID: ");
    			employeeID = sc.nextLine();
    			if (blController.employeeExists(employeeID)) {
                    //blController.assignEmployeeToActivity(activity.getActivityId(),employeeID);
                    activity.assignEmployee(employeeID);
    				System.out.println("Employee added to activity successfully!");
    			} else {
    				System.out.println("Employee not found.");
    			}
    		}
    		case 2 -> {
    			System.out.print("Enter activity's name: ");
    			activityName = sc.nextLine();
    			activity = project.getActivity(activityName);
    			if (activity == null) {
    				 System.out.println("Activity not found.");
    				 return;
    			}
    			System.out.print("Enter employee's ID: ");
    			employeeID = sc.nextLine();
    			if (blController.employeeExists(employeeID)) {
                    //blController.removeEmployeeFromActivity(activity.getActivityId(),employeeID);
                    activity.getAssignedEmployees().remove(employeeID);
    				System.out.println("Employee is removed from activity!");
    			}else {
    				System.out.println("Employee not found.");
    			}
    		}
    		case 0 -> System.out.println("Returning to Manage Project Options.");
            default -> System.out.println("Invalid choice. Please try again.");
    	}
        
    }

    private void assignProjectManager(Project project, Scanner sc) {
        System.out.print("Enter Employee ID to Assign as Project Manager: ");
        String employeeId = sc.nextLine();
        if (blController.employeeExists(employeeId)) {
            try {
                blController.assignProjectManager(project.getProjectId(), employeeId);
                System.out.println("Project Manager assigned successfully!");
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Employee not found.");
        }
    }

    private void renameProject(Project project, Scanner sc) {
        System.out.print("Enter New Project Name: ");
        String newName = sc.nextLine();
        project.setProjectName(newName);
        System.out.println("Project renamed successfully!");
    }

    private void employeeAvailabilityMenu(Scanner sc) {
        int choice;
        while (true) {
            clearConsole();
            System.out.println("-- Employee Availability Overview --");
            System.out.println("1. View Availability by Week");
            System.out.println("2. View Specific Employee Schedule");
            System.out.println("0. Back to Main Menu");
            System.out.print("# ");
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number (0-2).");
                continue; // Invalid choice, continue the loop
            }
            if (choice == 0) {
                break; // Exit the loop if the user chooses to go back
            }
            handleEmployeeAvailability(choice, sc);
            System.out.println("Press Enter to continue...");
            sc.nextLine(); // Wait for user input before continuing
        }
    }

    private void handleEmployeeAvailability(int choice, Scanner sc) {
    	switch (choice) {
        case 1 -> viewAvailableEmployeeByWeek(sc);
        case 2 -> viewSpecificEmployeeSchedule(sc);
        case 0 -> System.out.println("Returning to Project Management Menu.");
        default -> System.out.println("Invalid choice. Please try again.");
       }
		
	}

    private void viewAvailableEmployeeByWeek(Scanner sc) {
        String weekNumber = null;
        boolean isValidFormat = false;
        boolean goBack = false;

        do {
            clearConsole();
            System.out.print("Enter week's number (YY-WW) or '0' to go back: ");
            weekNumber = sc.nextLine();

            if (weekNumber.equals("0")) {
                goBack = true;
                break;
            }

            if (weekNumber.matches("\\d{2}-\\d{2}")) {
                try {
                    int year = Integer.parseInt(weekNumber.substring(0, 2));
                    int week = Integer.parseInt(weekNumber.substring(3));

                    if (week >= 1 && week <= 52) {
                        isValidFormat = true;
                    } else {
                        System.out.println("Week number must be between 1 and 52. Press Enter to try again.");
                        sc.nextLine();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format. Press Enter to try again.");
                    sc.nextLine();
                }
            } else {
                System.out.println("Invalid format. Please use YY-WW format (e.g., 25-01). Press Enter to try again or '0' to go back.");
                String response = sc.nextLine();
                if (response.equals("0")) {
                    goBack = true;
                    break;
                }
            }
        } while (!isValidFormat && !goBack);

        // Only proceed with printing the table if we didn't choose to go back
        if (!goBack && isValidFormat) {
            System.out.printf("%n%-12s %-15s%n", "Employee ID", "Nr. Activities");
            System.out.printf("%-12s %-15s%n", "-----------", "--------------");
            for (Employee employee : systemStorage.getEmployees()) {
                int weekActivities = systemStorage.getWeekActivitiesNumber(employee.getEmployeeId(), weekNumber);
                String activitiesDisplay = (weekActivities == -1) ? "Invalid week" : String.valueOf(weekActivities);
                System.out.printf("%-12s %-15s%n", employee.getEmployeeId(), activitiesDisplay);
            }

            System.out.println("\nPress Enter to continue...");
            sc.nextLine();
        }
    }

    private void viewSpecificEmployeeSchedule(Scanner sc) {
        System.out.print("Enter employee's ID: ");
        String employeeID = sc.nextLine();
        if (!blController.employeeExists(employeeID)) {
            System.out.println("Employee not found.");
            return;
        }

        System.out.println("\nSchedule for employee ("+ employeeID + "):");
        System.out.printf("%n%-12s %-15s %-15s %-15s%n", "Project", "Activity", "start", "end");
        System.out.printf("%-12s %-15s %-15s %-15s%n", "-------", "--------", "-----", "---");

        for(Project project : systemStorage.getProjects()) {
            boolean projectPrinted = false;

            // First check if any activities in the project are assigned to this employee
            boolean hasAssignedActivities = project.getActivities().stream()
                    .anyMatch(activity -> activity.isEmployeeAssigned(employeeID));

            if (hasAssignedActivities) {
                for(Activity activity : project.getActivities()) {
                    if(activity.isEmployeeAssigned(employeeID)) {
                        // Print project name only for the first matching activity
                        if (!projectPrinted) {
                            System.out.printf("%-12s%n", project.getProjectName());
                            projectPrinted = true;
                        }

                        System.out.printf("%-12s %-15s %-15s %-15s%n",
                                "      ",
                                activity.getActivityName(),
                                activity.getStartDate(),
                                activity.getEndDate());
                    }
                }
            }
        }

        System.out.println("\nPress Enter to continue...");
        sc.nextLine();
    }

	private void generateProjectStatusReport(Scanner sc) {
        clearConsole();
        System.out.println("-- Generate Report --");
        displayAllProjects();
        System.out.print("Enter Project ID: ");
        String projectId = sc.nextLine();
        try {
            Project project = systemStorage.getProject(projectId); // Fetch project from SystemStorage
            if (project == null) {
                System.out.println("Project not found.");
                return;
            }
            double totalBudgetedHours = 0;
            double totalRegisteredHours = 0;
            System.out.println();
            System.out.println("-- Status Report for " + project.getProjectName() + " --");

            // GENERAL DETAILS
            System.out.println("Project ID: " + project.getProjectId());
            System.out.println("Project Manager: " + project.getProjectManagerId());
            System.out.println("Project Deadline: " + project.getDeadline());
            int weeksLeft = project.getWeeksUntilDeadline(project.getCurrentDate());
            if (weeksLeft < 0) { System.out.println("Deadline exceeded by: " + Math.abs(weeksLeft) + " weeks"); }
            else { System.out.println("Deadline in: " + weeksLeft + " weeks"); }
            System.out.println();

            // TIME STATUS
            System.out.println("Time Status per Activity:");
            System.out.printf("%n%-20s %-20s %-20s%n", "Activity", "Budgeted Hours", "Registered Hours");
            System.out.printf("%-20s %-20s %-20s%n", "--------", "--------------", "----------------");
            for (Activity activity : project.getActivities()) {
                System.out.printf("%-20s %14.2f %16.2f%n",
                        activity.getActivityName(),
                        activity.getBudgetedHours(),
                        activity.getCurrentSpentHours(systemStorage.getTimeRegistrationsForActivity(activity)));
                // Calculate totals:
                totalBudgetedHours += activity.getBudgetedHours();
                totalRegisteredHours += activity.getCurrentSpentHours(systemStorage.getTimeRegistrationsForActivity(activity));
            }
            System.out.println();
            System.out.println("Time Status Total:");
            System.out.println(totalBudgetedHours + " Hours Budgeted Time");
            System.out.println(totalRegisteredHours + " Hours Registered Time");
            System.out.println();

            // VISUAL REPRESENTATION OF TIMELINE FOR ACTIVITIES
            System.out.println("Activity Timeline:");
            // Find boundaries of the timeline
            int earliestWeek = Integer.MAX_VALUE;
            int latestWeek = Integer.MIN_VALUE;
            for (Activity activity : project.getActivities()) {
                String[] startParts = activity.getStartDate().split("-");
                int startYear = Integer.parseInt(startParts[0]);
                int startWeek = Integer.parseInt(startParts[1]);
                int startAbsWeek = startYear * 52 + startWeek;
                String[] endParts = activity.getEndDate().split("-");
                int endYear = Integer.parseInt(endParts[0]);
                int endWeek = Integer.parseInt(endParts[1]);
                int endAbsWeek = endYear * 52 + endWeek;
                earliestWeek = Math.min(earliestWeek, startAbsWeek);
                latestWeek = Math.max(latestWeek, endAbsWeek);
            }
            // Calculate the number of 13-week chunks needed
            int totalWeeks = latestWeek - earliestWeek + 1;
            int weeksPerChunk = 13;
            int chunksNeeded = (int) Math.ceil((double) totalWeeks / weeksPerChunk);
            for (int chunk = 0; chunk < chunksNeeded; chunk++) {
                int chunkStartWeek = earliestWeek + (chunk * weeksPerChunk);
                int chunkEndWeek = Math.min(chunkStartWeek + weeksPerChunk - 1, latestWeek);
                int startYear = chunkStartWeek / 52 + 2000;
                int endYear = chunkEndWeek / 52 + 2000;
                // Create an appropriate header (might span multiple years)
                if (startYear == endYear) {
                    System.out.println("\n-- Weeks " + ((chunk * weeksPerChunk) + 1) + " to " +
                            Math.min((chunk + 1) * weeksPerChunk, totalWeeks) + " of Year " + startYear + " --");
                } else {
                    System.out.println("\n-- Weeks " + ((chunk * weeksPerChunk) + 1) + " to " +
                            Math.min((chunk + 1) * weeksPerChunk, totalWeeks) + " spanning Years " +
                            startYear + "-" + endYear + " --");
                }
                // Display week header for this chunk
                System.out.print("Activity          |");
                for (int i = 0; i <= chunkEndWeek - chunkStartWeek; i++) {
                    int weekNumber = (chunkStartWeek + i) % 52;
                    if (weekNumber == 0) weekNumber = 52;
                    System.out.printf("W%-2d|", weekNumber);
                }
                System.out.println();
                // Display all activities for this chunk
                for (Activity activity : project.getActivities()) {
                    String displayName = activity.getActivityName().length() > 16
                            ? activity.getActivityName().substring(0, 13) + "..."
                            : activity.getActivityName();
                    System.out.printf("%-18s|", displayName);
                    // Draw a timeline for this chunk
                    String[] startParts = activity.getStartDate().split("-");
                    int startAbsWeek = Integer.parseInt(startParts[0]) * 52 + Integer.parseInt(startParts[1]);
                    String[] endParts = activity.getEndDate().split("-");
                    int endAbsWeek = Integer.parseInt(endParts[0]) * 52 + Integer.parseInt(endParts[1]);
                    for (int i = 0; i <= chunkEndWeek - chunkStartWeek; i++) {
                        int currentWeek = chunkStartWeek + i;
                        if (currentWeek >= startAbsWeek && currentWeek <= endAbsWeek) {
                            System.out.print("███|");
                        } else {
                            System.out.print("   |");
                        }
                    }
                    System.out.println();
                }
            }
            System.out.println();

            // LATEST TIME REGISTRATIONS
            System.out.println("Latest Time Registration Descriptions (up to 5 per Activity):");
            System.out.println();
            System.out.printf("    %-6s %6s   %-45s%n", "User", "Hours", "Description");
            System.out.printf("    %-6s %6s   %-45s%n", "----", "-----", "-----------");
            for (Activity activity : project.getActivities()) {
                int employeeCount = activity.getAssignedEmployees().size();
                String employeeText;
                if (employeeCount == 0) { employeeText = "<No Assigned Employees>"; }
                else if (employeeCount == 1) { employeeText = employeeCount + " Assigned Employee"; }
                else { employeeText = employeeCount + " Assigned Employees"; }
                System.out.print(activity.getActivityName() + " - " + employeeText);
                List<TimeRegistration> registrations = systemStorage.getTimeRegistrationsForActivity(activity);
                if (registrations.isEmpty()) {
                    System.out.println(" - <Not Started>:");
                    System.out.println("   <none>");
                } else {
                    System.out.println(":");
                    registrations.sort((r1, r2) -> r2.getDate().compareTo(r1.getDate())); // Sort by date (newest first)
                    for (int i = 0; i < Math.min(5, registrations.size()); i++) {
                        TimeRegistration reg = registrations.get(i);
                        System.out.printf("    %-6s %6.2f   %-45s%n",
                                reg.getEmployee().getEmployeeId(),
                                reg.getHoursSpent(),
                                reg.getDescription());
                    }
                }
            }
            System.out.println();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.print("Press Enter to Return to the Main Menu...");
        sc.nextLine();
        System.out.println();
    }

    private void displayAllProjects() {
        System.out.println("Projects:");
        List<Project> projects = systemStorage.getProjects(); // Fetch projects from SystemStorage
        for (Project project : projects) {
            String projectManager = project.getProjectManagerId() != null ? project.getProjectManagerId() : "<None>";
            System.out.println("- [" + project.getProjectId() + "] " + project.getProjectName() + " (PM: " + projectManager + ")");
        }
    }

    private void clearConsole() {
        // ANSI escape code to clear the console
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}