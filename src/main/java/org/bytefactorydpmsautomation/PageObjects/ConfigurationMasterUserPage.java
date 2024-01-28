package org.bytefactorydpmsautomation.PageObjects;

import org.bytefactorydpmsautomation.AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;
import java.util.NoSuchElementException;

public class ConfigurationMasterUserPage extends AbstractComponent {
    WebDriver driver;
    public ConfigurationMasterUserPage(WebDriver driver) {
        super(driver);
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[normalize-space()='User']")
    WebElement userEle;
    @FindBy(xpath = "(//button[@class='ant-btn css-ru2fok ant-btn-primary'])[1]")
    WebElement addButton;
    @FindBy(xpath = "(//input[@id='userName'])")
    WebElement userNameEle;
    @FindBy(xpath = "//input[@id='roleId']")
    WebElement roleNameEle;
    @FindBy(xpath = "//input[@id='email']")
    WebElement emailEle;
    @FindBy(xpath = "//input[@id='contactNumber']")
    WebElement mobileNumberEle;
    @FindBy(xpath = "//input[@id='password']")
    WebElement passwordEle;
    @FindBy(xpath = "//input[@id='ahid']")
    WebElement userSiteEle;
    @FindBy(xpath = "//span[@class='ant-select-tree-title']")
    WebElement userBFALEle;
    @FindBy(xpath = "(//button[@type='submit'])[1]")
    WebElement saveButtonEle;
    public void createUser(String userName, String role, String email, String mobileNum, String password){

        userEle.click();
        addButton.click();
        userNameEle.sendKeys(userName);
        roleNameEle.click();
        try {
            WebElement desiredOption = null;
            do {
                Actions actions = new Actions(driver);
                actions.sendKeys(Keys.ARROW_DOWN).build().perform();
                desiredOption =driver.findElement(By.xpath("//div[contains(text(),'" + role + "')]"));
//                waitForWebElementToAppear(desiredOption);
            } while (!desiredOption.isDisplayed());
            desiredOption.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
        emailEle.sendKeys(email);
        mobileNumberEle.sendKeys(mobileNum);
        passwordEle.sendKeys(password);
        userSiteEle.click();
        userBFALEle.click();
        saveButtonEle.click();
    }

    @FindBy(xpath = "//tr[@data-testId='row']//td[2]")
    List<WebElement> userList;
    @FindBy(xpath = "//li[@title='Next Page']//button[@type='button']")
    WebElement nextPageButtonEle;
    @FindBy(xpath = "//a[@rel='nofollow']")
    List<WebElement> currentPage;
    public Boolean validateUser(String userName){
        boolean hasNextPage = true;
        boolean foundText = false;
        while (hasNextPage) {
            for (WebElement webElement : userList) {
                if (webElement.getText().trim().equalsIgnoreCase(userName)) {
                    foundText = true;
                    hasNextPage = false;
                    break;
                }
            }
            try {
                if (nextPageButtonEle.isEnabled()) {
                    Thread.sleep(1000);
                    nextPageButtonEle.click();
                } else {
                    hasNextPage = false;
                }
            } catch (NoSuchElementException e) {
                System.out.println("No next page button found. Exiting loop.");
                hasNextPage = false;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(foundText){
                break;
            }
        }
        if(currentPage.size()>1){
            currentPage.get(0).click();
        }
        return foundText;
    }
    @FindBy(xpath = "//input[@placeholder='Search...']")
    WebElement searchEle;
    public boolean searchUser(String userName){
        waitForWebElementToAppear(searchEle);
        searchEle.sendKeys(userName);
        return validateUser(userName);
    }

    public void updateUser(String userName, String role, String email, String mobileNum, String site){
        userNameEle.sendKeys(userName);
        roleNameEle.click();
        try {
            WebElement desiredOption = null;
            do {
                Actions actions = new Actions(driver);
                actions.sendKeys(Keys.ARROW_DOWN).build().perform();
                desiredOption =driver.findElement(By.xpath("//div[contains(text(),'" + role + "')]"));
//                waitForWebElementToAppear(desiredOption);
            } while (!desiredOption.isDisplayed());
            desiredOption.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
        emailEle.sendKeys(email);
        mobileNumberEle.sendKeys(mobileNum);
        userSiteEle.click();
        userBFALEle.click();
        saveButtonEle.click();
    }

    public boolean editUser(String userName, String role, String email, String mobileNum, String site){
        updateUser(userName, role, email, mobileNum, site);
        return validateUser(userName);
    }

    @FindBy(xpath = "//span[@aria-label='delete']")
    WebElement deleteButtonEle;
    @FindBy(xpath = "//span[normalize-space()='Yes']")
    WebElement yesButtonEle;
    public void findUser(String userName){
        boolean hasNextPage = true;
        boolean foundText = false;

        while (hasNextPage) {
            for (WebElement webElement : userList) {
                if (webElement.getText().trim().equalsIgnoreCase(userName)) {
                    foundText = true;
                    deleteButtonEle.click();
                    yesButtonEle.click();
                    hasNextPage = false;
                    break;
                }
            }
            try {
                if (nextPageButtonEle.isEnabled()) {
                    nextPageButtonEle.click();
                } else {
                    hasNextPage = false;
                }
            } catch (NoSuchElementException e) {
                System.out.println("No next page button found. Exiting loop.");
                hasNextPage = false;
            }
            if(foundText){
                break;
            }
        }
        if(currentPage.size()>1){
            currentPage.get(0).click();
        }
    }
    public boolean deleteUser(String userName){
        findUser(userName);
        return validateUser(userName);
    }


}
