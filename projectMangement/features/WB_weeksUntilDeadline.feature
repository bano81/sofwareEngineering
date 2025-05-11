#Feature: White Box test for getWeeksUntilDeadline
#
#  Background:
#    Given a project with ID "30000" exists
#
#  Scenario: Deadline is null
#    Given the project with ID "30000" has deadline null
#    When the user checks the number of weeks until deadline for project with ID "30000"
#    Then the result should be -1
#
#  Scenario: Deadline is empty string
#    Given the project with ID "30000" has deadline ""
#    When the user checks the number of weeks until deadline for project with ID "30000"
#    Then the result should be -1
#
#  Scenario: Deadline string is not parseable (wrong format)
#    Given the project with ID "30000" has deadline "invalid-date"
#    When the user checks the number of weeks until deadline for project with ID "30000"
#    Then the result should be -1
#
#  Scenario: Deadline string has invalid number format
#    Given the project with ID "30000" has deadline "xx-yy"
#    When the user checks the number of weeks until deadline for project with ID "30000"
#    Then the result should be -1
#
#  Scenario: Deadline is same as current week
#    Given the current date is year 2025 and week 19
#    And the project with ID "30000" has deadline "25-20"
#    When the user checks the number of weeks until deadline for project with ID "30000"
#    Then the result should be 1
#
#  Scenario: Deadline is in the future
#    Given the current date is year 2025 and week 19
#    And the project with ID "30000" has deadline "25-25"
#    When the user checks the number of weeks until deadline for project with ID "30000"
#    Then the result should be 6
#
#  Scenario: Deadline is in the past
#    Given the current date is year 2025 and week 19
#    And the project with ID "30000" has deadline "24-50"
#    When the user checks the number of weeks until deadline for project with ID "30000"
#    Then the result should be -21