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

    public Project(String projectName, String projectId) {
        this.projectName = projectName;
        this.projectId = projectId;
    }

    //for testing
    public Project(String projectName,String projectId, String deadline) {
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

    public int getWeeksUntilDeadline() {
        if (deadline == null || this.deadline.isEmpty()) {
            return -1; // No deadline set
        }
        try {
            String[] parts = deadline.split("-");
            int deadlineYear = 2000 + Integer.parseInt(parts[0]);
            int deadlineWeek = Integer.parseInt(parts[1]);
            Calendar current = Calendar.getInstance();
            int currentYear = current.get(Calendar.YEAR);
            int currentWeek = current.get(Calendar.WEEK_OF_YEAR);
            return (deadlineYear - currentYear) * 52 + (deadlineWeek - currentWeek);
        } catch (NumberFormatException e) {
            return -1; // Invalid date format
        }
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
        int nextActivityNumber = activities.size() + 1;
        String activityId = generateActivityId(nextActivityNumber);
        activity.setActivityId(activityId);
        this.activities.add(activity);
    }

    private String generateActivityId(int activityNumber) {
        return this.projectId + String.format("%03d", activityNumber);
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


