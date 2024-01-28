package org.bytefactorydpmsautomation.TestComponents;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.bytefactorydpmsautomation.PageObjects.LandingPage;
import org.bytefactorydpmsautomation.TestDataCleanup.DatabaseCleanup;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {
    public WebDriver driver;
    public LandingPage landingPage;
    public static DataFormatter formatter =  new DataFormatter();
    private final DatabaseCleanup dbCleanup  = new DatabaseCleanup();;
    public WebDriver initializeDriver() throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
                + "//src//main//java//org//bytefactorydpmsautomation//resources//GlobalData.properties");
        prop.load(fis);
        String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : prop.getProperty("browser");
//
//        if (browserName.contains("chrome")) {
//
//            ChromeOptions options = new ChromeOptions();
//            System.out.println(options);
//            WebDriverManager.chromedriver().setup();
//            if (browserName.contains("headless")) {
//                options.addArguments("headless");
//            }
//            driver = new ChromeDriver(options);
//            System.out.println(driver);
//        }
//
//        driver.manage().window().maximize();
        if (browserName.contains("chrome")) {
            System.setProperty("webdriver.chrome.driver", "D:\\seleniumjars\\chromedriver\\chromedriver.exe");
            driver = new ChromeDriver();
        }else if (browserName.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", "D://seleniumjars//firefoxdriver//geckodriver.exe");
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("edge")) {
            System.setProperty("webdriver.edge.driver", "D://seleniumjars//edgedriver//msedgedriver.exe");
            driver = new EdgeDriver();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }
    public String getScreenshot(String testCaseName,WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot)driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
        FileUtils.copyFile(source, file);
        return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
    }
    public LandingPage launchApplication(String url) throws IOException{
        driver = initializeDriver();
        landingPage = new LandingPage(driver);
        landingPage.goTo(url);
        return landingPage;
    }


}
