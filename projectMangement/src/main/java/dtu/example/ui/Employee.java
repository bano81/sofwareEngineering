package dtu.example.ui;

import java.util.*;


public class Employee {
 private String employeeId;
 private String name;
 private String surname;
 private Date birthday;
 private String email;
 private String telephonnumber;
 private Adresse adresse;
 private String username;
 private String password;
 private Map<String, Activity> activities = new HashMap<>(); // Activity ID and their corresponding activities
 private Map<String, String> projectNames = new HashMap<>(); // Activity ID and their corresponding project names
 private int numberOfActivities; // Number of activities assigned to the employee
 private final int maxActivities = 20; // Maximum number of activities allowed for an employee
 
 public Employee() {
     // Default constructor
 }

 public Employee(String employeeId, String name, String surname) {
     this.name = name;
     this.surname = surname;
     this.employeeId = employeeId; // Set employee ID directly
 }

 public Employee(String name, String surname) {
     this.name = name;
     this.surname = surname;
     employeeId = name.toLowerCase().substring(0, 2) + surname.toLowerCase().substring(0, 2); // Generate employee ID based on name and surname
 }

 public Employee(String name, String surname, Date birthday, String email, String telephonnumber, Adresse adresse) {
     this.name = name;
     this.surname = surname;
     this.birthday = birthday;
     this.email = email;
     this.telephonnumber = telephonnumber;
     this.adresse = adresse;
     employeeId = name.toLowerCase().substring(0, 2) + surname.toLowerCase().substring(0, 2); // Generate employee ID based on name and surname
 }

 public String getEmployeeId() {
     return employeeId;
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

 public String getName() {
     return name;
 }

 public String getSurname() {
     return surname;
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
 }

 public void setActivity(String id, Activity activity){
     activities.put(id, activity);
 }

 public Activity getActivity(String id){
     return activities.get(id);
 }


 /*public void getlistOfActivities(){
	 System.out.println("Activity ID \t Activity");
	 System.out.println("----------- \t ----------");
     for (Map.Entry<String, Activity> entry : activities.entrySet()) {
         String projectName = entry.getKey();
         Activity activity = entry.getValue();
         System.out.println(projectName + " \t\t " + activity.getActivityName());
     }
 }*/
 
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

 /*public void setBudgetedHours(String id, int hours){
     Activity activity = activities.get(id);
     if (activity != null) {
         activity.setBudgetedHours(hours);
     } else {
         System.out.println("Activity not found!");
     }
 }*/
 
 public void setBudgetedHours(String id, int hours){
	 activities.get(id).setBudgetedHours(hours);
 }

 public void setCurrentSpentHours(String id, double hours){
	 activities.get(id).setCurrentSpentHours(hours);
 }
 
 
 public double getCurrentSpentHours(String id){
	    return activities.get(id).getCurrentSpentHours(); 
 }

 public double calculateSpentHours(String id){
     return 0; // Placeholder for actual calculation logic
 }

 public void removeActivity(String id){
     activities.remove(id);
 }

 public void removeAllActivities(){
     activities.clear();
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
 
 public void countNumberOfActivities() {
	    int num = 0;
	    for (Map.Entry<String, Activity> entry : activities.entrySet()) {
	        Activity activity = entry.getValue();
	        if (!activity.getActivityStatus().equalsIgnoreCase("Completed")) {
	            num++;
	        }
	    }
	    setNumberOfActivities(num);
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
 
 public void sortActivitiesByDate() {
	    Map<String, Activity> sortedMap = new LinkedHashMap<>();
	    activities.entrySet().stream()
	            .sorted(Map.Entry.comparingByValue(Comparator.comparing(Activity::getStartDate)))
	            .forEachOrdered(entry -> sortedMap.put(entry.getKey(), entry.getValue()));
	    activities = sortedMap;
 }

}