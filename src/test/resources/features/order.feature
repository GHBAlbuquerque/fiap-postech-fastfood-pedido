Feature: Order

  # é necessário possuir produto e cliente cadastrado para funcionar
  Scenario: Create Order
    Given a new valid order request with valid products
    When a new order is succesfully created
    Then an order id is received

  Scenario: Fail Order Creation
    Given a new invalid order request with non-existant product
    When order creation fails
    Then an error is throwed with a message
