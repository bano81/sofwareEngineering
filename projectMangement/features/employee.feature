Feature: Create and manage employees

	Scenario: Create employee
		Given a user with ID "bano" exists
		And the user with ID "bano" is logged in
		When the user creates an employee with name "Hubert", surname "Baumeister" and ID "huba"
		Then a user with id "huba" is added to the list of employees

	Scenario: Create an employee with used ID
		Given a user with ID "huba" exists
		When the user creates an employee with name "Hubert", surname "Baumeister" and ID "huba"
		Then the system will return the error "Employee with ID huba already exists."

# 	Scenario: Adding activity to existing employee
# 		When an employee with ID "huba" add activity with ID "ana1", name "analysis", start date "2025-04-21", end date "2025-04-23", budget hours 3 and status "In progross"
# 		Then show the list of activities for the employee with ID "huba"

# 	Scenario: Display number of activity for an existing employee
# 		Given an employee with ID "huba" exits
# 		When the user select the employee with ID "huba"
# 		Then the system should display the number of activities that are not finished and the list of activites for the employee with ID "huba"

# 	Scenario: Display available employees
#		Given a project manager exists
#		And a project manager is logged in
#		And an employee with ID "huba" exists
#		And an employee with ID "bano" exists
#		When the project manager requests the list of available employees
#		Then the system should display the list of available employees