package org.bytefactorydpmsautomation.tests;

import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.poi.ss.formula.functions.T;
import org.bytefactorydpmsautomation.PageObjects.DashBoardPage;
import org.bytefactorydpmsautomation.PageObjects.ReportsPage;
import org.bytefactorydpmsautomation.PageObjects.ResolutionWorkOrderPage;
import org.bytefactorydpmsautomation.TestComponents.BaseTest;
import org.bytefactorydpmsautomation.TestComponents.CustomListeners;
import org.bytefactorydpmsautomation.data.DataProvider;
import org.bytefactorydpmsautomation.data.JSONDataReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Listeners(CustomListeners.class)
public class ResolutionWorkOrderTest {
    DataProvider dataProvider = new DataProvider();
    WebDriver driver;
    BaseTest baseTest;
    ResolutionWorkOrderPage resolutionWorkOrderPage;
    DashBoardPage dashBoardPage;
    ReportsPage reportsPage;
    JSONDataReader jsonDataReader;
    List<HashMap<String,String>> loginData;
    List<HashMap<String,String>> urlData;
    List<HashMap<String,String>> rwoData;
    String workOrderNum;


    @BeforeTest
    public void setUp() throws IOException {
        driver = LoginTest.driver;
        resolutionWorkOrderPage = new ResolutionWorkOrderPage(driver);
        dashBoardPage = new DashBoardPage(driver);
        reportsPage = new ReportsPage(driver);
        jsonDataReader = new JSONDataReader();
        loginData = jsonDataReader.getJsonDataToMap("LoginData.json");
        urlData = jsonDataReader.getJsonDataToMap("URLData.json");
        rwoData = jsonDataReader.getJsonDataToMap("ResolutionWorkOrder.json");
    }

    @Test(priority = 1)
    public void testManagerLogin() throws InterruptedException {
        String userName = loginData.get(2).get("username");
        String password = loginData.get(2).get("password");
        String expectedUrlAfterLogin = urlData.get(0).get("dashboardUrl");
        resolutionWorkOrderPage.login(userName, password);
        Thread.sleep(1000);
        String actualUrlAfterLogin = driver.getCurrentUrl();
        Assert.assertEquals(expectedUrlAfterLogin, actualUrlAfterLogin);

    }

    @Test(priority = 2)
    public void testDashBoardRWOOpenedStatus() throws InterruptedException {
        boolean isRWOPresent = dashBoardPage.dashBoardRWOOpenedStatus();
        Assert.assertTrue(isRWOPresent);
    }

    @Test(priority = 3)
    public void testReportOpenedStatus() throws InterruptedException {
        workOrderNum = resolutionWorkOrderPage.getWoNum();
        boolean isRWOPresent = reportsPage.checkReportOpenedStatus("Opened", workOrderNum);
        Assert.assertTrue(isRWOPresent);
    }

    @Test(priority = 4)
    public void testUpdateResolutionWorkOrder() throws InterruptedException {
        String AssignedTo = rwoData.get(0).get("AssignedTo");
        String Priority = rwoData.get(0).get("Priority");
        String DueDate = rwoData.get(0).get("DueDate");
        resolutionWorkOrderPage.updateResolutionWorkOrder(AssignedTo, Priority, DueDate);
    }

    @Test(priority = 5)
    public void testDashBoardAssignedStatus() throws InterruptedException {
        boolean isRWOPresent = dashBoardPage.dashBoardRWOAssignedStatus();
        Assert.assertTrue(isRWOPresent);
    }

    @Test(priority = 6)
    public void testReportAssignedStatus() throws InterruptedException {
         workOrderNum = resolutionWorkOrderPage.getWoNum();
        boolean isRWOPresent = reportsPage.checkReportAssignedStatus("Assigned", workOrderNum);
        Assert.assertTrue(isRWOPresent);
    }

    @Test(priority = 7)
    public void testManagerLogout() throws InterruptedException {
        String userName = loginData.get(2).get("username");
        String expectedUrlAfterLogin = urlData.get(0).get("loginUrl");

        resolutionWorkOrderPage.logOut(userName);
        Thread.sleep(1000);
        String actualUrlAfterLogin = driver.getCurrentUrl();
        Assert.assertEquals(expectedUrlAfterLogin, actualUrlAfterLogin);
    }
    @Test(priority = 8)
    public void testTechnicianLogin() throws InterruptedException {
        String username = loginData.get(3).get("username");
        String password = loginData.get(3).get("password");
        String expectedUrlAfterLogin = urlData.get(0).get("dashboardUrl");

        resolutionWorkOrderPage.login(username, password);
        Thread.sleep(1000);
        String actualUrlAfterLogin = driver.getCurrentUrl();
        Assert.assertEquals(expectedUrlAfterLogin, actualUrlAfterLogin);
    }

    @Test(priority = 9)
    public void testCompleteResolutionWorkOrder() throws InterruptedException {
        String RCA = rwoData.get(0).get("RCA");
        String CA = rwoData.get(0).get("CA");
        String PA = rwoData.get(0).get("PA");
        resolutionWorkOrderPage.completeResolutionWorkOrder(RCA, CA, PA);
    }

    @Test(priority = 10)
    public void testDashBoardResolvedStatus() throws InterruptedException {
        boolean isRWOPresent = dashBoardPage.dashBoardRWOResolvedStatus();
        Assert.assertTrue(isRWOPresent);
    }

    @Test(priority = 11)
    public void testReportResolvedStatus() throws InterruptedException {
        workOrderNum = resolutionWorkOrderPage.getWoNum();
        boolean isRWOPresent = reportsPage.checkReportResolvedStatus("Resolved", workOrderNum);
        Assert.assertTrue(isRWOPresent);
    }

    @Test(priority = 12)
    public void testTechnicianLogout() throws InterruptedException {
        String userName = loginData.get(3).get("username");
        String expectedUrlAfterLogin = urlData.get(0).get("loginUrl");
        resolutionWorkOrderPage.logOut(userName);
        Thread.sleep(1000);
        String actualUrlAfterLogin = driver.getCurrentUrl();
        Assert.assertEquals(expectedUrlAfterLogin, actualUrlAfterLogin);
    }

    @Test(priority = 13)
    public void testManagerLogin1() throws InterruptedException {
        String userName = loginData.get(2).get("username");
        String password = loginData.get(2).get("password");
        String expectedUrlAfterLogin = urlData.get(0).get("dashboardUrl");
        resolutionWorkOrderPage.login(userName, password);
        Thread.sleep(1000);
        String actualUrlAfterLogin = driver.getCurrentUrl();
        Assert.assertEquals(expectedUrlAfterLogin, actualUrlAfterLogin);
    }

    @Test(priority = 14)
    public void testRWOApproval() throws InterruptedException {
        resolutionWorkOrderPage.provideApproval();
    }

    @Test(priority = 15)
    public void testDashBoardCompletedStatus() throws InterruptedException {
        boolean isRWOPresent = dashBoardPage.dashBoardRWOCompletedStatus();
        Assert.assertTrue(isRWOPresent);
    }

    @Test(priority = 16)
    public void testReportCompletedStatus() throws InterruptedException {
        System.out.println(workOrderNum);
        boolean isRWOPresent = reportsPage.checkReportCompletedStatus("Completed", workOrderNum);
        Assert.assertTrue(isRWOPresent);
    }

    @Test(priority = 17)
    public void testRWOTicketStatus() throws InterruptedException {
        boolean isRWOTicketCompleted = resolutionWorkOrderPage.checkRWOTicketStatus();
        Assert.assertTrue(isRWOTicketCompleted);
    }
    @Test(priority = 18)
    public void testManagerLogout1() throws InterruptedException {
        String userName = loginData.get(2).get("username");
        String expectedUrlAfterLogin = urlData.get(0).get("loginUrl");
        resolutionWorkOrderPage.logOut(userName);
        Thread.sleep(1000);
        String actualUrlAfterLogin = driver.getCurrentUrl();
        Assert.assertEquals(expectedUrlAfterLogin, actualUrlAfterLogin);
    }
}
