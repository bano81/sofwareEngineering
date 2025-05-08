# Created by augusthansen at 07/05/2025
Feature: A project manager / employee should be able to assign activities to other employees and themselves if they ar employees
  # Enter feature description here

  Scenario: successfully assign an activity to an employee
    Given The employee with ID "valid_employee1" is logged in
    And an employee with ID "huba" exists
    And a project named "NewProject" with id "25001" and deadline "25-09-22" exists
    And an activity "Frontend Development" with ID "A001", in project with id "25001" exists
    When the logged in employee assigns the activity with ID "A0012" from project with ID "25001" to the employee with ID "huba"
    Then the activity "A0012" from project with ID "25001" should be assigned to the employee with ID "huba"