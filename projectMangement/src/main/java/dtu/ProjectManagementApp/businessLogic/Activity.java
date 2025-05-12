package dtu.ProjectManagementApp.businessLogic;
import java.util.ArrayList;
import java.util.List;

public class Activity {
   private String activityName;
   private double budgetedHours;
   private String startWeek;                    // format: YY-WW (e.g. 25-01)
   private String endWeek;                      // format: YY-WW (e.g. 25-05)
   private List<String> assignedEmployeeIDs = new ArrayList<>();
   private String activityId;

   public Activity(String activityName) {
       this.activityName = activityName;
   }

   public Activity(String activityName, String startWeek, String endWeek, double budgetedHours) {
    this.activityName = activityName;
    this.startWeek = startWeek;
    this.endWeek = endWeek;
    this.budgetedHours = budgetedHours;
   }

   // test constructor
   public Activity(String activityId, String activityName){
       this.activityId = activityId;
         this.activityName = activityName;
   }

   public void setActivityId(String activityId) {
       this.activityId = activityId;
   }

   public void assignEmployee(String employeeId) {
       assignedEmployeeIDs.add(employeeId);
   }
    public void removeEmployee(String employeeId) {
         assignedEmployeeIDs.remove(employeeId);
    }
    
    public boolean isEmployeeAssigned(String employeeId) {
    	for(String ID: assignedEmployeeIDs) {
    		if(ID.equalsIgnoreCase(employeeId)) {
    			return true;
    		}
    	}
		return false;
	}

    public void setBudgetedHours(double hours) {
        this.budgetedHours = hours;
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
    public double getBudgetedHours() {
        return budgetedHours;
    }

    public double getCurrentSpentHours(List<TimeRegistration> allTimeRegistrations) {
        double totalHours = 0;
        for (TimeRegistration tr : allTimeRegistrations) {
            if (tr.getActivity() != null && tr.getActivity().getActivityId().equals(this.activityId)) {
                totalHours += tr.getHoursSpent();
            }
        }
        return totalHours;
    }

    public String getStartDate() {
        return startWeek;
    }
    public String getEndDate() {
        return endWeek;
    }

    public List<String> getAssignedEmployees() {
        return assignedEmployeeIDs;
    }

    public String getActivityId() {
       return activityId;
    }


    
    

    
    


    
}

