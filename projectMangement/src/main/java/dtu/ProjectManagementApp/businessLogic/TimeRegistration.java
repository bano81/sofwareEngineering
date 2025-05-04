package dtu.ProjectManagementApp.businessLogic;

import java.util.Date;

public class TimeRegistration {
    private String TimeRegistrationId;
    private Employee employee;
    private Project project;
    private Activity activity;
    private Date date;
    private double hoursSpent;
    private String description;

    public TimeRegistration(String timeRegistrationId, Employee employee, Project project, Activity activity, Date date, double hoursSpent, String description) {
        this.TimeRegistrationId = timeRegistrationId;
        this.employee = employee;
        this.project = project;
        this.activity = activity;
        this.date = date;
        this.hoursSpent = hoursSpent;
        this.description = description;
    }

}
