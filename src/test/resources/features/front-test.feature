Feature: Login test

  Scenario: Good login

    Given I visit login page
    And I type "pouic" as login
    And "mdp" as password
    When I click on the login button
    Then I should be login


  Scenario: bad login

    Given I visit login page
    And I type "BAD" as login
    And "mdp" as password
    When I click on the login button
    Then I should not be login