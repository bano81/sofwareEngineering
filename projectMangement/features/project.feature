Feature: Create and manage projects
    As a user
    I want to create and manage projects
    So that I can organize my work effectively

Scenario: Create a new project
    Given A user inputs "2" to the terminal
    And A user inputs "NewProject" to the terminal
    And A user inputs "0" to the terminal
    When The input is processed
    And the program is run
    Then the list of projects should include "NewProject"

# Scenario: Add an activity to a project
#     Given A user inputs "2" to the terminal
#     And A user inputs "NewProject" to the terminal
#     And A user inputs "3" to the terminal
#     And A user inputs "NewActivity" to the terminal
#     And A user inputs "0" to the terminal
#     When The input is processed
#     And the program is run
#     Then the list of activities in "NewProject" should include "NewActivity"