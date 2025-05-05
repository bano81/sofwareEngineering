# Feature: Create and manage projects
#     As a user
#     I want to create and manage projects
#     So that I can organize my work effectively

#     Scenario: Create a new project
#         Given a user with id "huba" exists
#         And the user "huba" is logged in
#         When the user tries to create a new project with name "NewProject" and id "25001"
#         Then the project "NewProject" is created with id "25001"

#     Scenario: Assign existing user as project manager
#         Given a user with id "huba" exists
#         And the user "huba" is logged in
#         And a project named "NewProject" with id "25001" exists
#         When "huba" is assigned as project manager for the project with Id "25001"
#         Then "huba" should be listed as project manager for the project with Id "25001"
