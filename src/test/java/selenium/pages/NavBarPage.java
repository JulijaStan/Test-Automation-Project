package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class NavBarPage {

    private final WebDriver driver;

    // Locators
    private final By headerMenu = By.className("header-menu");
    private final By cartLink   = By.className("ico-cart");
    private final By headerLogo = By.className("header-logo");

    // Locators with parameters for categories and subcategories
    private By categoryItem(String name) {
        return By.xpath("//ul[@class='top-menu']//a[normalize-space()='" + name + "']");
    }

    private By dropdownForCategory(String name) {
        return By.xpath("//ul[@class='top-menu']//a[normalize-space()='" + name + "']" +
                "/parent::li//ul[contains(@class,'sublist')]");
    }

    private By subcategoryItem(String name) {
        return By.xpath("//ul[@class='top-menu']" +
                "//ul[contains(@class,'sublist')]//a[normalize-space()='" + name + "']");
    }

    public NavBarPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openHomePage() {
        driver.get("https://demowebshop.tricentis.com");
    }

    // Actions

    public void navigateToAnyPage() {
        driver.findElement(cartLink).click();
    }

    public boolean isNavigationBarVisible() {
        WebElement navigationBar = driver.findElement(headerMenu);
        return navigationBar.isDisplayed();
    }

    public void hoverOverCategory(String categoryName) {
        WebElement category = driver.findElement(categoryItem(categoryName));
        hover(category);
    }


    public boolean isDropdownVisibleFor(String categoryName) {

        List<WebElement> dropdowns = driver.findElements(dropdownForCategory(categoryName));

        return !dropdowns.isEmpty() && dropdowns.get(0).isDisplayed();
    }

    public void clickCategory(String categoryName) {
        WebElement categoryLink = driver.findElement(categoryItem(categoryName));
        categoryLink.click();
    }

    public boolean isOnCategoryPage(String categoryUrl) {
        String currentUrl = driver.getCurrentUrl();
        return currentUrl.endsWith(categoryUrl);
    }

    public void clickSubcategory(String subcategoryName) {
        WebElement subcategoryLink = driver.findElement(subcategoryItem(subcategoryName));
        subcategoryLink.click();
    }

    public boolean isOnSubcategoryPage(String subcategoryUrl) {
        String currentUrl = driver.getCurrentUrl();
        return currentUrl.endsWith(subcategoryUrl);
    }

    public void moveCursorOutsideDropdown() {
        WebElement headerArea = driver.findElement(headerLogo);
        hover(headerArea);
    }

    public boolean isCategoryHighlighted(String categoryName) {
        WebElement categoryLink = driver.findElement(categoryItem(categoryName));
        return isHighlighted(categoryLink);
    }

    public void hoverOverSubcategory(String subcategoryName) {
        WebElement subcategoryLink = driver.findElement(subcategoryItem(subcategoryName));
        hover(subcategoryLink);
    }

    public boolean isSubcategoryHighlighted(String subcategoryName) {
        WebElement subcategoryLink = driver.findElement(subcategoryItem(subcategoryName));
        return isHighlighted(subcategoryLink);
    }

    // Helpers

    private void hover(WebElement element) {
        new Actions(driver).moveToElement(element).perform();
    }

    private boolean isHighlighted(WebElement element) {
        String cssClass = element.getAttribute("class");
        return cssClass != null && cssClass.contains("hover");
    }
}
