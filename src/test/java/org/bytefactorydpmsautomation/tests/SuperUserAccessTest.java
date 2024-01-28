package org.bytefactorydpmsautomation.tests;

import org.bytefactorydpmsautomation.PageObjects.ConfigurationMasterUserAccessPage;
import org.bytefactorydpmsautomation.PageObjects.SchedulerPage;
import org.bytefactorydpmsautomation.TestComponents.CustomListeners;
import org.bytefactorydpmsautomation.data.DataProvider;
import org.bytefactorydpmsautomation.data.JSONDataReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Factory;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

@Listeners(CustomListeners.class)
public class SuperUserAccessTest {

    WebDriver driver;
    SchedulerPage schedulerPage;
    DataProvider dataProvider;
    JSONDataReader jsonDataReader;
    List<HashMap<String,String>> loginData;
    List<HashMap<String,String>> urlData;

    ConfigurationMasterUserAccessPage configurationMasterUserAccessPage;

    @BeforeTest
    public void setUp() throws IOException {
        driver = LoginTest.driver;
        schedulerPage = new SchedulerPage(driver);
        dataProvider = new DataProvider();
        configurationMasterUserAccessPage = new ConfigurationMasterUserAccessPage(driver);
        jsonDataReader = new JSONDataReader();
        loginData = jsonDataReader.getJsonDataToMap("LoginData.json");
        urlData = jsonDataReader.getJsonDataToMap("URLData.json");
    }

    @Test(priority = 1)
    public void testSuperUserLogin() throws InterruptedException {
        String userName = loginData.get(1).get("username");
        String password = loginData.get(1).get("password");

        schedulerPage.login(userName, password);
        String expectedUrlAfterLogin = urlData.get(0).get("dashboardUrl");
        Thread.sleep(1000);
        String actualUrlAfterLogin = driver.getCurrentUrl();
        Assert.assertEquals(expectedUrlAfterLogin, actualUrlAfterLogin);
    }

    @Test(priority = 2)
    public void testSuperUserAccess() throws InterruptedException {
        Assert.assertTrue(configurationMasterUserAccessPage.checkUserAccess());
    }

    @Test(priority = 3)
    public void testSuperUserLogout() throws IOException, InterruptedException {
        String userName = loginData.get(1).get("username");
        String expectedUrlAfterLogout = urlData.get(0).get("loginUrl");
        configurationMasterUserAccessPage.logOut(userName);
        Thread.sleep(1000);
        String actualUrlAfterLogout = driver.getCurrentUrl();
        Assert.assertEquals(expectedUrlAfterLogout, actualUrlAfterLogout);
    }
}
