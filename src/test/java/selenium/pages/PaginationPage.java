package selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PaginationPage {

    private final WebDriver driver;


    @FindBy(id = "products-pagesize")   //"Display" dropdown: selects how many items are shown per page
    private WebElement itemsPerPageSelect;

    @FindBy(css = "div.pager")         //Pagination container
    private WebElement pagerContainer;

    @FindBy(css = "div.pager li.current-page span")
    private WebElement currentPageSpan;

    @FindBy(css = "div.pager li.individual-page a")
    private List<WebElement> pageNumberLinks;

    @FindBy(css = "div.pager li.next-page a")
    private List<WebElement> nextButtons;

    @FindBy(css = "div.pager li.previous-page a")
    private List<WebElement> previousButtons;

    @FindBy(css = ".product-grid .item-box .product-title a")  //Used to verify content changes on the page
    private List<WebElement> productTitleLinks;


    public PaginationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void openCategoryWithMoreThanFourItems() {
        driver.get("https://demowebshop.tricentis.com/books");
    }

    //Actions

    //"Display" dropdown
    public void selectItemsPerPage(String value) {
        Select select = new Select(itemsPerPageSelect);
        select.selectByVisibleText(value);
    }

    //Pagination visibility checks
    public boolean isPaginationVisible() {
        return isElementDisplayed(pagerContainer);
    }

    public boolean arePageNumbersVisible() {
        return !pageNumberLinks.isEmpty() && pageNumberLinks.stream().allMatch(WebElement::isDisplayed);
    }
    //All page numbers are shown to the user
    //instead of simple loop:

    //    for (WebElement el : pageNumberLinks) {
    //        if (!el.isDisplayed()) {
    //            return false;
    //        }
    //    }
    //return true;

    public boolean isNextButtonVisible() {
        return isListElementDisplayed(nextButtons);
    }

    public boolean isPreviousButtonVisible() {
        return isListElementDisplayed(previousButtons);
    }

    public boolean isNextButtonPresent() {
        return isListElementDisplayed(nextButtons);
    }

    public boolean isPreviousButtonPresent() {
        return isListElementDisplayed(previousButtons);
    }

    public int getCurrentPageNumber() {
        return Integer.parseInt(currentPageSpan.getText().trim());
    }

//Navigate to specific pages

    public void goToPage(int pageNumber) {
        int current = getCurrentPageNumber();
        if (current == pageNumber) {
            return;
        }

        for (WebElement link : pageNumberLinks) {
            if (link.getText().trim().equals(String.valueOf(pageNumber))) {
                link.click();
                return;
            }
        }
        throw new IllegalStateException("Page number " + pageNumber + " not found in pager");
    }

    public void goToFirstPage() {
        goToPage(1);
    }

    public void goToLastPage() {
        while (isNextButtonPresent()) {
            clickNext();
        }
    }

    //Next and Previous button

    public void clickNext() {
        if (!nextButtons.isEmpty()) {
            nextButtons.get(0).click();
        } else {
            throw new IllegalStateException("Next button is not present");
        }
    }

    public void clickPrevious() {
        if (!previousButtons.isEmpty()) {
            previousButtons.get(0).click();
        } else {
            throw new IllegalStateException("Previous button is not present");
        }
    }

    //Get product titles on the current page

    public List<String> getProductTitles() {
        if (productTitleLinks == null) {
            return new ArrayList<>();
        }
        return productTitleLinks.stream()
                .map(el -> el.getText().trim())
                .collect(Collectors.toList());
    }

    //Helpers
    //Check whether an element or a list-based element is displayed,
    // without failing the test when the DOM changes

    private boolean isElementDisplayed(WebElement element) {
        try {
            return element != null && element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    private boolean isListElementDisplayed(List<WebElement> elements) {
        try {
            return elements != null && !elements.isEmpty() && elements.get(0).isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }
}
