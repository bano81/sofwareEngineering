package dtu.ProjectManagementApp.businessLogic;

public class Employee {
 private String employeeId;
 private String name;
 private String surname;
 private String username;
 private String password;
 /*private Date birthday;
 private String email;
 private String telephonnumber;
 private Adresse adresse;
 */
 
 //private Map<String, Activity> activities = new HashMap<>(); // Activity ID and their corresponding activities
 //private Map<String, String> projectNames = new HashMap<>(); // Activity ID and their corresponding project names

//  private List<Project> projectList = new ArrayList<>(); // List of projects assigned to the employee
//  private List<Activity> activityList = new ArrayList<>(); // List of activities assigned to the employee

 private int numberOfActivities; // Number of activities assigned to the employee
 private final int maxActivities = 20; // Maximum number of activities allowed for an employee
 private boolean isEmployee = true; // Flag to check if the employee is a regular employee
 
 public Employee() {
     // Default constructor
 }

 public Employee(String name, String surname,String employeeId ) {
     this.name = name;
     this.surname = surname;
     this.employeeId = employeeId; // Set employee ID directly
 }

 public Employee(String name, String surname) {
     this.name = name;
     this.surname = surname;
     employeeId = name.toLowerCase().substring(0, 2) + surname.toLowerCase().substring(0, 2); // Generate employee ID based on name and surname
 }

 /*public Employee(String name, String surname, Date birthday, String email, String telephonnumber, Adresse adresse) {
     this.name = name;
     this.surname = surname;
     this.birthday = birthday;
     this.email = email;
     this.telephonnumber = telephonnumber;
     this.adresse = adresse;
     employeeId = name.toLowerCase().substring(0, 2) + surname.toLowerCase().substring(0, 2); // Generate employee ID based on name and surname
 }*/

 public String getEmployeeId() {
     return employeeId;
 }   

 public String getName() {
     return name;
 }

 public String getSurname() {
     return surname;
 }

 
//  public Activity getActivity(int id){
//     return activityList.get(id);
//  }

//  public List<Activity> getActivityList() {
//      return activityList;
//  }

//  public void setActivityList(Activity activity){
//      activityList.add(activity);
//  }



 public double calculateSpentHours(String id){
     return 0; // Placeholder for actual calculation logic
 }

 public void setUsername(String username) {
     this.username = username;
 }
 public String getUsername() {
     return username;
 }
 public void setPassword(String password) {
     this.password = password;
 }
 public String getPassword() {
     return password;
 }
 
 public void setNumberOfActivities(int numberOfActivities) {
     this.numberOfActivities = numberOfActivities;
}

 public int getNumberOfActivities() {
     return numberOfActivities;
 }

 public int getMaxActivities() {
     return maxActivities;
 }

 public boolean isEmployee() {
     return isEmployee;
 }
 public void setEmployee(boolean isEmployee) {
     this.isEmployee = isEmployee;
 }

 // Method to add a project name to the list of projects assigned to the employee
//  public void setProject(Project project) {
//      projectList.add(project);
//  }

//  public List<Project> getProjectList() {
//      return projectList;
//  }

//  public List<Activity> getAllActivities() {
//     List<Activity> activityList = new ArrayList<>();
//     for(Project project : projectList) {
//         activityList.addAll(project.getActivities());
//     }
//     return activityList;
// }

// public List<Activity> getActivitiesByProject(String projectName) {
//     List<Activity> activityList = new ArrayList<>();
//     for(Project project : projectList) {
//         if (project.getProjectName().equalsIgnoreCase(projectName)) {
//             activityList.addAll(project.getActivities());
//         }
//     }
//     return activityList;
// }

// public void countNumberOfActivities() {
//     List<Activity> allActivities = getActivityList();
//     int num = allActivities.size();
//     setNumberOfActivities(num);
// }

//  public void setBudgetedHours(String pojectName, String activityName, int hours) {
//     projectList.stream()
//             .filter(project -> project.getProjectName().equalsIgnoreCase(pojectName))
//             .flatMap(project -> project.getActivities().stream())
//             .filter(activity -> activity.getActivityName().equalsIgnoreCase(activityName))
//             .findFirst()
//             .ifPresent(activity -> activity.setBudgetedHours(hours));   
// }

// public void setCurrentSpentHours(String pojectName, String activityName, int hours){
//     projectList.stream()
//             .filter(project -> project.getProjectName().equalsIgnoreCase(pojectName))
//             .flatMap(project -> project.getActivities().stream())
//             .filter(activity -> activity.getActivityName().equalsIgnoreCase(activityName))
//             .findFirst()
//             .ifPresent(activity -> activity.setCurrentSpentHours(hours));   
// }

// public double geteBudgetedHours(String pojectName, String activityName) {
//     return projectList.stream()
//             .filter(project -> project.getProjectName().equalsIgnoreCase(pojectName))
//             .flatMap(project -> project.getActivities().stream())
//             .filter(activity -> activity.getActivityName().equalsIgnoreCase(activityName))
//             .findFirst()
//             .map(Activity::getBudgetedHours)
//             .orElse(0.0); // Return 0 if not found  
// }

// public double getCurrentSpentHours(String pojectName, String activityName){
//     return projectList.stream()
//             .filter(project -> project.getProjectName().equalsIgnoreCase(pojectName))
//             .flatMap(project -> project.getActivities().stream())
//             .filter(activity -> activity.getActivityName().equalsIgnoreCase(activityName))
//             .findFirst()
//             .map(Activity::getCurrentSpentHours)
//             .orElse(0.0); // Return 0 if not found 
// }

}