Feature: Login/Logout
  As a user
  I want to login and logout of the system
  So that I can access my account and secure my information

  Scenario: Successful login
    Given An employee with ID "huba" exists
    When I login with ID "huba"
    Then The employee with ID "huba" should be logged in

  Scenario: Successful login with a valid employee ID
    Given An employee with ID "valid_employee" exists
    When I login with ID "valid_employee"
    Then The employee with ID "valid_employee" should be logged in

  Scenario: Login with an invalid employee ID
    Given No employee with ID "invalid_employee" exists
    When I login with ID "invalid_employee"
    Then The login attempt should fail

  Scenario: Login with an empty employee ID
    When I login with ID ""
    Then The login attempt should fail