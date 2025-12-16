package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import selenium.pages.NavBarPage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NavBarSteps {
    private final NavBarPage navBarPage;

    public NavBarSteps() {
        WebDriver driver = Hooks.driver;
        navBarPage = new NavBarPage(driver);
    }

    @Given("I am on the home page")
    public void iAmOnTheHomePage() {
        navBarPage.openHomePage();
    }

    //NavBar Visibility

    @When("I navigate to any website page")
    public void iNavigateToAnyWebsitePage() {
        navBarPage.navigateToAnyPage();
    }

    @Then("I see the navigation menu bar at the top of the page")
    public void iSeeTheNavigationMenuBarAtTheTopOfThePage() {
        assertTrue(navBarPage.isNavigationBarVisible());
    }

    //Dropdown appears on hover

    @When("I hover over the {string} in the navigation menu")
    public void iHoverOverTheCategoryInTheNavigationMenu(String categoryName) {
        navBarPage.hoverOverCategory(categoryName);
    }

    @Then("I see a dropdown menu with subcategories for {string}")
    public void iSeeADropdownMenuWithSubcategories(String categoryName) {
        assertTrue(navBarPage.isDropdownVisibleFor(categoryName));
    }

    //Redirections to category and subcategory pages

    @When("I click on the {string} category in the navigation menu")
    public void iClickOnTheCategoryInTheNavigationMenu(String categoryName) {
        navBarPage.clickCategory(categoryName);
    }

    @Then("I am redirected to the {string} category page")
    public void iAmRedirectedToTheCategoryPage(String categoryUrl) {
        assertTrue(
                navBarPage.isOnCategoryPage(categoryUrl),
                "User is not on expected category page: " + categoryUrl
        );
    }

    @And("I click on the {string} subcategory in the dropdown menu")
    public void iClickOnTheSubcategoryInTheDropdownMenu(String subcategoryName) {
        navBarPage.clickSubcategory(subcategoryName);

    }

    @Then("I am redirected to the {string} subcategory page")
    public void iAmRedirectedToTheSubcategoryPage(String subcategoryUrl) {
        assertTrue(
                navBarPage.isOnSubcategoryPage(subcategoryUrl),
                "User is not on expected subcategory page: " + subcategoryUrl
        );
    }

    //Dropdown disappears on moving cursor away and highlighting menu items

    @When("I move the cursor outside the dropdown menu area")
    public void iMoveTheCursorOutsideTheDropdownMenuArea() {
        navBarPage.moveCursorOutsideDropdown();
    }

    @Then("I do not see a dropdown menu with subcategories for {string}")
    public void iDoNotSeeADropdownMenuForCategory(String categoryName) {
        assertFalse(
                navBarPage.isDropdownVisibleFor(categoryName),
                "Dropdown should NOT be visible for category: " + categoryName
        );
    }

    @Then("the {string} first-level menu item is highlighted")
    public void theFirstLevelMenuItemIsHighlighted(String categoryName) {
        assertTrue(
                navBarPage.isCategoryHighlighted(categoryName),
                "First-level category should be highlighted: " + categoryName
        );
    }

    @When("I move the cursor over the {string} subcategory in the dropdown menu")
    public void iMoveTheCursorOverTheSubcategory(String subcategoryName) {
        navBarPage.hoverOverSubcategory(subcategoryName);
    }

    @Then("the {string} second-level menu item is highlighted")
    public void theSecondLevelMenuItemIsHighlighted(String subcategoryName) {
        assertTrue(
                navBarPage.isSubcategoryHighlighted(subcategoryName),
                "Second-level subcategory should be highlighted: " + subcategoryName
        );
    }

}