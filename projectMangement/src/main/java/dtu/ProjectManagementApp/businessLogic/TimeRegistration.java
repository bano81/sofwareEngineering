package dtu.ProjectManagementApp.businessLogic;

import java.time.LocalDate;
import java.util.Date;

public class TimeRegistration {
    private String timeRegistrationId;
    private Employee employee;
    private Project project;
    private Activity activity;
    private LocalDate localDate;
    private double hoursSpent;
    private String description;

    public TimeRegistration(Employee employee, Project project, Activity activity, LocalDate localDate, double hoursSpent, String description) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        if (project == null) {
            throw new IllegalArgumentException("Project cannot be null");
        }
        if (activity == null) {
            throw new IllegalArgumentException("Activity cannot be null");
        }
        if (localDate == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (hoursSpent <= 0) {
            throw new IllegalArgumentException("Invalid hours format");
        }
        this.timeRegistrationId = generateTimeRegistrationId();
        this.employee = employee;
        this.project = project;
        this.activity = activity;
        this.localDate = localDate;
        this.hoursSpent = hoursSpent;
        this.description = description;
    }

    private String generateTimeRegistrationId() {
        return "TR" + System.currentTimeMillis();
    }

    // Getters
    public String getTimeRegistrationId() {
        return timeRegistrationId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Project getProject() {
        return project;
    }

    public Activity getActivity() {
        return activity;
    }

    public LocalDate getDate() {

        return localDate;
    }

    public double getHoursSpent() {
        return hoursSpent;
    }

    public String getDescription() {
        return description;
    }

}
