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
     And the latest time registration should have employee "valid_employee1"
     And the latest time registration should be for activity "A001"
     And the latest time registration should have date "2025-05-15"
     And the latest time registration should have "7.5" hours spent
     And the latest time registration should have description "Implemented navigation bar and fixed layout issues"


   Scenario: Fail to register time with negative hours
     Given The employee with ID "valid_employee1" is logged in
     And a project "Website Redesign" with ID "25001" and deadline "2025-06-12" exists
     And an activity "Frontend Development" with ID "A001", in project with id "25001" exists
     And an activity "Frontend Development" with ID "A001" belongs to project "25001"
     And the employee with ID "valid_employee1" is assigned to activity with "A001"
     When a user registers time spent on activity with Id "A001", on date "2025-05-15", hours spent "-7.5", and description "Implemented navigation bar and fixed layout issues"
     Then the time registration should not be saved to SystemStorage
        And the system should display an error message "Invalid hours format, must be between 0 and 24"

   Scenario: Fail to register time with zero hours
     Given The employee with ID "valid_employee1" is logged in
     And a project "Website Redesign" with ID "25001" and deadline "2025-06-12" exists
     And an activity "Frontend Development" with ID "A001", in project with id "25001" exists
     And an activity "Frontend Development" with ID "A001" belongs to project "25001"
     And the employee with ID "valid_employee1" is assigned to activity with "A001"
     When a user registers time spent on activity with Id "A001", on date "2025-05-15", hours spent "0", and description "Implemented navigation bar and fixed layout issues"
     Then the time registration should not be saved to SystemStorage
     And the system should display an error message "Invalid hours format, must be between 0 and 24"

   Scenario: Fail to register time with more than 24 hours
     Given The employee with ID "valid_employee1" is logged in
     And a project "Website Redesign" with ID "25001" and deadline "2025-06-12" exists
     And an activity "Frontend Development" with ID "A001", in project with id "25001" exists
     And an activity "Frontend Development" with ID "A001" belongs to project "25001"
     And the employee with ID "valid_employee1" is assigned to activity with "A001"
     When a user registers time spent on activity with Id "A001", on date "2025-05-15", hours spent "24.1", and description "Implemented navigation bar and fixed layout issues"
     Then the time registration should not be saved to SystemStorage
     And the system should display an error message "Invalid hours format, must be between 0 and 24"


   Scenario: Fail to register time for non-existent activity
     Given The employee with ID "valid_employee1" is logged in
     And a project "Website Redesign" with ID "25001" and deadline "2025-06-12" exists
     And an activity "Frontend Development" with ID "A001", in project with id "25001" exists
     And an activity "Frontend Development" with ID "A001" belongs to project "25001"
     And the employee with ID "valid_employee1" is assigned to activity with "A001"
     When a user registers time spent on activity with Id "A002test1", on date "2025-05-15", hours spent "7.5", and description "Implemented navigation bar and fixed layout issues"
     Then the time registration should not be saved to SystemStorage
     And  the system should display an error message "Project with activity ID A002test1 does not exist"

   Scenario: Fail to register time when no user is logged in
     Given No user is logged in
     And a project "Website Redesign" with ID "25001" and deadline "2025-06-12" exists
     And an activity "Frontend Development" with ID "A001", in project with id "25001" exists
     And an activity "Frontend Development" with ID "A001" belongs to project "25001"
     When a user registers time spent on activity with Id "A001", on date "2025-05-15", hours spent "7.5", and description "Implemented navigation bar and fixed layout issues"
     Then the time registration should not be saved to SystemStorage
     And  the system should display an error message "No user is logged in"


   Scenario: Fail to register time invalid dateformat
     Given The employee with ID "valid_employee1" is logged in
     And a project "Website Redesign" with ID "25001" and deadline "2025-06-12" exists
     And an activity "Frontend Development" with ID "A001", in project with id "25001" exists
     And an activity "Frontend Development" with ID "A001" belongs to project "25001"
     And the employee with ID "valid_employee1" is assigned to activity with "A001"
     When a user registers time spent on activity with Id "A001", on date "202-05-15", hours spent "7.5", and description "Implemented navigation bar and fixed layout issues"
     Then the time registration should not be saved to SystemStorage
     And the system should display an error message "Invalid date format. Use yyyy-MM-dd"

   Scenario: Fail to register time invalid dateformat
     Given The employee with ID "valid_employee1" is logged in
     And a project "Website Redesign" with ID "25001" and deadline "2025-06-12" exists
     And an activity "Frontend Development" with ID "A001", in project with id "25001" exists
     And an activity "Frontend Development" with ID "A001" belongs to project "25001"
     And the employee with ID "valid_employee1" is assigned to activity with "A001"
     When a user registers time spent on activity with Id "A001", on date "", hours spent "7.5", and description "Implemented navigation bar and fixed layout issues"
     Then the time registration should not be saved to SystemStorage
     And the system should display an error message "Invalid date format. Use yyyy-MM-dd"


   Scenario: Fail to register time if employee is not assigned to activity
     Given The employee with ID "valid_employee2" is logged in
     And a project "Website Redesign" with ID "25001" and deadline "2025-06-12" exists
     And an activity "Frontend Development" with ID "A001", in project with id "25001" exists
     And an activity "Frontend Development" with ID "A001" belongs to project "25001"
     And the employee with ID "valid_employee2" is not assigned to activity with "A001"
     When a user registers time spent on activity with Id "A001", on date "2025-05-15", hours spent "7.5", and description "Implemented navigation bar and fixed layout issues"
     Then the time registration should not be saved to SystemStorage
     And the system should display an error message "You are not assigned to this activity"


     # scenarios for editing time registration
    Scenario: Successfully edit time registration
        Given The employee with ID "valid_employee1" is logged in
        And a project "Website Redesign" with ID "25001" and deadline "2025-06-12" exists
        And an activity "Frontend Development" with ID "A001", in project with id "25001" exists
        And an activity "Frontend Development" with ID "A001" belongs to project "25001"
        And the employee with ID "valid_employee1" is assigned to activity with "A001"
        And a time registration for activity with Id "A001", on date "2025-05-15", hours spent "7.5", and description "Implemented navigation bar and fixed layout issues" exists
        When a user edits the time registration for activity with Id "A001", on date "2025-05-15", hours spent "8.0", and description "Updated description"
        Then the latest time registration should have employee "valid_employee1"
        Then the latest time registration should be for activity "A001"
        Then the latest time registration should have date "2025-05-15"
        Then the latest time registration should have "8.0" hours spent
        Then the latest time registration should have description "Updated description"

    Scenario: Fail to edit time registration with negative hours
        Given The employee with ID "valid_employee1" is logged in
        And a project "Website Redesign" with ID "25001" and deadline "2025-06-12" exists
        And an activity "Frontend Development" with ID "A001", in project with id "25001" exists
        And an activity "Frontend Development" with ID "A001" belongs to project "25001"
        And the employee with ID "valid_employee1" is assigned to activity with "A001"
        And a time registration for activity with Id "A001", on date "2025-05-15", hours spent "7.5", and description "Implemented navigation bar and fixed layout issues" exists
        When a user edits the time registration for activity with Id "A001", on date "2025-05-15", hours spent "-8.0", and description "Updated description"
        Then the system should display an error message "Invalid hours format, must be between 0 and 24"

    Scenario: Fail to edit time registration with zero hours
        Given The employee with ID "valid_employee1" is logged in
        And a project "Website Redesign" with ID "25001" and deadline "2025-06-12" exists
        And an activity "Frontend Development" with ID "A001", in project with id "25001" exists
        And an activity "Frontend Development" with ID "A001" belongs to project "25001"
        And the employee with ID "valid_employee1" is assigned to activity with "A001"
        And a time registration for activity with Id "A001", on date "2025-05-15", hours spent "7.5", and description "Implemented navigation bar and fixed layout issues" exists
        When a user edits the time registration for activity with Id "A001", on date "2025-05-15", hours spent "0", and description "Updated description"
        Then the system should display an error message "Invalid hours format, must be between 0 and 24"

    Scenario: Fail to edit time registration with more than 24 hours
        Given The employee with ID "valid_employee1" is logged in
        And a project "Website Redesign" with ID "25001" and deadline "2025-06-12" exists
        And an activity "Frontend Development" with ID "A001", in project with id "25001" exists
        And an activity "Frontend Development" with ID "A001" belongs to project "25001"
        And the employee with ID "valid_employee1" is assigned to activity with "A001"
        And a time registration for activity with Id "A001", on date "2025-05-15", hours spent "7.5", and description "Implemented navigation bar and fixed layout issues" exists
        When a user edits the time registration for activity with Id "A001", on date "2025-05-15", hours spent "24.1", and description "Updated description"
        Then the system should display an error message "Invalid hours format, must be between 0 and 24"

      Scenario: Fail to edit time registration with empty description
        Given The employee with ID "valid_employee1" is logged in
        And a project "Website Redesign" with ID "25001" and deadline "2025-06-12" exists
        And an activity "Frontend Development" with ID "A001", in project with id "25001" exists
        And an activity "Frontend Development" with ID "A001" belongs to project "25001"
        And the employee with ID "valid_employee1" is assigned to activity with "A001"
        And a time registration for activity with Id "A001", on date "2025-05-15", hours spent "7.5", and description "Implemented navigation bar and fixed layout issues" exists
        When a user edits the time registration for activity with Id "A001", on date "2025-05-15", hours spent "8.0", and description ""
        Then the system should display an error message "Description cannot be null or empty"


      # scenarios for deleting time registration
    Scenario: Successfully delete time registration
        Given The employee with ID "valid_employee1" is logged in
        And a project "Website Redesign" with ID "25001" and deadline "2025-06-12" exists
        And an activity "Frontend Development" with ID "A001", in project with id "25001" exists
        And an activity "Frontend Development" with ID "A001" belongs to project "25001"
        And the employee with ID "valid_employee1" is assigned to activity with "A001"
        And a time registration for activity with Id "A001", on date "2025-05-15", hours spent "7.5", and description "Implemented navigation bar and fixed layout issues" exists
        When a user deletes the time registration for activity with Id "A001", on date "2025-05-15"
        Then the time registration should be deleted from SystemStorage

    Scenario: Fail to delete time registration when no time registration with id exists
        Given The employee with ID "valid_employee1" is logged in
        And a project "Website Redesign" with ID "25001" and deadline "2025-06-12" exists
        And an activity "Frontend Development" with ID "A001", in project with id "25001" exists
        And an activity "Frontend Development" with ID "A001" belongs to project "25001"
        And the employee with ID "valid_employee1" is assigned to activity with "A001"
        When a user deletes the time registration for activity with Id "A001", on date "2025-05-15"
        Then the time registration should not be deleted from SystemStorage
        And the system should display an error message "Time registration ID does not exist."


