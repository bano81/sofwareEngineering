package dtu.ProjectManagementApp.businessLogic;

import java.time.LocalDate;

public class TimeRegistration {
    private String timeRegistrationId;
    private Employee employee;
    private Project project;
    private Activity activity;
    private LocalDate localDate;
    private double hoursSpent;
    private String description;

    public TimeRegistration(Employee employee, Project project, Activity activity, LocalDate localDate, double hoursSpent, String description) {

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

    // Setters
    public void setHoursSpent(double hours) {
        if (hours <= 0 || hours > 24) {
            throw new IllegalArgumentException("Invalid hours format, must be between 0 and 24");
        }
        this.hoursSpent = hours;
    }

    public void setDescription(String newDescription) {
        if (newDescription == null || newDescription.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        this.description = newDescription;
    }
}
