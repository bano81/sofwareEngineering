package dtu.ProjectManagementApp.businessLogic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Project {
    private String projectId;
    private String projectName;
    private Date deadline;
    private String projectManagerId;
    private List<Activity> activities = new ArrayList<>();// Initialize the list to avoid NullPointerException
    private List<Employee> assignedEmployees = new ArrayList<>();// Initialize the list to avoid NullPointerException

    public Project(String projectName) {
        this.projectName = projectName;
    }

    public Project(String projectName, Date deadline) {
        this.projectName = projectName;
        this.deadline = deadline;
    }
    public Project(String projectId, String projectName, Date deadline) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.deadline = deadline;
    }

    public Project(String projectId, String projectName) {
        this.projectId = projectId;
        this.projectName = projectName;
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

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
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
    
    public boolean activityExists(int activityID) {
        for (Activity activity : activities) {
            if (activity.getActivityId() ==activityID) {
                return true;
            }
        }
        return false;
    }
    public boolean isOverdue() {
        Date currentDate = new Date();
        return deadline.before(currentDate);
    }

    public boolean isCompleted() {
        for (Activity activity : activities) {
            if (!activity.isCompleted()) {
                return false;
            }
        }
        return true;
    }

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
    

    public String generateProjectId(SystemStorage systemStorage) {
        return String.valueOf((Calendar.getInstance().get(Calendar.YEAR) % 100) * 1000 + systemStorage.getProjects().size() + 1);
    }

    public List<Activity> getActivities() {
        return activities;
    }
}


