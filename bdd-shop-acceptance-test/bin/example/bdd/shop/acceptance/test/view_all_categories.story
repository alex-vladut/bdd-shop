Scenario: A user is able to view all the available product categories

Given the following categories have been defined:
name        |description                                                           |
Smartphones |All the modern mobile devices capable of computer-like functionalities|
PC          |All the computers,laptops and additional components
Books       |All the books and magazines
When an anonymous user searches for all available categories
Then a list of all categories is displayed