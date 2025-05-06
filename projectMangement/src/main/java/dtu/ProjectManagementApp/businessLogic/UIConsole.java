package dtu.ProjectManagementApp.businessLogic;

import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

public class UIConsole {
    private final BLController blController;
    private final SystemStorage systemStorage;

    public UIConsole(BLController blController, SystemStorage systemStorage) {
        this.blController = blController;
        this.systemStorage = systemStorage;
    }

    public void run() {
        Scanner sc = new Scanner(System.in);

        // Login process
        boolean isLoggedIn = false;
        while (!isLoggedIn) {
            System.out.println("Welcome to the Project Management System!");
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
            case 0 -> System.out.println("Exiting the program.");
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    private void timeAndActivitiesMenu(String username, Scanner sc) {
        int choice;
        do {
            clearConsole();
            System.out.println("-- Time & Activities (User: " + username + ") --");
            System.out.println("My Assigned Activities:");
            // TODO: Fetch and display assigned activities
            System.out.println("Options:");
            System.out.println("1. Register Time");
            System.out.println("2. View/Edit My Registered Time");
            System.out.println("3. Register Future Fixed Activity Time (Vacation, etc.)");
            System.out.println("0. Back to Main Menu");
            System.out.print("# ");
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline
            handleTimeAndActivitiesChoice(choice, sc);
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
        clearConsole();
        System.out.print("Select Activity (Number/ID): ");
        String activityId = sc.nextLine();
        System.out.print("Enter Date (YYYY-MM-DD, default Today): ");
        String date = sc.nextLine();
        System.out.print("Enter Hours (0.5 increments): ");
        double hours = sc.nextDouble();
        sc.nextLine(); // Consume newline
        // TODO: Implement logic to register time
        System.out.println("Time registered successfully!");
    }

    private void editRegisteredTime(Scanner sc) {
        clearConsole();
        System.out.print("View for Date (YYYY-MM-DD): ");
        String date = sc.nextLine();
        System.out.print("Select ActivityID to Edit, or 0 to go Back: ");
        String activityId = sc.nextLine();
        if (!activityId.equals("0")) {
            System.out.print("Enter new Hours: ");
            double hours = sc.nextDouble();
            sc.nextLine(); // Consume newline
            // TODO: Implement logic to edit registered time
            System.out.println("Time updated successfully!");
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
            System.out.println("Projects:");
            List<Project> projects = systemStorage.getProjects(); // Fetch projects from SystemStorage
            for (Project project : projects) {
                String projectManager = project.getProjectManagerId() != null ? project.getProjectManagerId() : "<None>";
                System.out.println("- [" + project.getProjectId() + "] " + project.getProjectName() + " (PM: " + projectManager + ")");
            }
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

        try {
            blController.createProject(projectName);
            System.out.println("Project created successfully!");
            // wait for 2 seconds before returning to the menu
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
            }
            
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void manageProject(String projectId, Scanner sc) {
        // TODO: Implement project management logic
        System.out.println("Managing project: " + projectId);
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
        // TODO: Implement employee availability logic
    }

    private void generateReportsMenu(Scanner sc) {
        clearConsole();
        System.out.println("-- Reports --");
        System.out.println("Options:");
        System.out.println("1. Project Status Report (Time vs Budget)");
        System.out.println("2. Project Remaining Work Estimate Report");
        System.out.println("0. Back to Main Menu");
        System.out.print("# ");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline
        // TODO: Implement report generation logic
    }

    private void clearConsole() {
        // ANSI escape code to clear the console
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}