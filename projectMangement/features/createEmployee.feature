Feature: Create employee

Scenario: Create employee with a name, surname and ID
	When an employee is created with name "Hubert", surname "Baumeister" and ID "huba"	
	Then show the list of employees contains an employee with that name, surname and ID
	
Scenario: Create an employee with used ID
	Given emplyee with name "Baqer", surname "Nour" and ID "bano" exists
	When an employee is created with name "Noah", surname "Johnsen" and a used ID "bano"
	Then the system will return an error message.
	
Scenario: Adding activity to existing employee
	When an employee with ID "huba" add activity with ID "ana1", name "analysis", start date "2025-04-21", end date "2025-04-23", budget hours 3 and status "in progross"
	Then show the list of activities for the employee with ID "huba"