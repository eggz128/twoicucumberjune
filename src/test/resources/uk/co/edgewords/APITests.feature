@API
Feature: API Demo
  Scenario: Get all products
    When I request all products
    Then I get a status code of 200