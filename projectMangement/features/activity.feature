Feature: Activity Management
    Scenario: Adding activity to existing employee
        Given a user with ID "bano" exists
        And a project named "NewProject" with ID "25001" exists
        And an activity named "NewActivity" exists in project with ID "25001"
        When the user adds the activity "NewActivity" from project with ID "25001" to the employee with ID "bano"
        Then the employee with ID "bano" should have the activity "NewActivity" from project with ID "25001" assigned

    # Scenario: Create a new activity
    #     Given a user with id "huba" exists
    #     And the user "huba" is logged in
    #     And a project named "NewProject" with id "25001" exists
    #     And "huba" is assigned as project manager for the project with id "25001"
    #     When an activity named "NewActivity" is created for the project with id "25001"
    #     Then the list of activities in the project with id "25001" should include "NewActivity"