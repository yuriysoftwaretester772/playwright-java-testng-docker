package config;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BrowserSetup {

    private static final ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> context = new ThreadLocal<>();
    private static final ThreadLocal<Page> page = new ThreadLocal<>();

    @BeforeMethod
    public void setUp() {
        String browserName = System.getProperty("BROWSER", "chromium").toLowerCase(); // default: chromium
        boolean headless = Boolean.parseBoolean(System.getProperty("HEADLESS", "true")); // default: true

        playwright.set(Playwright.create());

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(headless);

        // Set the browser
        switch (browserName) {
            case "firefox":
                browser.set(playwright.get().firefox().launch(launchOptions));
                break;
            case "webkit":
                browser.set(playwright.get().webkit().launch(launchOptions));
                break;
            case "chromium":
            default:
                browser.set(playwright.get().chromium().launch(launchOptions));
                break;
        }

        // Set a realistic User-Agent string per browser
        String userAgent;
        switch (browserName) {
            case "firefox":
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:122.0) Gecko/20100101 Firefox/122.0";
                break;
            case "webkit":
                userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 13_2_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.0 Safari/605.1.15";
                break;
            case "chromium":
            default:
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36";
                break;
        }

        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions()
                .setUserAgent(userAgent);

        context.set(browser.get().newContext(contextOptions));
        page.set(context.get().newPage());
    }

    @AfterMethod
    public void tearDown() {
        if (page.get() != null) page.get().close();
        if (context.get() != null) context.get().close();
        if (browser.get() != null) browser.get().close();
        if (playwright.get() != null) playwright.get().close();

        page.remove();
        context.remove();
        browser.remove();
        playwright.remove();
    }

    protected Page getPage() {
        return page.get();
    }
}
