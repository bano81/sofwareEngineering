Feature: WhiteBox test for login

Scenario: invalid employee id
    Given no user with id "huba" exists in the system
    When the user with id "huba" tries to login
    Then the system throws an error message "Employee with ID huba does not exist."

Scenario: login when already logged in
    Given a user with id "huba" exists in the system
    And the user with id "huba" is logged in
    When the user with id "huba" tries to login
    Then the system throws an error message "You are already logged in."

Scenario: Login when other user already logged in
    Given a user with id "mama" exists in the system
    And the user with id "mama" is logged in
    And a user with id "huba" exists in the system
    When the user with id "huba" tries to login
    Then the system throws an error message "Another employee is already logged in."

Scenario: Successful login
    Given a user with id "huba" exists in the system
    And no user is logged in
    When the user with id "huba" tries to login
    Then then the user with id "huba" is logged in