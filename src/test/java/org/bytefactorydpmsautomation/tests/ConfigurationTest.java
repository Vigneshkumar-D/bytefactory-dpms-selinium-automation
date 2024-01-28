package org.bytefactorydpmsautomation.tests;

import org.bytefactorydpmsautomation.PageObjects.*;
import org.bytefactorydpmsautomation.TestComponents.CustomListeners;
import org.bytefactorydpmsautomation.data.JSONDataReader;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.awt.image.DataBuffer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Listeners(CustomListeners.class)
public class ConfigurationTest {
    WebDriver driver;
    ConfigurationMasterRolePage configurationMasterRolePage;
    ConfigurationMasterUserPage configurationMasterUserPage;
    ConfigurationMasterUserAccessPage configurationMasterUserAccessPage;
    ConfigurationMasterUserGroupPage configurationMasterUserGroupPage;
    ConfigurationAssertPage configurationAssertPage;
    ConfigurationCheckListPage configurationCheckListPage;
    JSONDataReader jsonDataReader;
    List<HashMap<String,String>> loginData;
    List<HashMap<String,String>> roleData;
    List<HashMap<String,String>> userData;
    List<HashMap<String,String>> userGroupData;
    List<HashMap<String,String>> urlData;
    List<HashMap<String,String>> assetData;
    List<HashMap<String,String>> checkTypesData;
    List<HashMap<String,String>> checksData;
    List<HashMap<String,String>> checkListData;

    @BeforeTest
    public void setUp() throws IOException {
        driver = LoginTest.driver;

        configurationMasterRolePage = new ConfigurationMasterRolePage(driver);
        configurationMasterUserPage = new ConfigurationMasterUserPage(driver);
        configurationMasterUserAccessPage = new ConfigurationMasterUserAccessPage(driver);
        configurationMasterUserGroupPage = new ConfigurationMasterUserGroupPage(driver);
        configurationAssertPage = new ConfigurationAssertPage(driver);
        configurationCheckListPage = new ConfigurationCheckListPage(driver);
        jsonDataReader = new JSONDataReader();

        loginData = jsonDataReader.getJsonDataToMap("LoginData.json");
        roleData = jsonDataReader.getJsonDataToMap("RoleCreationData.json");
        userData = jsonDataReader.getJsonDataToMap("UserCreationData.json");
        userGroupData = jsonDataReader.getJsonDataToMap("UserGroupData.json");
        urlData = jsonDataReader.getJsonDataToMap("URLData.json");
        assetData = jsonDataReader.getJsonDataToMap("AssetData.json");
        checkTypesData = jsonDataReader.getJsonDataToMap("CheckTypesData.json");
        checksData = jsonDataReader.getJsonDataToMap("ChecksData.json");
        checkListData = jsonDataReader.getJsonDataToMap("CheckListData.json");
    }

    @Test(priority = 1)
    public void testSuperUserRoleCreation() throws InterruptedException {
        Thread.sleep(2000);
        String roleName = roleData.get(0).get("SuperUserRole");
        HashMap<String, String> inputValidationData = roleData.get(1);
        configurationMasterRolePage.selectConfiguration();
        configurationMasterRolePage.createRole(roleName, inputValidationData);
        boolean isRoleCreated = configurationMasterRolePage.validateRole(roleName);
        Assert.assertTrue(isRoleCreated);
    }

    @Test(priority = 2) //, dependsOnMethods={"superUserRoleCreationTest"})
    public void testSearchRole()throws InterruptedException {
        Thread.sleep(1000);
        String roleName = roleData.get(0).get("SuperUserRole");
        boolean isRoleFound =  configurationMasterRolePage.searchRole(roleName);
        Assert.assertTrue(isRoleFound);
    }

    @Test(priority = 3) //, dependsOnMethods={"superUserRoleCreationTest"})
    public void testSuperUserCreation(){
        String userName = userData.get(0).get("UserName");
        String role = userData.get(0).get("Role");
        String email = userData.get(0).get("Email");
        String mobileNo = userData.get(0).get("MobileNo");
        String password = userData.get(0).get("Password");
        configurationMasterUserPage.createUser(userName,role, email, mobileNo, password);
        boolean isUserAdded = configurationMasterUserPage.searchUser(userName);
        Assert.assertTrue(isUserAdded);
    }

    @Test(priority = 4) //, dependsOnMethods={"superUserRoleCreationTest"})
    public void testSuperUserAccess() throws InterruptedException {
        String roleName = roleData.get(0).get("SuperUserRole");
        String successMessage =configurationMasterUserAccessPage.provideUserAccess(roleName);
        Assert.assertEquals(successMessage,"UserAcess provided successfully");
    }

    @Test(priority = 5)
    public void testLogOutAdmin() throws InterruptedException, IOException {
        String userName = loginData.get(0).get("username");
        String expectedUrlAfterLogin = urlData.get(0).get("loginUrl");
        configurationMasterRolePage.logOut(userName);
        Thread.sleep(1000);
        String actualUrlAfterLogin = driver.getCurrentUrl();
        Assert.assertEquals(expectedUrlAfterLogin, actualUrlAfterLogin);
    }

    @Test(priority = 6)
    public void testLoginSuperUser() throws InterruptedException {
        String userName = loginData.get(1).get("username");
        String password = loginData.get(1).get("password");
        configurationMasterRolePage.login(userName, password);
        Thread.sleep(2000);
        String expectedUrlAfterLogin = urlData.get(0).get("dashboardUrl");;
        String actualUrlAfterLogin = driver.getCurrentUrl();
        Assert.assertEquals(expectedUrlAfterLogin, actualUrlAfterLogin);
    }

    @Test(priority = 7)
    public void testManagerRoleCreation() throws InterruptedException {
        String roleName = roleData.get(0).get("ManagerRole");
        HashMap<String, String> inputValidationData = roleData.get(1);
        configurationMasterRolePage.selectConfiguration();
        configurationMasterRolePage.createRole(roleName, inputValidationData);
        boolean isRoleCreated = configurationMasterRolePage.validateRole(roleName);
        Assert.assertTrue(isRoleCreated);
    }

    @Test(priority = 8)
    public void testTechnicianRoleCreation() throws InterruptedException {
        String roleName = roleData.get(0).get("TechnicianRole");
        HashMap<String, String> inputValidationData = roleData.get(1);
        configurationMasterRolePage.selectConfiguration();
        configurationMasterRolePage.createRole(roleName, inputValidationData);
        boolean isRoleCreated = configurationMasterRolePage.validateRole(roleName);
        Assert.assertTrue(isRoleCreated);
    }

    @Test(priority = 9) //, dependsOnMethods={"managerRoleCreationTest"})
    public void testManagerUserCreation(){
        String userName = userData.get(1).get("UserName");
        String role = userData.get(1).get("Role");
        String email = userData.get(1).get("Email");
        String mobileNo = userData.get(1).get("MobileNo");
        String password = userData.get(1).get("Password");
        configurationMasterUserPage.createUser(userName,role, email, mobileNo, password);
        boolean isUserAdded = configurationMasterUserPage.searchUser(userName);
        Assert.assertTrue(isUserAdded);
    }

    @Test(priority = 10) //, dependsOnMethods={"technicianRoleCreationTest"})
    public void testTechnicianUserCreation(){
        String userName = userData.get(2).get("UserName");
        String role = userData.get(2).get("Role");
        String email = userData.get(2).get("Email");
        String mobileNo = userData.get(2).get("MobileNo");
        String password = userData.get(2).get("Password");
        configurationMasterUserPage.createUser(userName,role, email, mobileNo, password);
        boolean isUserAdded = configurationMasterUserPage.searchUser(userName);
        Assert.assertTrue(isUserAdded);
    }

    @Test(priority = 11)
    public void testUserGroupCreation() throws InterruptedException {
        String GroupName = userGroupData.get(0).get("GroupName");
        String Description = userGroupData.get(0).get("Description");
        List<String> groupMemberList = new ArrayList<>();
        groupMemberList.add(userGroupData.get(0).get("User1"));
        groupMemberList.add(userGroupData.get(0).get("User2"));
        groupMemberList.add(userGroupData.get(0).get("User3"));
        groupMemberList.add(userGroupData.get(0).get("User4"));
        configurationMasterUserGroupPage.createUserGroup(GroupName, Description, groupMemberList);
        boolean isGroupCreated = configurationMasterUserGroupPage.searchUserGroup(GroupName);
        Assert.assertTrue(isGroupCreated);
    }

    @Test(priority = 12) // dependsOnMethods={"managerRoleCreationTest"})
    public void testManagerUserAccess() throws InterruptedException {
        String roleName = roleData.get(0).get("ManagerRole");
        String successMessage= configurationMasterUserAccessPage.provideUserAccess(roleName);
        Assert.assertEquals("UserAcess provided successfully", successMessage);
    }

    @Test(priority = 13) //, dependsOnMethods={"technicianRoleCreationTest"})
    public void testTechnicianUserAccess() throws InterruptedException {
        String roleName = roleData.get(0).get("TechnicianRole");
        String successMessage = configurationMasterUserAccessPage.provideUserAccess(roleName);
        Assert.assertEquals("UserAcess provided successfully", successMessage);
    }

    @Test(priority = 14)
    public void testSuperUserLogout() throws InterruptedException {
        String userName = loginData.get(1).get("username");
        String expectedUrlAfterLogin = urlData.get(0).get("loginUrl");
        configurationMasterRolePage.logOut(userName);
        Thread.sleep(2000);
        String actualUrlAfterLogin = driver.getCurrentUrl();
        Assert.assertEquals(expectedUrlAfterLogin, actualUrlAfterLogin);
    }

    @Test(priority = 15)
    public void testManagerLogin() throws InterruptedException {
        String userName = loginData.get(2).get("username");
        String password = loginData.get(2).get("password");

        configurationMasterUserPage.login(userName, password);
        String expectedUrlAfterLogin = urlData.get(0).get("dashboardUrl");
        Thread.sleep(1000);
        String actualUrlAfterLogin = driver.getCurrentUrl();
        Assert.assertEquals(expectedUrlAfterLogin, actualUrlAfterLogin);
    }

    @Test(priority = 16)
    public void testAssetCreation() throws InterruptedException {
        String assertName = assetData.get(0).get("AssertName");
        String maintenanceRefNum = assetData.get(0).get("MaintenanceRefNum");
        String description = assetData.get(0).get("Description");
        String imagePath = assetData.get(0).get("ImagePath");
        String assetMsg = configurationAssertPage.createAssert(assertName, maintenanceRefNum, description, imagePath);
        Assert.assertEquals(assetMsg, "Published Successfully");
    }

    @Test(priority = 17)
    public void testSearchAssert() throws InterruptedException {
        String assertName = assetData.get(0).get("AssertName");
        boolean isValidAsset = configurationAssertPage.searchAssert(assertName);
        Assert.assertTrue(isValidAsset);
    }

    @Test(priority = 18)
    public void testCreateCheckTypes() throws InterruptedException {
        String checkType = checkTypesData.get(0).get("CheckType");
        String description = checkTypesData.get(0).get("Description");
        configurationCheckListPage.createCheckTypes(checkType, description);
        boolean isCheckTypesAdded = configurationCheckListPage.validateCheckTypes(checkType, description);
        Assert.assertTrue(isCheckTypesAdded);
    }

    @Test(priority = 19)
    public void testCreateChecks() throws InterruptedException {
        for (HashMap<String, String> checkData : checksData) {
            String checksName = checkData.get("ChecksName");
            String description = checkData.get("Description");
            String checksType = checkData.get("ChecksType");
            String priority = checkData.get("Priority");
            String successMessage = configurationCheckListPage.createChecks(checksName, description, checksType, priority);
            Assert.assertEquals("Added Successfully", successMessage);
        }
    }

    @Test(priority = 20)
    public void testCreateCheckList() throws InterruptedException {
        String assetName = checkListData.get(0).get("AssetName");
        String checkListName = checkListData.get(0).get("CheckListName");
        String description = checkListData.get(0).get("Description");
        List<String> CheckItemsList = new ArrayList<>();
        for (HashMap<String, String> checkData : checksData) {
            String checksName = checkData.get("ChecksName");
            CheckItemsList.add(checksName);
        }
        boolean successMsg = configurationCheckListPage.createCheckList(checkListName, assetName ,description, CheckItemsList);
        Assert.assertTrue(successMsg);
    }

    @Test(priority = 21)
    public void testValidateCheckList() throws InterruptedException {
        String assetName = checkListData.get(0).get("AssetName");
        String checkListName = checkListData.get(0).get("CheckListName");
        String description = checkListData.get(0).get("Description");
        boolean isValidChecks = configurationCheckListPage.validateCheckList(assetName, checkListName, description);
        Assert.assertTrue(isValidChecks);
    }

}
