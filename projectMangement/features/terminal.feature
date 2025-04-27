Feature: Testing for viewing output given an input in the terminal
  As a user
  I want to see the output of my input in the terminal
  So that I can verify the functionality of the terminal

  Scenario: Viewing output given an input in the terminal
    Given We examine the output of the terminal
    And A user inputs "Hello World!" to the terminal
    And The input is processed
    When The input is output to the terminal
    Then I should see "Hello World!" in the terminal output
    And We leave the terminal