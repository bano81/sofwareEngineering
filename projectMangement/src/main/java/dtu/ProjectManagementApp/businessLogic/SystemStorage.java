package dtu.ProjectManagementApp.businessLogic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SystemStorage {

    private final List<Employee> employees = new ArrayList<>();
    private final List<Project> projects = new ArrayList<>();
    private Employee employeeLoggedIn = null; // Variable to store the logged-in employee


    public SystemStorage() {
    }


    public void initiateTestUsers(BLController blController) {

        //test user
        employees.add(new Employee("test", "normal", "employee")); // Create a test user
        employees.add(new Employee("test4", "normal", "employee2")); // Create a test user
        
        projects.add(new Project("1", "P1")); // Create a test project
        projects.add(new Project("2", "P2")); // Create a test project
        projects.add(new Project("3", "P3")); // Create a test project
        
        getProjectByName("P1").addActivity(new Activity(1,"design"));
        getProjectByName("P1").addActivity(new Activity(2,"analysis"));
        getProjectByName("P2").addActivity(new Activity(3,"implementation"));

        /*SystemStorage.getEmployee("admin").setActivityList(new Activity(1,"desing"));
        SystemStorage.getEmployee("admin").setActivityList(new Activity(2,"analysis"));
        SystemStorage.getEmployee("admin").setActivityList(new Activity(3,"implementation"));
        SystemStorage.getEmployee("employee").setActivityList(new Activity(1,"desing"));
        SystemStorage.getEmployee("employee2").setActivityList(new Activity(3,"implementation"));*/

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
        project.setProjectId(generateProjectId());
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

    public String generateProjectId() {
        int year = Calendar.getInstance().get(Calendar.YEAR) % 100;
        int nextNumber = projects.size() + 1;
        return String.valueOf(year * 1000 + nextNumber);
    }


    /*  activity stuff*/
    //public Activity getActivityByID(String activityID){
//
    //}

}
