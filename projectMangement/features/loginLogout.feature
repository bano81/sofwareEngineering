Feature: Login/Logout
  As a user
  I want to login and logout of the system
  So that I can access my account and secure my information

  Scenario: Successful login
    Given An employee with ID "huba" exists
    When I login with ID "huba"
    Then The employee with ID "huba" should be logged in