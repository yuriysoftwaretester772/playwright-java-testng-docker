package tests;

import com.microsoft.playwright.Page;
import config.BrowserSetup;
import io.qameta.allure.Description;
import org.testng.annotations.Test;
import pages.AmazonHomePage;


public class HomePageAmazonTest extends BrowserSetup {

    @Test(groups = "SmokeTest")
    @Description("Open Amazon Home page and check the title")
    public void openHomePageAndCheckTheTitle() {
        if (getPage() == null) {
            setUp();
        }
        Page page = getPage();
        AmazonHomePage amazonHomePage = new AmazonHomePage(page);

        amazonHomePage.navigateToHomePage();
        amazonHomePage.verifyPageTitle();
    }
}
