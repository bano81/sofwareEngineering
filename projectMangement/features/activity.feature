Feature: Activity Management

  Background:
    Given a user with ID "bano" exists
    And the user with ID "bano" is logged in
    And a project named "NewProject" with id "25001" and deadline "25-09" exists
    And an activity named "NewActivity" exists in project with ID "25001"

  Scenario: Create a new activity
    Then the list of activities in the project with ID "25001" should include "NewActivity"

  Scenario: Adding existing activity to existing employee
    When the user adds the employee with ID "bano" to the activity "NewActivity" from project with ID "25001"
    Then the activity "NewActivity" from project with ID "25001" should have the employee with ID "bano" assigned

  Scenario: successfully assign an activity to an employee
    And a user with ID "huba" exists
    When the logged in employee assigns the activity with name "NewActivity" from project with ID "25001" to the employee with ID "huba"
    Then the activity "NewActivity" from project with ID "25001" should be assigned to the employee with ID "huba"

  Scenario: Edit all attributes of an activity
    When the user edits the activity "NewActivity" from project with ID "25001" with the following details:
      | attribute     | value           |
      | name          | UpdatedActivity |
      | startWeek     |           25-02 |
      | endWeek       |           25-05 |
      | budgetedHours |              50 |
    Then the activity "UpdatedActivity" from project with ID "25001" should have the following details:
      | attribute     | value           |
      | name          | UpdatedActivity |
      | startWeek     |           25-02 |
      | endWeek       |           25-05 |
      | budgetedHours |              50 |

  Scenario: Remove an employee from an activity
    When the user adds the employee with ID "bano" to the activity "NewActivity" from project with ID "25001"
    And the user removes the employee with ID "bano" from the activity "NewActivity" from project with ID "25001"
    Then the activity "NewActivity" from project with ID "25001" should not have the employee with ID "bano" assigned

  Scenario: Track spent hours on an activity
    When the user records 5.5 hours spent on activity "NewActivity" from project with ID "25001"
    Then the activity "NewActivity" from project with ID "25001" should have 5.5 hours spent
    When the user records 2.5 more hours spent on activity "NewActivity" from project with ID "25001"
    Then the activity "NewActivity" from project with ID "25001" should have 8.0 hours spent

  Scenario: Verify unique activity IDs
    And an activity named "NewActivity2" is created for the project with ID "25001"
    Then the activities "NewActivity" and "NewActivity2" from project with ID "25001" should have different IDs
