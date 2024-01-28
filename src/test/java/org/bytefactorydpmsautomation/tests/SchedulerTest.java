package org.bytefactorydpmsautomation.tests;

import org.bytefactorydpmsautomation.PageObjects.SchedulerPage;
import org.bytefactorydpmsautomation.TestComponents.CustomListeners;
import org.bytefactorydpmsautomation.data.DataProvider;
import org.bytefactorydpmsautomation.data.JSONDataReader;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Listeners(CustomListeners.class)
public class SchedulerTest {
    WebDriver driver;
    SchedulerPage schedulerPage;
    DataProvider dataProvider;
    JSONDataReader jsonDataReader;
    List<HashMap<String,String>> loginData;
    List<HashMap<String,String>> urlData;
    List<HashMap<String,String>> schedulerData;
    @BeforeTest
    public void setUp() throws IOException {
        driver = LoginTest.driver;
        schedulerPage = new SchedulerPage(driver);
        dataProvider = new DataProvider();
        jsonDataReader = new JSONDataReader();

        loginData = jsonDataReader.getJsonDataToMap("LoginData.json");
        schedulerData = jsonDataReader.getJsonDataToMap("SchedulerData.json");
        urlData = jsonDataReader.getJsonDataToMap("URLData.json");
    }


    @Test(priority = 1)
    public void testSchedulerCreation() throws InterruptedException {
        String SchedulerDate = schedulerData.get(0).get("SchedulerDate");
        String AssertName = schedulerData.get(0).get("Assert");
        String CheckList = schedulerData.get(0).get("CheckList");
        String Frequency = schedulerData.get(0).get("Frequency");
        String AssignedToUser = schedulerData.get(0).get("AssignedToUser");
        String StartDate = schedulerData.get(0).get("StartDate");
        String EndDate = schedulerData.get(0).get("EndDate");
        String StartTime = schedulerData.get(0).get("StartTime");
        String EndTime = schedulerData.get(0).get("EndTime");
        String Description = schedulerData.get(0).get("Description");

        schedulerPage.createScheduler(SchedulerDate, AssertName, CheckList, Frequency, AssignedToUser, StartDate, EndDate, StartTime, EndTime, Description);
//        boolean isSchedulerCreated = schedulerPage.validateScheduler(AssignedToUser, Description);
//        Assert.assertTrue(isSchedulerCreated);
    }



}
