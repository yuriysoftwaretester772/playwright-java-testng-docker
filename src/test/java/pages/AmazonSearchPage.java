package pages;

import com.microsoft.playwright.Page;
import org.testng.Assert;

public class AmazonSearchPage {

    private static final String PAGE_TITLE = "Amazon.com : ";
    private static final String PAGE_URL = "https://www.amazon.com/s?k=";

    private static final String SEARCH_INPUT_FIELD = "#twotabsearchtextbox";
    private static final String SEARCH_BUTTON = "#nav-search-submit-button";

    private final Page page;

    public AmazonSearchPage(Page page) {
        this.page = page;
    }

    public void navigateToSearchPage(String item) {
        page.navigate(PAGE_URL + item);
    }

    public void clearSearchInputField() {
        page.locator(SEARCH_INPUT_FIELD).clear();
    }

    public void enterSearchItemAndSubmit(String item) {
        page.locator(SEARCH_INPUT_FIELD).fill(item);
        page.locator(SEARCH_BUTTON).click();
    }

    public void verifyPageTitle(String searchItem) {
        String actualTitle = page.title().toLowerCase();
        String searchItemLower = searchItem.toLowerCase();

        Assert.assertTrue(actualTitle.contains(searchItemLower),
                String.format("Page title '%s' does not contain search term '%s'", actualTitle, searchItem));
    }
}