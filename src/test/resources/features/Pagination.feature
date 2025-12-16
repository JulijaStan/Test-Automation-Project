Feature: Pagination on category pages
  As a user
  I want to use the pagination component on category pages
  So that I can navigate through all available items

  Background:
    Given I am on a category page that contains more than 4 items
    And I select "4" items per page in the "Display" dropdown

# Pagination Visibility

  Scenario: Pagination appears after selecting 4 items per page
    Then I see pagination component

  Scenario: Page numbers are visible in the pagination component
    Given I see pagination component
    Then page number buttons are visible

  Scenario: Next button is visible when not on the last page
    Given I see pagination component
    And I am on a non-last page
    Then the Next button is visible

  Scenario: Previous button is visible when not on the first page
    Given I see pagination component
    And I am on page 2
    Then the Previous button is visible


# Next button

  Scenario: Next button redirects to the correct next page
    Given I see pagination component
    And I am on page N where N is less than the last page
    When I click the Next button
    Then page N+1 is displayed
    And items from page N are no longer visible
    And items from page N+1 are visible

  Scenario: Next button is disabled on the last page
    Given I see pagination component
    And I am on the last page
    Then the Next button is disabled

# Direct page navigation

  Scenario Outline: Clicking page N opens page N
    Given I see pagination component
    When I click on page <pageNumber> in the pagination component
    Then page <pageNumber> is displayed
    And the content is updated according to page <pageNumber>

    Examples:
      | pageNumber |
      | 1          |
      | 2          |

# Previous button

  Scenario: Previous button redirects to the correct previous page
    Given I see pagination component
    And I am on page N where N is greater than 1
    When I click the Previous button
    Then page N-1 is displayed
    And items from page N are no longer visible
    And items from page N-1 are visible

  Scenario: Previous button is disabled on the first page
    Given I see pagination component
    And I am on page 1
    Then the Previous button is disabled