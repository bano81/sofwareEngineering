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
            case 4 -> generateReportsMenu(sc);
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
            List<Activity> activities = systemStorage.getActivitiesForUser(systemStorage.getLoggedInEmployee().getEmployeeId()); // Fetch activities from SystemStorage
            for (Activity activity : activities) {
                System.out.println("- [" + activity.getActivityId() + "] "+ activity.getActivityName() + " Start: " + activity.getStartDate() + " End: " + 
                       activity.getEndDate() + " Budgeted hours: " + activity.getBudgetedHours()+ " Current hours spent: " + 
                	   activity.getCurrentSpentHours(systemStorage.getTimeRegistrations()));
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
                choice = -1; // Invalid choice to continue loop
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
        int choice;  // Start with option to enter data

        while (true) {  // Continue only if user wants to try again
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
                    blController.editTimeRegistration(timeRegistrationId, newHours, newDescription);
                    System.out.println("Time registration updated successfully!");
                    break;
                } else if (choice == 2) {
                    System.out.print("Enter Time Registration ID to Delete: ");
                    String timeRegistrationId = sc.nextLine();
                    blController.deleteTimeRegistration(timeRegistrationId);
                    System.out.println("Time registration deleted successfully!");
                    break;
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
        clearConsole();
        System.out.println("Fixed Activity Types:");
        System.out.println("1: Vacation");
        System.out.println("2: Course");
        System.out.println("3: Sickness");
        System.out.print("Select Fixed Activity Type: ");
        int type = sc.nextInt();
        sc.nextLine(); // Consume newline
        System.out.print("Enter Start Date (YYYY-MM-DD or YYYY-Www): ");
        String startDate = sc.nextLine();
        System.out.print("Enter End Date (YYYY-MM-DD or YYYY-Www): ");
        String endDate = sc.nextLine();
        // TODO: Implement logic to register fixed activity
        System.out.println("Fixed activity registered successfully!");
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
            Thread.sleep(1000); // 1 seconds delay
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
                        if (systemStorage.employeeExists(employeeId)) {
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
    			if (systemStorage.employeeExists(employeeID)) {
                    blController.assignEmployeeToActivity(activity.getActivityId(),employeeID);
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
    			if (systemStorage.employeeExists(employeeID)) {
                    blController.removeEmployeeFromActivity(activity.getActivityId(),employeeID);
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
        if (systemStorage.employeeExists(employeeId)) {
            try {
                blController.assignProjectManager(project.getProjectId(), employeeId);
                System.out.println("Project Manager assigned successfully!");
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
                return;
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
        clearConsole();
        System.out.println("-- Employee Availability --");
        System.out.println("Options:");
        System.out.println("1. View Availability by Week");
        System.out.println("2. View Specific Employee Schedule");
        System.out.println("0. Back to Main Menu");
        System.out.print("# ");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline
        handleEmployeeAvailability(choice, sc);
        System.out.println("Press Enter to continue...");
        sc.nextLine();
        // TODO: Implement employee availability logic
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
    	System.out.print("Enter week's number (YY-WW): ");
    	String weekNumber = sc.nextLine();
    	int WeekActivities = 0;
    	
    	System.out.printf("%n%-12s %-15s%n", "Employee ID", "Nr. Activities");
    	System.out.printf("%-12s %-15s%n",   "-----------", "--------------");
    	for(Employee employee : systemStorage.getEmployees()) {
    		WeekActivities = systemStorage.getWeekActivitiesNumber(employee.getEmployeeId(), weekNumber);
    		System.out.printf("%-12s %-15s%n", employee.getEmployeeId(), WeekActivities);
    	}
	}

	private void viewSpecificEmployeeSchedule(Scanner sc) {
		System.out.print("Enter employee's ID: ");
		String employeeID = sc.nextLine();
		System.out.println("\nSchedule for employee ("+ employeeID + "):");
		System.out.printf("%n%-12s %-15s %-15s %-15s%n", "Project", "Activity", "start", "end");
		System.out.printf("%-12s %-15s %-15s %-15s%n",     "-------", "--------", "-----", "---");
		for(Project project : systemStorage.getProjects()) {
			System.out.printf("%-12s%n", project.getProjectName());
			for(Activity activity : project.getActivities()) {
				if(activity.isEmployeeAssigned(employeeID)) {
					System.out.printf("%-12s %-15s %-15s %-15s%n",     "      ", activity.getActivityName(),
							activity.getStartDate(), activity.getEndDate());
				}				
			}
		}
	}

	

	private void generateReportsMenu(Scanner sc) {
        clearConsole();
        System.out.println("-- Reports --");
        displayAllProjects();
        System.out.println("Options:");
        System.out.println("1. Project Status Report (Budgeted Time / Registered Time)");
        System.out.println("2. Project Remaining Work Estimate Report");
        System.out.println("0. Back to Main Menu");
        System.out.print("# ");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline
        handleGenerateReports(choice, sc);
        // TODO: Implement report generation logic
    }

    private void handleGenerateReports(int choice, Scanner sc) {
        switch (choice) {
            case 1 -> {
                System.out.print("Enter Project ID: ");
                String projectId = sc.nextLine();
                try {
                generateProjectStatusReport(projectId);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            case 2 -> System.out.println("Generating Project Remaining Work Estimate Report..."); // TODO: Implement logic
            case 0 -> System.out.println("Returning to Main Menu.");
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    private void generateProjectStatusReport(String projectId) {
        Project project = systemStorage.getProject(projectId); // Fetch project from SystemStorage
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }
        double totalBudgetedHours = 0;
        double totalRegisteredHours = 0;

        System.out.println();
        System.out.println("Project Status Report for " + project.getProjectName() + ":");
        System.out.println("Project Manager: " + project.getProjectManagerId());
        System.out.println();
        System.out.println("Time Status per Activity:");
        System.out.printf("%n%-20s %-20s %-20s%n", "Activity", "Budgeted Hours", "Registered Hours");
        System.out.printf("%-20s %-20s %-20s%n", "--------", "--------------", "----------------");
        for (Activity activity : project.getActivities()) {
            System.out.printf("%-20s %-20.2f %-20.2f%n",
                    activity.getActivityName(),
                    activity.getBudgetedHours(),
                    activity.getCurrentSpentHours(systemStorage.getTimeRegistrations()));

            // Calculate totals:
            totalBudgetedHours += activity.getBudgetedHours();
            totalRegisteredHours += activity.getCurrentSpentHours(systemStorage.getTimeRegistrations());
        }
        System.out.println();
        System.out.println("Time Status Total:");
        System.out.println("Budgeted Time for " + project.getProjectName() + ": " + totalBudgetedHours + " Hours");
        System.out.println("Registered Time for " + project.getProjectName() + ": " + totalRegisteredHours + " Hours");
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