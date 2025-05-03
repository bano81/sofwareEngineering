package dtu.ProjectManagementApp.businessLogic;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;


public class Project {
    private String projectId;
    private String projectName;
    private Date deadline;
    private String projectManagerId;
    private List<Activity> activities = new ArrayList<>(); // Initialize the list to avoid NullPointerException

    public Project(String projectName) {
        this.projectId = generateProjectId();
        this.projectName = projectName;
    }

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
        return String.valueOf((Calendar.getInstance().get(Calendar.YEAR) % 100) * 1000 + SystemStorage.getProjects().size() + 1);
    }

    public List<Activity> getActivities() {
        return activities;
    }
}

