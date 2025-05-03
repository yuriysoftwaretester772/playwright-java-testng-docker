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
        page.waitForLoadState();
    }

    public void verifyPageTitle() {
        Assert.assertTrue(page.title().contains("Amazon.com"),
                "Page title does not contain the expected text. Actual title: " + page.title());
    }

    public void waitForSearchBox() {
        searchInputField.waitFor();
    }

    public void enterSearchItemAndSubmit(String item) {
        searchInputField.fill(item);
        searchButton.click();
    }
}
