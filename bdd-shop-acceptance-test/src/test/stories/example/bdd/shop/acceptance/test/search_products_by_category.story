A user should be able to search all the products in a category

Narative:
In order to find the products I need in an easy way
As a user
I want to be able to search the products by category

Scenario: Searching all the products in a category
Meta:
@main_path
@happy_path

Given the categories "Peripheral devices" and "Connectors" are defined
And the following products exist in the system:
|Name             |Specifications                         |Category          | 
|USB 3.0 cable    |Length:3 m                             |Connectors        |
|Wireless Mouse   |Dimensions:2.6x1.6x4 inches            |Peripheral devices|
|Wireless Keyboard|Dimensions:20.6x6.2x2 inches; OS: Win 8|Peripheral devices|
|HDMI cable       |Length:4 m                             |Connectors        |
When a user searches all the products in the "Peripheral devices" category
Then the following products are returned: 
|Name             |Specifications                         |Category          |
|Wireless Mouse   |Dimensions:2.6x1.6x4 inches            |Peripheral devices|
|Wireless Keyboard|Dimensions:20.6x6.2x2 inches; OS: Win 8|Peripheral devices|