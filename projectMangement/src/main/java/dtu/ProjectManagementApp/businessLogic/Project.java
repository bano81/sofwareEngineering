package dtu.ProjectManagementApp.businessLogic;

import java.util.Date;
import java.util.List;


public class Project {
    private String projectId;
    private String projectName;
    private Date deadline;
    private Employee projectManager;
    // private Customer customer;
    private List<Activity> activities;

    public Project(String projectName, Date deadline) {
        this.projectId = generateProjectId();
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

    public Employee getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(Employee projectManager) {
        this.projectManager = projectManager;
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    public void removeActivity(Activity activity) {
        this.activities.remove(activity);
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

    public String generateProjectId() {
        String uniqueId = projectName + "_" + System.currentTimeMillis();
        return uniqueId;
    }
}

