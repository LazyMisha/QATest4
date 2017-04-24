package myprojects.automation.assignment5;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Base script functionality, can be used for all Selenium scripts.
 */
public abstract class BaseTest {
    protected WebDriver driver;
    protected GeneralActions actions;
    protected DesiredCapabilities capability;
    protected boolean isMobileTesting;

    /**
     * Prepares {@link WebDriver} instance with timeout and browser window configurations.
     *
     * Driver type is based on passed parameters to the automation project,
     * creates {@link ChromeDriver} instance by default.
     *
     */
    @BeforeClass
    @Parameters({"selenium.browser", "selenium.grid"})
    public void setUp(@Optional("android") String browser, @Optional("http://localhost:4444/wd/hub") String gridUrl) {
        switch (browser){
            case "firefox":
                try {
                    capability = DesiredCapabilities.firefox();
                    driver = new RemoteWebDriver(new URL(gridUrl), capability);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            case "chrome":
                try {
                    capability = DesiredCapabilities.chrome();
                    driver = new RemoteWebDriver(new URL(gridUrl), capability);
                }catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            case "phantomjs":
                try{
                    capability = DesiredCapabilities.phantomjs();
                    driver = new RemoteWebDriver(new URL(gridUrl), capability);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            case "android":
                try {
                    capability = DesiredCapabilities.android();
                    driver = new RemoteWebDriver(new URL(gridUrl), capability);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
        }

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

        if (!isMobileTesting(browser))
            driver.manage().window().maximize();

        isMobileTesting = isMobileTesting(browser);
        actions = new GeneralActions(driver);
    }

    /**
     * Closes driver instance after test class execution.
     */
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     *
     * @return Whether required browser displays content in mobile mode.
     */
    private boolean isMobileTesting(String browser) {
        switch (browser) {
            case "android":
                return true;
            case "firefox":
            case "ie":
            case "internet explorer":
            case "chrome":
            case "phantomjs":
            default:
                return false;
        }
    }
}