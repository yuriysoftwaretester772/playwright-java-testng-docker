package tests;

import com.microsoft.playwright.Page;
import config.BrowserSetup;
import io.qameta.allure.Description;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AmazonHomePage;
import pages.AmazonSearchPage;

public class SearchMultipleItemsAmazonTest extends BrowserSetup {

    @DataProvider(name = "searchItems")
    public Object[][] provideSearchItems() {
        return new Object[][] {
                {"nike airmax"},
                {"puma sneakers"},
                {"reebok crossfit"}
        };
    }

    @Test(dataProvider = "searchItems", groups = "RegressionTest")
    @Description("Open Amazon Home page, search for an item, and verify the title")
    public void testSearchFunctionality(String searchItem) {
        if (getPage() == null) {
            setUp();
        }
        Page page = getPage();
        AmazonHomePage amazonHomePage = new AmazonHomePage(page);
        AmazonSearchPage amazonSearchPage = new AmazonSearchPage(page);

        amazonHomePage.navigateToHomePage();
        amazonHomePage.verifyPageTitle();
        amazonHomePage.enterSearchItemAndSubmit(searchItem);
        amazonSearchPage.verifyPageTitle(searchItem);
    }
}