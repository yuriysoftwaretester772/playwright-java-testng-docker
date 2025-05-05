package tests;

import com.microsoft.playwright.Page;
import config.BrowserSetup;
import io.qameta.allure.Description;
import org.testng.annotations.Test;
import pages.AmazonHomePage;
import pages.AmazonSearchPage;

public class SearchItemAmazonTest extends BrowserSetup {

    private final String searchItem = "nike airmax";

    @Test(groups = "RegressionTest")
    @Description("Open Amazon Home page, search for an item and check the title")
    public void openHomePageAndCheckTheTitle() {
        if (getPage() == null) {
            setUp();
        }
        Page page = getPage();

        AmazonHomePage amazonHomePage = new AmazonHomePage(page);
        AmazonSearchPage amazonSearchPage = new AmazonSearchPage(page);

        amazonHomePage.navigateToHomePage();
        amazonHomePage.verifyPageTitle();
        amazonSearchPage.navigateToSearchPage(searchItem);
        amazonSearchPage.verifyPageTitle(searchItem);
    }
}