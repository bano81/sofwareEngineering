package dtu.ProjectManagementApp.businessLogic;


import java.util.ArrayList;
import java.util.List;


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

 private List<Project> projectList = new ArrayList<>(); // List of projects assigned to the employee
 private List<Activity> activityList = new ArrayList<>(); // List of activities assigned to the employee

 private int numberOfActivities; // Number of activities assigned to the employee
 private final int maxActivities = 20; // Maximum number of activities allowed for an employee
 private boolean isAdmin = false;
 private boolean isProjectManager = false; // Flag to check if the employee is a project manager
 private boolean isEmployee = true; // Flag to check if the employee is a regular employee
 
 public Employee() {
     // Default constructor
 }

 public Employee(String name, String surname,String employeeId ) {
     this.name = name;
     this.surname = surname;
     this.employeeId = employeeId; // Set employee ID directly
 }

 public Employee(String name, String surname,String employeeId, boolean isAdmin, boolean isProjectManager) {
    this.name = name;
    this.surname = surname;
    this.employeeId = employeeId; // Set employee ID directly
    this.isAdmin = isAdmin; // Set admin status
    this.isProjectManager = isProjectManager; // Set project manager status
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

 
 public Activity getActivity(int id){
    return activityList.get(id);
 }

 public List<Activity> getActivityList() {
     return activityList;
 }

 public void setActivityList(Activity activity){
     activityList.add(activity);
 }



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

 public void setAdmin(boolean isAdmin) {
     this.isAdmin = isAdmin;
 }
 public boolean isAdmin() {
     return isAdmin;
 }

 public void setProjectManager(boolean isProjectManager) {
     this.isProjectManager = isProjectManager;
 }
 public boolean isProjectManager() {
     return isProjectManager;
 }

 public boolean isEmployee() {
     return isEmployee;
 }
 public void setEmployee(boolean isEmployee) {
     this.isEmployee = isEmployee;
 }


 // Method to add a project name to the list of projects assigned to the employee
 public void setProject(Project project) {
     projectList.add(project);
 }

 public List<Project> getProjectList() {
     return projectList;
 }

 public List<Activity> getAllActivities() {
    List<Activity> activityList = new ArrayList<>();
    for(Project project : projectList) {
        activityList.addAll(project.getActivities());
    }
    return activityList;
}

public List<Activity> getActivitiesByProject(String projectName) {
    List<Activity> activityList = new ArrayList<>();
    for(Project project : projectList) {
        if (project.getProjectName().equalsIgnoreCase(projectName)) {
            activityList.addAll(project.getActivities());
        }
    }
    return activityList;
}

public void countNumberOfActivities() {
    List<Activity> allActivities = getActivityList();
    int num = allActivities.size();
    setNumberOfActivities(num);
}

 public void setBudgetedHours(String pojectName, String activityName, int hours) {
    projectList.stream()
            .filter(project -> project.getProjectName().equalsIgnoreCase(pojectName))
            .flatMap(project -> project.getActivities().stream())
            .filter(activity -> activity.getActivityName().equalsIgnoreCase(activityName))
            .findFirst()
            .ifPresent(activity -> activity.setBudgetedHours(hours));   
}

public void setCurrentSpentHours(String pojectName, String activityName, int hours){
    projectList.stream()
            .filter(project -> project.getProjectName().equalsIgnoreCase(pojectName))
            .flatMap(project -> project.getActivities().stream())
            .filter(activity -> activity.getActivityName().equalsIgnoreCase(activityName))
            .findFirst()
            .ifPresent(activity -> activity.setCurrentSpentHours(hours));   
}

public double geteBudgetedHours(String pojectName, String activityName) {
    return projectList.stream()
            .filter(project -> project.getProjectName().equalsIgnoreCase(pojectName))
            .flatMap(project -> project.getActivities().stream())
            .filter(activity -> activity.getActivityName().equalsIgnoreCase(activityName))
            .findFirst()
            .map(Activity::getBudgetedHours)
            .orElse(0.0); // Return 0 if not found  
}

public double getCurrentSpentHours(String pojectName, String activityName){
    return projectList.stream()
            .filter(project -> project.getProjectName().equalsIgnoreCase(pojectName))
            .flatMap(project -> project.getActivities().stream())
            .filter(activity -> activity.getActivityName().equalsIgnoreCase(activityName))
            .findFirst()
            .map(Activity::getCurrentSpentHours)
            .orElse(0.0); // Return 0 if not found 
}


/*
public void setActivity(String id, Activity activity){
    activities.put(id, activity);
}

public void setActivity(Activity activity){
    activities.put(activity.getActivityName(), activity);
}

public Activity getActivity(String id){
    return activities.get(id);
}

public Map<String, Activity> getActivities() {
	    return activities;
}
public void getlistOfProjects(){
    for (Map.Entry<String, Activity> entry : activities.entrySet()) {
        String projectName = entry.getKey();
        System.out.println("Project Name: " + projectName);
    }
}

public double geteBudgetedHours(String id) {
	 return activities.get(id).getBudgetedHours();
}


public void setBudgetedHours(String id, double hours){
	 activities.get(id).setBudgetedHours(hours);
}

public void setCurrentSpentHours(String id, double hours){
	 activities.get(id).setCurrentSpentHours(hours);
}

public double getCurrentSpentHours(String id){
	    return activities.get(id).getCurrentSpentHours(); 
}
public void removeActivity(String id){
   activities.remove(id);
}

public void removeAllActivities(){
   activities.clear();
}



public void sortActivitiesByDate() {
   Map<String, Activity> sortedMap = new LinkedHashMap<>();
   activities.entrySet().stream()
           .sorted(Map.Entry.comparingByValue(Comparator.comparing(Activity::getStartDate)))
           .forEachOrdered(entry -> sortedMap.put(entry.getKey(), entry.getValue()));
   activities = sortedMap;
}

 /*public void countNumberOfActivities() {
	    int num = 0;
	    for (Map.Entry<String, Activity> entry : activities.entrySet()) {
	        Activity activity = entry.getValue();
	        if (!activity.getActivityStatus().equalsIgnoreCase("Completed")) {
	            num++;
	        }
	    }
	    setNumberOfActivities(num);
 }*/

 /*public void getlistOfActivities(){
	 System.out.println("Activity ID \t Activity");
	 System.out.println("----------- \t ----------");
     for (Map.Entry<String, Activity> entry : activities.entrySet()) {
         String projectName = entry.getKey();
         Activity activity = entry.getValue();
         System.out.println(projectName + " \t\t " + activity.getActivityName());
     }
 }

 public void setBudgetedHours(String id, int hours){
     Activity activity = activities.get(id);
     if (activity != null) {
         activity.setBudgetedHours(hours);
     } else {
         System.out.println("Activity not found!");
     }
 }
 
 public void setBirthday(Date birthday){
     this.birthday = birthday;
 }

 public void setEmail(String email){
     this.email = email;
 }

 public void setTelephonnumber(String telephonnumber){
     this.telephonnumber = telephonnumber;
 }

 public void setAdresse(Adresse adresse){
     this.adresse = adresse;
 }

 public Date getBirthday() {
    return birthday;
}

public String getEmail() {
    return email;
}

public String getTelephonnummer() {
    return telephonnumber;
}

public Adresse getAdresse() {
    return adresse;
}*/

}