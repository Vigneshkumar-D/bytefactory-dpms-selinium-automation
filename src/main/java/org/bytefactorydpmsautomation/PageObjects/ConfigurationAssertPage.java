package org.bytefactorydpmsautomation.PageObjects;

import org.bytefactorydpmsautomation.AbstractComponents.AbstractComponent;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class ConfigurationAssertPage extends AbstractComponent {
    WebDriver driver;


    public ConfigurationAssertPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "(//tbody[@class=\"ant-table-tbody\"])[2]")
    List<WebElement> assertList;
    @FindBy(xpath = "(//button[@class='ant-pagination-item-link'])[4]")
    WebElement nextPageButton;
    @FindBy(xpath = "//li[@title='Next Page']//button[@type='button']")
    WebElement nextPageButtonEle;
    public boolean validateAssert(String assertName) throws InterruptedException {
        boolean hasNextPage = true;
        boolean foundText = false;
        while (hasNextPage) {
            List<WebElement> tableData =  driver.findElements(By.xpath("//tbody//tr"));
            for (int i = 0; i < tableData.size(); i++) {
                WebElement webElement = tableData.get(i);
                WebElement AssertNameElement;
                try {
                    AssertNameElement = webElement.findElement(By.xpath(".//td[3]"));
                } catch (StaleElementReferenceException e) {
                    System.out.println("Stale element reference encountered");
                    TimeUnit.SECONDS.sleep(3);
                    tableData = driver.findElements(By.xpath("//tbody//tr"));
                    webElement = tableData.get(i);
                    AssertNameElement = webElement.findElement(By.xpath(".//td[3]"));
                }
                boolean isValidAssert = AssertNameElement.getText().equalsIgnoreCase(assertName);
                if (isValidAssert) {
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

            hasNextPage = false;
        }
        return foundText;
    }

    @FindBy(xpath = "//ul[@role='menu']//li[2]")
    WebElement configurationEle;
    @FindBy(xpath = "//a[normalize-space()='Asset']")
    WebElement assertButtonEle;
    @FindBy(xpath = "(//span[contains(text(),'Add')])[2]")
    WebElement addButtonEle;
    @FindBy(xpath = "(//input[@id='assetName'])[2]")
    WebElement assertNameInputEle;
    @FindBy(xpath = "(//input[@id='ahid'])[2]")
    WebElement siteInputEle;
    @FindBy(xpath = "//span[@class='ant-select-tree-title']")
    WebElement bfalEle;
    @FindBy(xpath = "(//input[@id='maintananceReferenceNumber'])[2]")
    WebElement maintenanceReferenceNumberEle;
    @FindBy(xpath = "(//textarea[@id='description'])[2]")
    WebElement descriptionEle;
    @FindBy(xpath = "(//span[contains(text(),'Next')])[2]")
    WebElement nextButtonEle;
    @FindBy(xpath = "//p[@class='ant-upload-drag-icon']")
    WebElement imageUploadInputEle;
    @FindBy(xpath = "//div[@id='rc-tabs-0-panel-asset']//span[contains(text(),'Finish')]")
    WebElement finishButtonEle;
    @FindBy(xpath = "//span[normalize-space()='Published Successfully']")
    WebElement AssertPublishEle;

    public String createAssert(String assertName, String maintenanceRefNum, String description, String imagePath) throws InterruptedException {
        configurationEle.click();
        assertButtonEle.click();
        Thread.sleep(1000);
        waitForWebElementToAppear(addButtonEle);
        addButtonEle.click();
        Thread.sleep(2000);
        assertNameInputEle.sendKeys(assertName);
        siteInputEle.click();
        Thread.sleep(1000);
        bfalEle.click();

        waitForWebElementToAppear(maintenanceReferenceNumberEle);
        maintenanceReferenceNumberEle.sendKeys(maintenanceRefNum);
        descriptionEle.sendKeys(description);
        Thread.sleep(1000);
        nextButtonEle.click();
        //Image Uploading
        boolean clicked = false;
        int attempts = 0;
        while (!clicked && attempts < 3) {
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                WebElement fileInput = (WebElement) js.executeScript(
                        "return document.querySelector(\"input[type='file']\");");
                try {
                    fileInput = (WebElement) js.executeScript(
                            "return document.querySelector(\"input[type='file']\")");
                } catch (Exception e) {
                    System.out.println("Element not found or unable to interact with it.");
                }
                if (fileInput != null) {
                    js.executeScript("arguments[0].style.display='block';", fileInput);
                    js.executeScript("arguments[0].setAttribute('value', arguments[1]);", fileInput, imagePath);
                } else {
                    System.out.println("File input element not found.");
                }
                clicked = true;
            } catch (StaleElementReferenceException e) {
                attempts++;
            }
        }
        Thread.sleep(1000);
        System.out.println(imagePath);
        nextButtonEle.click();
        Thread.sleep(2000);
        waitForWebElementToAppear(finishButtonEle);
        finishButtonEle.click();
        Thread.sleep(2000);
        waitForWebElementToAppear(AssertPublishEle);
        return AssertPublishEle.getText();
    }

//    @FindBy(xpath = "(//input[@placeholder='Search...'])[2]")
//    WebElement searchInputEle;
    public boolean searchAssert(String assertName) throws InterruptedException {
        Thread.sleep(1000);
//        waitForWebElementToAppear(searchInputEle);
//        searchInputEle.sendKeys(assertName);
        return validateAssert(assertName);
    }
}
