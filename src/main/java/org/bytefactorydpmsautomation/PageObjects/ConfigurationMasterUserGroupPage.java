package org.bytefactorydpmsautomation.PageObjects;

import org.bytefactorydpmsautomation.AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;
import java.util.NoSuchElementException;

public class ConfigurationMasterUserGroupPage extends AbstractComponent {
    WebDriver driver;

    public ConfigurationMasterUserGroupPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[normalize-space()='User Group']")
    WebElement userGroupCreationEle;
    @FindBy(xpath = "//span[normalize-space()='Add']")
    WebElement addButtonEle;

    @FindBy(xpath = "//input[@id='userGroupName']")
    WebElement userGroupNameEle;
    @FindBy(xpath = "//input[@id='description']")
    WebElement getUserGroupDescriptionEle;
    @FindBy(xpath = "//input[@id='ahid']")
    WebElement getUserGroupSiteEle;
    @FindBy(xpath = "//span[@class='ant-select-tree-title']")
    WebElement BAFLEle;
    @FindBy(xpath = "//input[@id='userIds']")
    WebElement groupUsersDropDownEle;
    @FindBy(xpath = "//button[@type='submit']")
    WebElement saveButtonEle;

    @FindBy(xpath = "//ul[@role='menu']//li[2]")
    WebElement configurationEle;
    public void createUserGroup(String groupName, String description, List<String> groupMemberList) throws InterruptedException {
        configurationEle.click();
        Thread.sleep(500);
        userGroupCreationEle.click();
        addButtonEle.click();
        userGroupNameEle.sendKeys(groupName);
        getUserGroupDescriptionEle.sendKeys(description);
        getUserGroupSiteEle.click();
        BAFLEle.click();

        for (String user: groupMemberList) {
            groupUsersDropDownEle.click();
            groupUsersDropDownEle.sendKeys(user);
            WebElement member1 = driver.findElement(By.xpath("//div[contains(text(),'" + user + "')]"));
            waitForWebElementToAppear(member1);
            member1.click();
        }
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='none';", groupUsersDropDownEle);
        saveButtonEle.click();
    }

    @FindBy(xpath = "//tr[@data-testid='row']//td[2]")
    List<WebElement> userGroupList;

    @FindBy(xpath = "//li[@title='Next Page']//button")
    WebElement nextPageButton;

    public boolean validateUserGroup(String groupName) {
        boolean hasNextPage = true;
        boolean foundText = false;

        while (hasNextPage) {
            for (WebElement webElement : userGroupList) {
                if (webElement.getText().trim().equalsIgnoreCase(groupName)) {
                    foundText = true;
                    hasNextPage = false;
                    break;
                }
            }
            try {
                if (nextPageButton.isEnabled()) {
                    nextPageButton.click();

                } else {
                    hasNextPage = false;
                }
            } catch (NoSuchElementException e) {
                System.out.println("No next page button found. Exiting loop.");
                hasNextPage = false;
            }
        }
        return  foundText;
    }
    @FindBy(xpath = "//input[@placeholder='Search...']")
    WebElement searchEle;
    public boolean searchUserGroup(String groupName){
        waitForWebElementToAppear(searchEle);
        searchEle.sendKeys(groupName);
        return validateUserGroup(groupName);
    }
}
