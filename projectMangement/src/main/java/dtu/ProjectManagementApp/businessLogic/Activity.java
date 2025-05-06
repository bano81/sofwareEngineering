package dtu.ProjectManagementApp.businessLogic;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Activity {
   private String activityName;
   private Project activityAssignedProject;
   private String ativityStatus;
   private double budgetedHours;
   private double currentSpentHours;
   private String startWeek;                    // format: YY-WW (e.g. 25-01)
   private String endWeek;                      // format: YY-WW (e.g. 25-05)
   private List<String> assignedEmployeeIDs = new ArrayList<>();
   private String activityId;

//    LocalDate today = LocalDate.now();
//    private int activityStartYear;
//    private int activityEndYear;
//    private int activityStartWeekNumber;
//    private int activityEndWeekNumber;

   public Activity(String activityName) {
       this.activityName = activityName;
       this.activityId = generateActivityId();
   }

   public Activity(String activityName, String activityStatus, String startWeek, String endWeek) {
    this.activityName = activityName;
    this.ativityStatus = activityStatus;
    this.startWeek = startWeek;
    this.endWeek = endWeek;
    this.activityId = generateActivityId(); // Generate a unique ID for the activity
   }

   public Activity(String activityName, String ativityStatus, String startWeek, String endWeek, double budgetedHours) {
    this.activityName = activityName;
    this.ativityStatus = ativityStatus;
    this.startWeek = startWeek;
    this.endWeek = endWeek;
    this.budgetedHours = budgetedHours;
    this.activityId = generateActivityId();
   }

   public Activity(String activityName, String startWeek, String endWeek, double budgetedHours) {
    this.activityName = activityName;
    this.startWeek = startWeek;
    this.endWeek = endWeek;
    this.budgetedHours = budgetedHours;
    this.activityId = generateActivityId();
   }

   public String generateActivityId() {
       return String.valueOf(System.currentTimeMillis()); // TODO: Implement better ID generation
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
    public void setStartDate(String date) {
        this.startWeek = date;
    }
    public void setEndDate(String date) {
        this.endWeek = date;
    }
    public void setActivityName(String name) {
        this.activityName = name;
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
    public String getStartDate() {
        return startWeek;
    }
    public String getEndDate() {
        return endWeek;
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
    // public boolean isOverdue() {
    //     Date currentDate = new Date();
    //     return endDate.before(currentDate);
    // }
    // public boolean isCompleted() {
    //     return ativityStatus.equals("Completed");
    // }
    // public boolean isInProgress() {
    //     return ativityStatus.equals("In Progress");
    // }
    // public boolean isNotStarted() {
    //     return ativityStatus.equals("Not Started");
    // }
    // public boolean isOnHold() {
    //     return ativityStatus.equals("On Hold");
    // }
    // public boolean isCancelled() {
    //     return ativityStatus.equals("Cancelled");
    // }

    // Get a date (Monday of the week) from week number and year
    /*public LocalDate getDateFromWeek(int year, int week) {
        return LocalDate.of(year, 1, 1)
            .with(WeekFields.ISO.weekBasedYear(), year)
            .with(WeekFields.ISO.weekOfWeekBasedYear(), week)
            .with(WeekFields.ISO.dayOfWeek(), 1); // Monday
    }  */
      

    // public boolean isTimeDateValid(int year, int week) {
    //     LocalDate inputDate = LocalDate.now();  //getDateFromWeek(year, week);   
    //     if (inputDate.isAfter(today) || inputDate.isEqual(today)) {
    //         return true; // Future or current week is valid for reporting
    //     } else {
    //         return false; // Past week is not valid for reporting
    //     }
    // }


    
    

    
    


    
}

