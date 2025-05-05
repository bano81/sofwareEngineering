Feature: Activity Management

    Scenario: Create a new activity
        Given a user with ID "bano" exists
        And the user with ID "bano" is logged in
        And a project named "NewProject" with id "25001" exists
        When an activity named "NewActivity" is created for the project with ID "25001"
        Then the list of activities in the project with ID "25001" should include "NewActivity"

    Scenario: Adding existing activity to existing employee
        Given a user with ID "bano" exists
        And the user with ID "bano" is logged in
        And a project named "NewProject" with ID "25001" exists
        And an activity named "NewActivity" exists in project with ID "25001"
        When the user adds the employee with ID "bano" to the activity "NewActivity" from project with ID "25001"
        Then the activity "NewActivity" from project with ID "25001" should have the employee with ID "bano" assigned