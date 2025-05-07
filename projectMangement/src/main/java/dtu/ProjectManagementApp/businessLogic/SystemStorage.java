package dtu.ProjectManagementApp.businessLogic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SystemStorage {

    private final List<Employee> employees = new ArrayList<>();
    private final List<Project> projects = new ArrayList<>();
    private final List<TimeRegistration> timeRegistrations = new ArrayList<>();
    private Employee employeeLoggedIn = null; // Variable to store the logged-in employee


    public SystemStorage() {
    }


    public void initiateTestUsers(BLController blController) {
        employees.add(new Employee("Hubert", "Baumeister", "huba")); 
        employees.add(new Employee("Silas", "Kejser", "sike")); 
        
        projects.add(new Project("Project1","25-05"));
        projects.add(new Project("Project2","25-06"));
        projects.add(new Project("Project3","25-07"));
        
        Project project1 = getProjectByName("Project1");
        project1.addActivity(new Activity("design", "25-01", "25-02", 20));
        project1.addActivity(new Activity("analysis", "25-02", "25-03", 20));
        project1.addActivity(new Activity("implementation", "25-03", "25-05", 20));
    }

    public void addEmployee(Employee employee) {
        employees.add(employee); // Create a new employee and add it to the list
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public Employee getEmployee(String employeeId) {
        return employees.stream()
                .filter(employee -> employee.getEmployeeId().equals(employeeId))
                .findFirst()
                .orElse(null);
    }

    public void setLoggedInEmployee(Employee employee) {
        employeeLoggedIn = employee; // Set the logged-in employee
    }
    public void removeLoggedInEmployee() {
        employeeLoggedIn = null; // Remove the logged-in employee
    }
    
    public Employee getLoggedInEmployee()  {
        return employeeLoggedIn;
    }

// ####################### Projects #######################
    public void addProject(Project project) {
        projects.add(project);
        //project.setProjectId(generateProjectId());  //DELETE?
    }

    public Project getProject(String projectID) {
        return projects.stream()
                .filter(project -> project.getProjectId().equals(projectID))
                .findFirst()
                .orElse(null);
    }
    
    public Project getProjectByName(String projectName) {
        return projects.stream()
                .filter(project -> project.getProjectName().equals(projectName))
                .findFirst()
                .orElse(null);
    }
    public boolean projectIDExists(String projectId) {
        // Check if the project ID already exists in the list of projects
        return projects.stream()
                .anyMatch(project -> project.getProjectId().equals(projectId));        
    }
    

    public  void removeProject(String projectID) {
        projects.remove(getProject(projectID));
    }

    public  List<Project> getProjects() {
        return projects;
    }


    public boolean employeeExists(String employeeId) {
        // Check if the employee ID already exists in the list of employees
        return employees.stream()
                .anyMatch(employee -> employee.getEmployeeId().equals(employeeId));        
    }
    public void resetLoginState() {
        employeeLoggedIn = null;
    }

    //shoudl prly be deleted
    //public String generateProjectId() {
    //    int year = Calendar.getInstance().get(Calendar.YEAR) % 100;
    //    int nextNumber = projects.size() + 1;
    //    return String.valueOf(year * 1000 + nextNumber);
    //}


   // ####################### Activities #######################

    public List<Activity> getActivitiesForUser(String username) {
        List<Activity> userActivities = new ArrayList<>();
        for (Project project : projects) {
            for (Activity activity : project.getActivities()) {
                // Directly compare the username with the list of assigned employee IDs
                if (activity.getAssignedEmployees().stream().anyMatch(employeeId -> employeeId.equals(username))) {
                    userActivities.add(activity);
                }
            }
        }
        return userActivities;
    }

    /* TIME REGISTRATION*/
    public void addTimeRegistration(TimeRegistration timeRegistration) {
        timeRegistrations.add(timeRegistration);
    }
    public List<TimeRegistration> getTimeRegistrations() {
        return timeRegistrations;
    }
}