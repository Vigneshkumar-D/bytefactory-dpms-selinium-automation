package org.bytefactorydpmsautomation.tests;

import org.bytefactorydpmsautomation.PageObjects.LandingPage;
import org.bytefactorydpmsautomation.TestComponents.BaseTest;
import org.bytefactorydpmsautomation.TestComponents.CustomListeners;
import org.bytefactorydpmsautomation.data.DataProvider;
import org.bytefactorydpmsautomation.data.JSONDataReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Listeners(CustomListeners.class)
public class LoginTest {
    public static WebDriver driver;
    BaseTest baseTest;
    JSONDataReader jsonDataReader;
    List<HashMap<String,String>> loginData;
    List<HashMap<String,String>> urlData;
    @BeforeTest
    public void setUp() throws IOException, SQLException, ClassNotFoundException {
        baseTest = new BaseTest();
        driver = baseTest.initializeDriver();
        jsonDataReader = new JSONDataReader();
        loginData = jsonDataReader.getJsonDataToMap("LoginData.json");
        urlData = jsonDataReader.getJsonDataToMap("URLData.json");
    }

    @Test(priority = 1)
    public void testLogin() throws InterruptedException, IOException, SQLException, ClassNotFoundException {
        String userName = loginData.get(0).get("username");
        String password = loginData.get(0).get("password");
        String loginUrl = urlData.get(0).get("loginUrl");
        String dashboardUrl = urlData.get(0).get("dashboardUrl");
        LandingPage landingPage = new LandingPage(driver);
        landingPage.goTo(loginUrl);
        landingPage.loginApplication(userName, password);
        Thread.sleep(1);
        landingPage.loginMainApplication(userName, password);
        WebDriverWait wait = new WebDriverWait(baseTest.driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe(dashboardUrl));
        String actualUrlAfterLogin = baseTest.driver.getCurrentUrl();
        Assert.assertEquals(actualUrlAfterLogin, dashboardUrl);
    }
}
