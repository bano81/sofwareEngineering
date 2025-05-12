# Feature: White Box test for getCurrentSpentHours

#   Background:
#     Given a project with name "Ninja Project" and ID "30000" exists
#     And an activity with name "Ninja activity" and ID "A1" exists
#     And an employee named "Silas" "Kejser" exists

#   Scenario: Empty list of time registrations
#     When the user checks the current spent hours with an empty list
#     Then the result should be 0.0

#   Scenario: Time registration has null activity
#     Given the time registrations contain one entry with null activity
#     When the user checks the current spent hours
#     Then the result should be 0.0

#   Scenario: Time registration has different activity ID
#     Given the time registrations contain one entry with activity ID "B2"
#     When the user checks the current spent hours
#     Then the result should be 0.0

#   Scenario: Two matching registrations with hours
#     Given the time registrations contain two entries with activity ID "A1" and hours 2.0 and 3.5
#     When the user checks the current spent hours
#     Then the result should be 5.5

#   Scenario: Mixed valid and invalid entries
#     Given the time registrations contain three entries: one with null activity, one with ID "B2", and one with ID "A1" and 4.0 hours
#     When the user checks the current spent hours
#     Then the result should be 4.0
