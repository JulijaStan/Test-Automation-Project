package stepDefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import selenium.pages.PaginationPage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static stepDefinitions.Hooks.driver;

public class PaginationSteps {

    private final WebDriver webDriver = driver;
    private final PaginationPage paginationPage = new PaginationPage(webDriver);

    private int previousPageNumber;
    private List<String> itemsBeforeClick;

    @Given("I am on a category page that contains more than 4 items")
    public void i_am_on_a_category_page_with_more_than_4_items() {
        paginationPage.openCategoryWithMoreThanFourItems();
    }

    @Given("I select {string} items per page in the \"Display\" dropdown")
    public void i_select_items_per_page_in_display_dropdown(String value) {
        paginationPage.selectItemsPerPage(value);
        assertTrue(paginationPage.isPaginationVisible(),
                "Pagination should be visible after selecting items per page");
    }

    @Then("I see pagination component")
    public void i_see_pagination_component() {
        assertTrue(paginationPage.isPaginationVisible(),
                "Pagination component is not visible");
    }

    //    Pagination Visibility

    @Then("page number buttons are visible")
    public void page_number_buttons_are_visible() {
        assertTrue(paginationPage.arePageNumbersVisible(),
                "Page number buttons are not visible");
    }

    @Given("I am on a non-last page")
    public void i_am_on_a_non_last_page() {
        paginationPage.goToFirstPage();
        assertTrue(paginationPage.isNextButtonPresent(),
                "Next button should be present on non-last page");
    }

    @Then("the Next button is visible")
    public void the_next_button_is_visible() {
        assertTrue(paginationPage.isNextButtonVisible(),
                "Next button is not visible");
    }

    @Given("I am on page {int}")
    public void i_am_on_page_number(int pageNumber) {
        paginationPage.goToPage(pageNumber);
        assertEquals(pageNumber, paginationPage.getCurrentPageNumber(),
                "Current page number is not " + pageNumber);
    }

    @Then("the Previous button is visible")
    public void the_previous_button_is_visible() {
        assertTrue(paginationPage.isPreviousButtonVisible(),
                "Previous button is not visible");
    }


    //   Next Button

    @Given("I am on page N where N is less than the last page")
    public void i_am_on_page_n_where_n_is_less_than_last_page() {
        paginationPage.goToFirstPage();
        assertTrue(paginationPage.isNextButtonPresent(),
                "Next button should be present when N is less than last page");
    }

    @When("I click the Next button")
    public void i_click_the_next_button() {
        previousPageNumber = paginationPage.getCurrentPageNumber();
        itemsBeforeClick = paginationPage.getProductTitles();
        paginationPage.clickNext();
    }

    @Then("page N+1 is displayed")
    public void page_n_plus_1_is_displayed() {
        int current = paginationPage.getCurrentPageNumber();
        assertEquals(previousPageNumber + 1, current,
                "Current page should be N+1");
    }

    @Then("items from page N are no longer visible")
    public void items_from_page_n_are_no_longer_visible() {
        List<String> itemsAfter = paginationPage.getProductTitles();
        assertNotEquals(itemsBeforeClick, itemsAfter,
                "Items from page N should not be the same on page N+1");
    }

    @Then("items from page N+1 are visible")
    public void items_from_page_n_plus_1_are_visible() {
        List<String> itemsAfter = paginationPage.getProductTitles();
        assertFalse(itemsAfter.isEmpty(), "Items on page N+1 should be visible");
    }

    @Given("I am on the last page")
    public void i_am_on_the_last_page() {
        paginationPage.goToLastPage();
    }

    @Then("the Next button is disabled")
    public void the_next_button_is_disabled() {
        assertFalse(paginationPage.isNextButtonPresent(),
                "Next button should be disabled (not present) on the last page");
    }

    //    Direct page navigation

    @When("I click on page {int} in the pagination component")
    public void i_click_on_page_n_in_the_pagination_component(int pageNumber) {
        paginationPage.goToPage(pageNumber);
    }

    @Then("page {int} is displayed")
    public void page_n_is_displayed(int pageNumber) {
        assertEquals(pageNumber, paginationPage.getCurrentPageNumber(),
                "Expected page " + pageNumber + " to be displayed");
    }

    @Then("the content is updated according to page {int}")
    public void the_content_is_updated_according_to_page_n(int pageNumber) {
        List<String> titles = paginationPage.getProductTitles();
        assertFalse(titles.isEmpty(),
                "Content for page " + pageNumber + " should not be empty");
    }

    //    Previous Button

    @Given("I am on page N where N is greater than 1")
    public void i_am_on_page_n_where_n_is_greater_than_1() {
        paginationPage.goToPage(2);
        assertTrue(paginationPage.isPreviousButtonPresent(),
                "Previous button should be present when N > 1");
    }

    @When("I click the Previous button")
    public void i_click_the_previous_button() {
        previousPageNumber = paginationPage.getCurrentPageNumber();
        itemsBeforeClick = paginationPage.getProductTitles();
        paginationPage.clickPrevious();
    }

    @Then("page N-1 is displayed")
    public void page_n_minus_1_is_displayed() {
        int current = paginationPage.getCurrentPageNumber();
        assertEquals(previousPageNumber - 1, current,
                "Current page should be N-1");
    }

    @Then("items from page N-1 are visible")
    public void items_from_page_n_minus_1_are_visible() {
        List<String> itemsAfter = paginationPage.getProductTitles();
        assertFalse(itemsAfter.isEmpty(),
                "Items on page N-1 should be visible");
    }

    @Then("items from page N are no longer visible after going back")
    public void items_from_page_n_are_no_longer_visible_after_going_back() {
        List<String> itemsAfter = paginationPage.getProductTitles();
        assertNotEquals(itemsBeforeClick, itemsAfter,
                "Items on previous page should differ from items on N");
    }

    @Then("the Previous button is disabled")
    public void the_previous_button_is_disabled() {
        assertFalse(paginationPage.isPreviousButtonPresent(),
                "Previous button should be disabled (not present) on the first page");
    }
}