Feature: White Box test for getNumberOfWeekActivityForEmployee

  Scenario: count number of activities for a user with ID "obaa"
    Given a user with name "nametest2", surname "surnametest2" and ID "obaa" exists
    And the user "obaa" is logged in
    And a project named "BaqerTest" with id "30000" exists
    When the user tests ID "obaa" to count the number of aktivities in project with ID "30000" at week "25-01"
    Then the number of activities for the ID "obaa" will be 0
    
  Scenario: count number of activities for a user with ID "bano"
    And a user with ID "bano" does not exists
    And a project named "BaqerTest" with id "30000" exists
    When the user tests ID "bano" to count the number of aktivities in project with ID "30000" at week "25-01"
    Then the number of activities for the ID "bano" will be 0
    
  Scenario: count number of activities for a user with ID "huba"
  Given a user with name "nametest", surname "surnametest" and ID "huba" exists
  And a project named "BaqerTest" with id "30000" exists
  And the user with ID "huba" is assigned to activity "a1" to project with id "30000"
  And the activity "a1" from project with ID "30000" with the following details exits:
    | attribute | value           |
    | name      | UpdatedActivity |
    | startWeek | 25-03       		|
    | endWeek   | 25-06     		  |
  When the user tests ID "huba" to count the number of aktivities in project with ID "30000" at week "24-04"
  Then the number of activities for the ID "huba" will be 0
  
  Scenario: count number of activities for a user with ID "huba"
    When the user tests ID "huba" to count the number of aktivities in project with ID "30000" at week "25-02"
    Then the number of activities for the ID "obaa" will be 0
    
  Scenario: count number of activities for a user with ID "huba"
    When the user tests ID "huba" to count the number of aktivities in project with ID "30000" at week "22-02"
    Then the number of activities for the ID "obaa" will be 0
  
  Scenario: count number of activities for a user with ID "huba"
    When the user tests ID "huba" to count the number of aktivities in project with ID "30000" at week "26-03"
    Then the number of activities for the ID "obaa" will be 0
    
  Scenario: count number of activities for a user with ID "huba"
    When the user tests ID "huba" to count the number of aktivities in project with ID "30000" at week "25-07"
    Then the number of activities for the ID "obaa" will be 0
    
  Scenario: count number of activities for a user with ID "huba"
    When the user tests ID "huba" to count the number of aktivities in project with ID "30000" at week "26-07"
    Then the number of activities for the ID "obaa" will be 0
    
  Scenario: count number of activities for a user with ID "huba"
    When the user tests ID "huba" to count the number of aktivities in project with ID "30000" at week "24-07"
    Then the number of activities for the ID "obaa" will be 0
    
  Scenario: count number of activities for a user with ID "huba"
    When the user tests ID "huba" to count the number of aktivities in project with ID "30000" at week "26-02"
    Then the number of activities for the ID "obaa" will be 0
    
  Scenario: count number of activities for a user with ID "huba"
    When the user tests ID "huba" to count the number of aktivities in project with ID "30000" at week "25-04"
    Then the number of activities for the ID "obaa" will be 1
    
  Scenario: count number of activities for a user with ID "huba"
  Given the user with ID "huba" is assigned to activity "a2" to project with id "30000"
  And the activity "a2" from project with ID "30000" with the following details exits:
    | attribute | value           |
    | name      | UpdatedActivity2|
    | startWeek | 25-51           |
    | endWeek   | 26-08           |
  When the user tests ID "huba" to count the number of aktivities in project with ID "30000" at week "26-04"
  Then the number of activities for the ID "huba" will be 1
  