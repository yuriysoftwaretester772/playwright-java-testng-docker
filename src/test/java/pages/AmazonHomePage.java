package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.testng.Assert;

public class AmazonHomePage {

    private final Page page;
    private static final String PAGE_TITLE = "Amazon.com. Spend less. Smile more.";
    private static final String BASE_URL = System.getProperty("BASE_URL", "https://www.amazon.com");

    // Locators
    private final Locator searchInputField;
    private final Locator searchButton;

    public AmazonHomePage(Page page) {
        this.page = page;
        this.searchInputField = page.locator("#twotabsearchtextbox");
        this.searchButton = page.locator("#nav-search-submit-button");
    }

    public void navigateToHomePage() {
        page.navigate(BASE_URL);
    }

    public void verifyPageTitle() {
        Assert.assertEquals(page.title(), PAGE_TITLE, "Page title does not match.");
    }

    public void waitForSearchBox() {
        searchInputField.waitFor();
    }

    public void enterSearchItemAndSubmit(String item) {
        searchInputField.fill(item);
        searchButton.click();
    }
}
