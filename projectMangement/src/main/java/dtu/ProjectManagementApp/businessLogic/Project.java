package dtu.ProjectManagementApp.businessLogic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Project {
    private String projectId;
    private String projectName;
    private String deadline;
    private String projectManagerId;
    private List<Activity> activities = new ArrayList<>();
    private List<Employee> assignedEmployees = new ArrayList<>();

    public Project(String projectName) {
        this.projectName = projectName;
        this.projectId = generateProjectId();
    }

    public Project(String projectName, String deadline) {
        this.projectName = projectName;
        this.projectId = generateProjectId();
        this.deadline = deadline;
    }

    //for testing
    public Project(String projectId, String projectName, String deadline) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.deadline = deadline;
    }


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getProjectManagerId() {
        return projectManagerId;
    }

    public void setProjectManager(String projectManagerId) {
        this.projectManagerId = projectManagerId;
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    public void removeActivity(Activity activity) {
        this.activities.remove(activity);
    }

    public Activity getActivity(String activityName) {
        for (Activity activity : activities) {
            if (activity.getActivityName().equals(activityName)) {
                return activity;
            }
        }
        return null;
    }
    public Activity getActivityById(String activityId) {
        for (Activity activity : activities) {
            if (activity.getActivityId().equals(activityId)) {
                return activity;
            }
        }
        return null;
    }
    
    public boolean activityExists(String activityName) {
        for (Activity activity : activities) {
            if (activity.getActivityName() == activityName) {
                return true;
            }
        }
        return false;
    }
    // public boolean isOverdue() {
    //     Date currentDate = new Date();
    //     return deadline.before(currentDate);
    // }

    // public boolean isCompleted() {
    //     for (Activity activity : activities) {
    //         if (!activity.isCompleted()) {
    //             return false;
    //         }
    //     }
    //     return true;
    // }

    public void addEmployee(Employee employee) {
        this.assignedEmployees.add(employee);
    }

    public Employee getEmployee(String employeeId) {
        for (Employee employee : assignedEmployees) {
            if (employee.getEmployeeId().equals(employeeId)) {
                return employee;
            }
        }
        return null;
    }

    public List<Employee> getAssignedEmployees() {
        return this.assignedEmployees;
    }

    public boolean isEmployeeAssigned(String employeeId) {
        for (Employee employee : assignedEmployees) {
            if (employee.getEmployeeId().equals(employeeId)) {
                return true;
            }
        }
        return false;
    }
    

    public String generateProjectId() {
        int year = Calendar.getInstance().get(Calendar.YEAR) % 100;
        int nextNumber = (int) (Math.random() * 9000) + 1000; // Generate a random 4-digit number
        return String.valueOf(year * 1000 + nextNumber);
    }

    public List<Activity> getActivities() {
        return activities;
    }
    
    public List<String> getListOfActivityIdByEmployee(String employeeId) {
        List<String> activityIds = new ArrayList<>();
        for (Activity activity : activities) {
            if (activity.isEmployeeAssigned(employeeId)) {
                activityIds.add(activity.getActivityId());
            }
        }        
        return activityIds;
    }
    
    public int getNumberOfWeekActivityForEmployee(String employeeID, String weekNumber) {
        int count = 0;
        int inputYear = Integer.parseInt(weekNumber.substring(0, 2));
        int inputWeek = Integer.parseInt(weekNumber.substring(3));
        
        for (Activity activity : activities) {
            if (activity.isEmployeeAssigned(employeeID)) {
                String[] startParts = activity.getStartDate().split("-");
                int startYear = Integer.parseInt(startParts[0]);
                int startWeek = Integer.parseInt(startParts[1]);
                
                String[] endParts = activity.getEndDate().split("-");
                int endYear = Integer.parseInt(endParts[0]);
                int endWeek = Integer.parseInt(endParts[1]);
                
                if ((inputYear > startYear || (inputYear == startYear && inputWeek >= startWeek)) &&
                    (inputYear < endYear || (inputYear == endYear && inputWeek <= endWeek))) {
                    count++;
                }
            }
        }
        
        return count;
    }


}


