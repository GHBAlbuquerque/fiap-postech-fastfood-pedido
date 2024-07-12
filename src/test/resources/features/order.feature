Feature: Order

  Scenario: Create Order
    Given a new valid order request
    When has an item list with valid products
    Then a new order is succesfully created

  Scenario: Fail Order Creation
    Given a new invalid order request
    When has an item with non-existant product
    Then an error is throwed
