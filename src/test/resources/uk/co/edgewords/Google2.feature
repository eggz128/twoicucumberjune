@GUI
Feature: Google search2

  Scenario: Search Google for Edgewords

    Given i am on Google
    When I search for "edgewords"
    Then "Edgewords" is the top result


  Scenario Outline: Searching for stuff
    Given i am on Google
    When I search for "<searchTerm>"
    Then "<searchTerm>" is the top result
    Examples:
      | searchTerm |
      | edgewords  |
      | bbc        |

  Scenario: Using inline tables
    Given I am on Google
    When I search for "edgewords"
    Then I see in the results
      | url                                 | description                                                         |
      | https://www.edgewordstraining.co.uk | Edgewords Training - Automated Software Testing Training            |
      | https://twitter.com › edgewords     | https://edgewordstraining.co.uk Edgewords delivers training courses |
      | github.com                          | Edgewords provides training courses in                              |

