Feature: Navigation Bar
  As a user
  I want to use the navigation bar
  So that I can quickly access different product categories

  Background:
    Given I am on the home page

  Scenario: Navigation bar is visible on all pages
    When I navigate to any website page
    Then I see the navigation menu bar at the top of the page

  Scenario Outline: Dropdown menu appears for each category on hover
    When I hover over the "<category>" in the navigation menu
    Then I see a dropdown menu with subcategories for "<category>"

    Examples:
      | category          |
      | Books             |
      | Computers         |
      | Electronics       |
      | Apparel & Shoes   |
      | Digital downloads |
      | Jewelry           |
      | Gift Cards        |

  Scenario Outline: First-level menu item redirects to correct category page
      When I click on the "<category>" category in the navigation menu
      Then I am redirected to the "<categoryUrl>" category page

    Examples:
      | category          | categoryUrl          |
      | Books             | /books               |
      | Computers         | /computers           |
      | Electronics       | /electronics         |
      | Apparel & Shoes   | /apparel-shoes       |
      | Digital downloads | /digital-downloads   |
      | Jewelry           | /jewelry             |
      | Gift Cards        | /gift-cards          |

  Scenario Outline: Second-level menu item redirects to correct subcategory page
    When I hover over the "<category>" in the navigation menu
    And I click on the "<subcategory>" subcategory in the dropdown menu
    Then I am redirected to the "<subcategoryUrl>" subcategory page

    Examples:
      | category   | subcategory | subcategoryUrl  |
      | Computers  | Desktops    | /desktops       |
      | Computers  | Notebooks   | /notebooks      |
      | Computers  | Accessories | /accessories    |
      | Electronics| Camera, photo | /camera-photo |
      | Electronics| Cell phones | /cell-phones    |


  Scenario Outline: Dropdown closes when cursor leaves the menu area
    When I hover over the "<category>" in the navigation menu
    Then I see a dropdown menu with subcategories for "<category>"
    When I move the cursor outside the dropdown menu area
    Then I do not see a dropdown menu with subcategories for "<category>"

    Examples:
      | category    |
      | Computers   |
      | Electronics |

  Scenario Outline: First-level menu item is highlighted on hover
    When I hover over the "<category>" in the navigation menu
    Then the "<category>" first-level menu item is highlighted

    Examples:
      | category    |
      | Books       |
      | Computers   |
      | Electronics |

  Scenario Outline: Second-level menu item is highlighted on hover
    When I hover over the "<category>" in the navigation menu
    And I move the cursor over the "<subcategory>" subcategory in the dropdown menu
    Then the "<subcategory>" second-level menu item is highlighted

    Examples:
      | category    | subcategory |
      | Computers   | Desktops    |
      | Computers   | Notebooks   |
      | Computers   | Accessories |
      | Electronics | Camera, photo |
      | Electronics | Cell phones   |
