 #Created by augusthansen at 04/05/2025
 Feature: Time Registration
     a user should be able to register hours spent on an activity after a day of work
     So that they can keep track of work hours and activities, and so managers can see the progress.
     when a user registers spent hours the system should store the time registration with activity, date, hours and description.
     The user should not have to remember different activity id's and project id's, the system should show me a list of activities and projects to choose from.

   Scenario: Successfully register time for an activity
     Given The employee with ID "valid_employee1" is logged in
     And a project "Website Redesign" with ID "25001" and deadline "2025-06-12" exists
     And an activity "Frontend Development" with ID "A001", in project with id "25001" exists
     And an activity "Frontend Development" with ID "A001" belongs to project "25001"
     And the employee with ID "valid_employee1" is assigned to activity with "A001"
     When a user registers time spent on activity with Id "A001", on date "2025-05-15", hours spent "7.5", and description "Implemented navigation bar and fixed layout issues"
     Then the time registration should be saved to SystemStorage


   Scenario: Reject time registration with negative hours
     Given The employee with ID "valid_employee1" is logged in
     And a project "Website Redesign" with ID "25001" and deadline "2025-06-12" exists
     And an activity "Frontend Development" with ID "A001", in project with id "25001" exists
     And an activity "Frontend Development" with ID "A001" belongs to project "25001"
     And the employee with ID "valid_employee1" is assigned to activity with "A001"
     When a user registers time spent on activity with Id "A001", on date "2025-05-15", hours spent "-7.5", and description "Implemented navigation bar and fixed layout issues"
     Then the time registration should not be saved to SystemStorage
     And the system should display an error message "Invalid hours format"


   Scenario: Reject time registration for non-existent activity
     Given The employee with ID "valid_employee1" is logged in
     And a project "Website Redesign" with ID "25001" and deadline "2025-06-12" exists
     And an activity "Frontend Development" with ID "A001", in project with id "25001" exists
     And an activity "Frontend Development" with ID "A001" belongs to project "25001"
     And the employee with ID "valid_employee1" is assigned to activity with "A001"
     When a user registers time spent on activity with Id "A002test1", on date "2025-05-15", hours spent "7.5", and description "Implemented navigation bar and fixed layout issues"
     Then the time registration should not be saved to SystemStorage
     And  the system should display an error message "Project with activity ID A002test1 does not exist"

   Scenario: Reject time registration when no user is logged in
     Given No user is logged in
     And a project "Website Redesign" with ID "25001" and deadline "2025-06-12" exists
     And an activity "Frontend Development" with ID "A001", in project with id "25001" exists
     And an activity "Frontend Development" with ID "A001" belongs to project "25001"
     When a user registers time spent on activity with Id "A001", on date "2025-05-15", hours spent "7.5", and description "Implemented navigation bar and fixed layout issues"
     Then the time registration should not be saved to SystemStorage
     And  the system should display an error message "No user is logged in"


   Scenario: register time with invalid dateformat
     Given The employee with ID "valid_employee1" is logged in
     And a project "Website Redesign" with ID "25001" and deadline "2025-06-12" exists
     And an activity "Frontend Development" with ID "A001", in project with id "25001" exists
     And an activity "Frontend Development" with ID "A001" belongs to project "25001"
     And the employee with ID "valid_employee1" is assigned to activity with "A001"
     When a user registers time spent on activity with Id "A001", on date "202-05-15", hours spent "7.5", and description "Implemented navigation bar and fixed layout issues"
     Then the time registration should not be saved to SystemStorage
     And the system should display an error message "Invalid date format. Use yyyy-MM-dd"

