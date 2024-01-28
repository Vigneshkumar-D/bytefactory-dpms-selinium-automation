package org.bytefactorydpmsautomation.PageObjects;

import org.bytefactorydpmsautomation.AbstractComponents.AbstractComponent;
import org.bytefactorydpmsautomation.AbstractComponents.InputFieldsValidation;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

public class ConfigurationMasterRolePage extends AbstractComponent {
    WebDriver driver;
    InputFieldsValidation inputFieldsValidation;
    public ConfigurationMasterRolePage(WebDriver driver) {
        super(driver);
        this.driver=driver;
        inputFieldsValidation = new InputFieldsValidation(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//ul[@role='menu']//li[2]")
    WebElement configurationEle;
    @FindBy(xpath = "(//div[@class='ant-row ant-row-space-between css-ru2fok'])//div[2]")
    WebElement addButton;
    @FindBy(xpath = "//input[@id='roleName']")
    WebElement roleNameEle;
    @FindBy(xpath = "//button[@type='submit']")
    WebElement submitButton;
//    @FindBy(xpath = "//input[@type='radio']")
//    List<WebElement> radioButtons;
    public void selectConfiguration(){
        waitForWebElementToAppear(configurationEle);
        configurationEle.click();
    }

    @FindBy(xpath = "//form[@class='ant-form ant-form-horizontal ant-form-small css-ru2fok form-horizontal']//input")
    List<WebElement> inputElementsList;
    public void createRole(String roleName, HashMap<String, String> inputValidationData) throws InterruptedException {
        addButton.click();
        Thread.sleep(1000);
        boolean isValidInputFields = inputFieldsValidation.validateRolePage(inputElementsList, inputValidationData);
        System.out.println(isValidInputFields);
        roleNameEle.sendKeys("");
        submitButton.click();
        boolean isValidMandatoryField = inputFieldsValidation.validateMandatoryFields();
        System.out.println(isValidMandatoryField);
        roleNameEle.sendKeys(roleName);
        submitButton.click();
        Thread.sleep(2000);
        System.out.println(isValidInputFields +" "+isValidMandatoryField);
        configurationEle.click();
    }

    @FindBy(xpath = "//tr[@data-testId='row']//td[2]")
    List<WebElement> roleList;
    @FindBy(xpath = "(//button[@class='ant-pagination-item-link'])[2]")
    WebElement nextPageButton;
    public boolean validateRole(String role){
        boolean hasNextPage = true;
        boolean foundText = false;
        while (hasNextPage) {
            for (WebElement webElement : roleList) {
                if (webElement.getText().trim().equalsIgnoreCase(role)) {
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
        return foundText;
    }

    @FindBy(xpath = "//input[@placeholder='Search...']")
    WebElement searchEle;
    public boolean searchRole(String roleName){
        waitForWebElementToAppear(searchEle);
        searchEle.sendKeys(roleName);
        return validateRole(roleName);
    }

    @FindBy(xpath = "//input[@id='roleName']")
    WebElement searchViewEle;
    public String viewRole(){
        configurationEle.click();
        return searchViewEle.getText();
    }

    @FindBy(xpath = "(//button[@class='ant-pagination-item-link'])[2]")
    WebElement nextPageButtonEle;
    public void validateViewRole(String role){
        boolean hasNextPage = true;
        boolean foundText = false;

        while (hasNextPage) {
            List<WebElement> elementList = driver.findElements(By.xpath("//tr[@data-testId='row']//td[2]"));
            for (WebElement webElement : elementList) {
                if (webElement.getText().trim().equalsIgnoreCase(role)) {
                    foundText = true;
                    searchViewEle.click();
                    break;
                }
            }

            try {
//                WebElement nextPageButton = driver.findElement(By.xpath("(//button[@class='ant-pagination-item-link'])[2]"));
                if (nextPageButtonEle.isEnabled()) {
                    nextPageButtonEle.click();

                } else {
                    hasNextPage = false;
                }
            } catch (NoSuchElementException e) {
                System.out.println("No next page button found. Exiting loop.");
                hasNextPage = false;
            }
        }
    }

}
