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