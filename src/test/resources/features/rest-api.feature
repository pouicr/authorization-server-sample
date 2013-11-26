Feature: Access test

  Scenario: ping the server

    Given I create a "ping" request
    When I send the request
    Then I should get "pong"