Feature: Create and manage projects
    As a user
    I want to create and manage projects
    So that I can organize my work effectively

    Scenario: Create a new project
        Given an admin user exists
        And the admin user is logged in
        When the user creates a new project with name "NewProject"
        Then the project "NewProject" is created with a unique ID

    Scenario: Create a new activity
        Given a user exists
        And the user is logged in
        And a project named "NewProject" exists
        And the user is assigned as project manager for "NewProject"
        And an activity named "NewActivity" is created for project "NewProject"
        When the activity is added to the project
        Then the list of activities in "NewProject" should include "NewActivity"
