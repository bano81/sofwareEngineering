package dtu.ProjectManagementApp.businessLogic;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Activity {
   private int activityId;
   private String activityName;
   private Project activityAssignedProject;
   private String ativityStatus;
   //private Activity activityType;
   private double budgetedHours;
   private double currentSpentHours;
   private Date startDate;
   private Date endDate;
   private List<String> assignedEmployeeIDs;

   LocalDate today = LocalDate.now();
   private int activityStartYear;
   private int activityEndYear;
   private int activityStartWeekNumber;
   private int activityEndWeekNumber;

   public Activity(String activityName) {
       this.activityName = activityName;
   }

   public Activity(int activityId, String activityName) {
    this.activityName = activityName;
    this.activityId = activityId;
   }

   public Activity(String activityName, String activityStatus, Date startDate, Date endDate) {
    this.activityName = activityName;
    this.ativityStatus = activityStatus;
    this.startDate = startDate;
    this.endDate = endDate;
   }

   public Activity(String activityName, String ativityStatus, Date startDate, Date endDate, double budgetedHours) {
    this.activityName = activityName;
    this.ativityStatus = ativityStatus;
    this.startDate = startDate;
    this.endDate = endDate;
    this.budgetedHours = budgetedHours;
   }

   public Activity(String activityName, Date startDate, Date endDate, double budgetedHours) {
    this.activityName = activityName;
    this.startDate = startDate;
    this.endDate = endDate;
    this.budgetedHours = budgetedHours;
   }

   public void assignEmployee(String employeeId) {
       assignedEmployeeIDs.add(employeeId);
   }
    public void removeEmployee(String employeeId) {
         assignedEmployeeIDs.remove(employeeId);
    }
    public void assignProject(Project project) {
        this.activityAssignedProject = project;
    }
    
    public void assignEmployeeToActivity(String employeeId) {
        assignedEmployeeIDs.add(employeeId); // Add the employee ID to the list of assigned employees
    }
    public void removeProject() {
        this.activityAssignedProject = null;
    }
    public void setBudgetedHours(double hours) {
        this.budgetedHours = hours;
    }
    public void setCurrentSpentHours(double hours) {
        this.currentSpentHours = hours;
    }
    public void setActivityStatus(String status) {
        this.ativityStatus = status;
    }
    public void setStartDate(Date date) {
        this.startDate = date;
    }
    public void setEndDate(Date date) {
        this.endDate = date;
    }
    public void setActivityName(String name) {
        this.activityName = name;
    }
    public void setActivityId(int id) {
        this.activityId = id;
    }
    public int getActivityId() {
        return activityId;
    }
    public String getActivityName() {
        return activityName;
    }
    public String getActivityStatus() {
        return ativityStatus;
    }
    public double getBudgetedHours() {
        return budgetedHours;
    }
    public double getCurrentSpentHours() {
        return currentSpentHours;
    }
    public Date getStartDate() {
        return startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public Project getActivityAssignedProject() {
        return activityAssignedProject;
    }
    public List<String> getAssignedEmployees() {
        return assignedEmployeeIDs;
    }
    public void setAssignedEmployees(List<String> assignedEmployeeIDs) {
        this.assignedEmployeeIDs = assignedEmployeeIDs;
    }
    public void setActivityAssignedProject(Project activityAssignedProject) {
        this.activityAssignedProject = activityAssignedProject;
    }
    public void setAtivityStatus(String ativityStatus) {
        this.ativityStatus = ativityStatus;
    }
    public boolean isOverdue() {
        Date currentDate = new Date();
        return endDate.before(currentDate);
    }
    public boolean isCompleted() {
        return ativityStatus.equals("Completed");
    }
    public boolean isInProgress() {
        return ativityStatus.equals("In Progress");
    }
    public boolean isNotStarted() {
        return ativityStatus.equals("Not Started");
    }
    public boolean isOnHold() {
        return ativityStatus.equals("On Hold");
    }
    public boolean isCancelled() {
        return ativityStatus.equals("Cancelled");
    }

    // Get a date (Monday of the week) from week number and year
    /*public LocalDate getDateFromWeek(int year, int week) {
        return LocalDate.of(year, 1, 1)
            .with(WeekFields.ISO.weekBasedYear(), year)
            .with(WeekFields.ISO.weekOfWeekBasedYear(), week)
            .with(WeekFields.ISO.dayOfWeek(), 1); // Monday
    }  */
      

    public boolean isTimeDateValid(int year, int week) {
        LocalDate inputDate = LocalDate.now();  //getDateFromWeek(year, week);   
        if (inputDate.isAfter(today) || inputDate.isEqual(today)) {
            return true; // Future or current week is valid for reporting
        } else {
            return false; // Past week is not valid for reporting
        }
    }


    
    

    
    


    
}

