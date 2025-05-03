Feature: Create and manage projects
    As a user
    I want to create and manage projects
    So that I can organize my work effectively

    Scenario: Create a new project with admin logged in
        Given an admin user exists
        And an admin user is logged in
        When the user tries to create a new project with name "NewProject"
        Then the project "NewProject" is created with a unique ID
        And I logout
    
    Scenario: Fail to create project with normal user logged in
        Given a user with id "huba" exists
        And the user "huba" is logged in
        When the user tries to create a new project with name "NewProject"
        Then the system should return the error message "Insufficient permissions to create a project"
        And I logout
    
    Scenario: Assign existing user as project manager
        Given a user with id "huba" exists
        And an admin user exists
        And an admin user is logged in
        And a project named "NewProject" exists
        When the admin assigns "huba" as project manager for "NewProject"
        Then "huba" should be listed as project manager for "NewProject"
        And I logout

    Scenario: Create a new activity
        Given a user with id "huba" exists
        And an admin user exists
        And an admin user is logged in
        And a project named "NewProject" exists
        And the admin assigns "huba" as project manager for "NewProject"
        When an activity named "NewActivity" is created for project "NewProject"
        Then the list of activities in "NewProject" should include "NewActivity"
        And I logout
