# Feature: Login/Logout
#   As a user
#   I want to login and logout of the system
#   So that I can access my account and secure my information

#   Scenario: Successful login
#     Given An employee with ID "huba" exists
#     When I login with ID "huba"
#     Then The employee with ID "huba" should be logged in
#     And I logout

#   Scenario: Successful login with a valid employee ID
#     Given An employee with ID "valid_employee" exists
#     When I login with ID "valid_employee"
#     Then The employee with ID "valid_employee" should be logged in
#     And I logout

#   Scenario: Login with an invalid employee ID
#     Given No employee with ID "invalid_employee" exists
#     When I login with ID "invalid_employee"
#     Then The login attempt should fail

#   Scenario: Login with an empty employee ID
#     When I login with ID ""
#     Then The login attempt should fail

#   Scenario: Login of other user while already logged in
#     Given An employee with ID "huba" exists
#     And An employee with ID "other_user" exists
#     When I login with ID "huba"
#     And I login with ID "other_user"
#     Then The employee with ID "huba" should be logged in
#     And The employee with ID "other_user" should not be logged in
#     And I logout

#   Scenario: Logout
#     Given An employee with ID "huba" exists
#     When I login with ID "huba"
#     And I logout
#     Then There should be no logged-in employee