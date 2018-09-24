Narative: An admin user should be able to create a new product by specifying its category.

In order to manage the products in the system
As an admin user
I want to be able to create a new product

Scenario: An admin user should be able to create a new product

Given John is an admin user
And the category 'Laptops' is defined
When John adds a new product with the following details:
name        |specifications   |category
Acer Aspire |Intel Core i5    |Laptops
Then the product is added successfully

Scenario: A product cannot be added without specifying a category

Given John is an admin user
When John adds a new product with the following details:
name        |specifications   |category
Acer Aspire |                 |
Then the request is rejected