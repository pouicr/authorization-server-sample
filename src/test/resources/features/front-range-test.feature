Feature: Login test


  Background:
    Given a global administrator named "Greg"
    And a blog named "Greg's anti-tax rants"
    And a customer named "Dr. Bill"
    And a blog named "Expensive Therapy" owned by "Dr. Bill"

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