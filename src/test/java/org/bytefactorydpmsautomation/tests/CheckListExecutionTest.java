package org.bytefactorydpmsautomation.tests;

import org.bytefactorydpmsautomation.PageObjects.CheckListExecutionPage;
import org.bytefactorydpmsautomation.PageObjects.DashBoardPage;
import org.bytefactorydpmsautomation.PageObjects.SchedulerPage;
import org.bytefactorydpmsautomation.TestComponents.CustomListeners;
import org.bytefactorydpmsautomation.data.DataProvider;
import org.bytefactorydpmsautomation.data.JSONDataReader;
import org.openqa.selenium.WebDriver;
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
public class CheckListExecutionTest{
    WebDriver driver;
    SchedulerPage schedulerPage;

    DashBoardPage dashBoardPage;
    CheckListExecutionPage checkListExecutionPage;
    JSONDataReader jsonDataReader;
    List<HashMap<String,String>> loginData;
    List<HashMap<String,String>> roleData;
    List<HashMap<String,String>> userData;
    List<HashMap<String,String>> userGroupData;
    List<HashMap<String,String>> urlData;
    List<HashMap<String,String>> schedulerData;
    List<HashMap<String,String>> schedulerRemarkData;

    @BeforeTest
    public void setUp() throws IOException {
        driver = LoginTest.driver;

        schedulerPage = new SchedulerPage(driver);
        dashBoardPage = new DashBoardPage(driver);
        checkListExecutionPage = new CheckListExecutionPage(driver);

        jsonDataReader = new JSONDataReader();
        loginData = jsonDataReader.getJsonDataToMap("LoginData.json");
        roleData = jsonDataReader.getJsonDataToMap("RoleCreationData.json");
        userData = jsonDataReader.getJsonDataToMap("UserCreationData.json");
        userGroupData = jsonDataReader.getJsonDataToMap("UserGroupData.json");
        urlData = jsonDataReader.getJsonDataToMap("URLData.json");
        schedulerData = jsonDataReader.getJsonDataToMap("SchedulerData.json");
        schedulerRemarkData = jsonDataReader.getJsonDataToMap("SchedulerRemarkData.json");

    }

    @Test(priority = 1)
    public void testCheckListExecutionScheduledStatus() throws InterruptedException {
        List<Integer> scheduledCountList = dashBoardPage.checkListExecutionScheduledStatus();
        Assert.assertEquals(scheduledCountList.get(0), scheduledCountList.get(1));
    }

    @Test(priority = 2)
    public void testChecklistExecutionValidate() throws InterruptedException {
        String AssertName = schedulerData.get(0).get("Assert");
        String AssignedToUser = schedulerData.get(0).get("AssignedToUser");
        String Description = schedulerData.get(0).get("Description");
        boolean isValidCheckList = checkListExecutionPage.validateChecklistExecution(AssertName, AssignedToUser, Description);
        Assert.assertTrue(isValidCheckList);
    }

    @Test(priority = 3)
    public void testManagerLogout() throws InterruptedException {
        String userName = loginData.get(2).get("username");
        String expectedUrlAfterLogin = urlData.get(0).get("loginUrl");
        checkListExecutionPage.logOut(userName);
        Thread.sleep(1000);
        String actualUrlAfterLogin = driver.getCurrentUrl();
        Assert.assertEquals(expectedUrlAfterLogin, actualUrlAfterLogin);
    }

    @Test(priority = 4)
    public void testTechnicianLogin() {
        String userName = loginData.get(3).get("username");
        String password = loginData.get(3).get("password");
        String expectedUrlAfterLogin = urlData.get(0).get("dashboardUrl");
        checkListExecutionPage.login(userName, password);
        String actualUrlAfterLogin = driver.getCurrentUrl();
        Assert.assertEquals(expectedUrlAfterLogin, actualUrlAfterLogin);

    }

    @Test(priority = 5)
    public void testSchedulerExecutionInProgress() throws InterruptedException {
        String AssertName = schedulerData.get(0).get("Assert");
        String AssignedToUser = schedulerData.get(0).get("AssignedToUser");
        String Description = schedulerData.get(0).get("Description");
        checkListExecutionPage.schedulerExecutionInProgress(AssertName, AssignedToUser, Description);
        dashBoardPage.actualTicketCount();
    }

    @Test(priority = 6)
    public void testCheckListExecutionStatusInProgress() throws InterruptedException {
        List<Integer> inProgressCountList = dashBoardPage.checkListExecutionInProgressStatus();
        Assert.assertEquals(inProgressCountList.get(0), inProgressCountList.get(1));
    }

    @Test(priority = 7)
    public void testSchedulerExecutionClosed() throws InterruptedException {
        String AssertName = schedulerData.get(0).get("Assert");
        String AssignedToUser = schedulerData.get(0).get("AssignedToUser");
        String Description = schedulerData.get(0).get("Description");
        HashMap<String, String> remarkData =  schedulerRemarkData.get(0);
        checkListExecutionPage.checkListExecutionClosed(AssertName, AssignedToUser, Description, remarkData);
    }

    @Test(priority = 8)
    public void testCheckListExecutionStatusClosed() throws InterruptedException {
        List<Integer> completedCountList = dashBoardPage.checkListExecutionClosedStatus();
        Assert.assertEquals(completedCountList.get(0), completedCountList.get(1));
    }

    @Test(priority = 9)
    public void testOverallAfterTicketCount() throws InterruptedException {
        boolean isValidTicketCount = dashBoardPage.checkUpdatedTicketCount();
        Assert.assertTrue(isValidTicketCount);
    }

    @Test(priority = 10)
    public void testTechnicianLogout() {
        String userName = loginData.get(3).get("username");
        String expectedUrlAfterLogout = urlData.get(0).get("loginUrl");
        checkListExecutionPage.logOut(userName);
        String actualUrlAfterLogout = driver.getCurrentUrl();
        Assert.assertEquals(expectedUrlAfterLogout, actualUrlAfterLogout);
    }
}
