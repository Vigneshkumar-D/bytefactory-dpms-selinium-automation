package org.bytefactorydpmsautomation.PageObjects;

import org.bytefactorydpmsautomation.AbstractComponents.AbstractComponent;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class ConfigurationCheckListPage extends AbstractComponent {
    WebDriver driver;

    public ConfigurationCheckListPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//ul[@role='menu']//li[2]")
    WebElement configurationEle;
    @FindBy(xpath = "//a[normalize-space()='Checklist']")
    WebElement checkListButtonEle;
    @FindBy(xpath = "//div[@id=\"rc-tabs-0-panel-checklist\"]//span[contains(text(),'CheckTypes')]")
    WebElement checkTypesButtonEle;
    @FindBy(xpath = "//div[@id=\"rc-tabs-0-panel-checklist\"]//span[contains(text(),'Add')]")
    WebElement checkTypesAddButtonEle;
    @FindBy(xpath = "//input[@id='checkTypeName']")
    WebElement checkTypesNameEle;
    //    @FindBy(xpath = "//input[@id=\"assets\"]")
//    WebElement assetNameInputEle;
    @FindBy(xpath = "//textarea[@id='description']")
    WebElement descriptionInputEle;
    @FindBy(xpath = "//span[normalize-space()='Save']")
    WebElement saveButtonEle;

    public void createCheckTypes(String checkType, String description) throws InterruptedException {
        waitForWebElementToAppear(configurationEle);
        configurationEle.click();
        Thread.sleep(1000);
        waitForWebElementToAppear(checkListButtonEle);
        checkListButtonEle.click();
        Thread.sleep(1000);
        waitForWebElementToAppear(checkTypesButtonEle);
        checkTypesButtonEle.click();
        waitForWebElementToAppear(checkTypesAddButtonEle);
        checkTypesAddButtonEle.click();
        waitForWebElementToAppear(checkTypesNameEle);
        checkTypesNameEle.sendKeys(checkType);
        waitForWebElementToAppear(descriptionInputEle);
        descriptionInputEle.sendKeys(description);
        saveButtonEle.click();
    }

    public boolean validateCheckTypes(String checksType, String description) throws InterruptedException {
        Thread.sleep(2000);
        boolean hasNextPage = true;
        boolean foundText = false;
        while (hasNextPage) {
            List<WebElement> tableData = driver.findElements(By.xpath("//tbody//tr"));
            for (int i = 0; i < tableData.size(); i++) {
                WebElement webElement = tableData.get(i);
                WebElement checksTypeEle;
                WebElement descriptionEle;
                try {
                    checksTypeEle = webElement.findElement(By.xpath(".//td[2]"));
                    descriptionEle = webElement.findElement(By.xpath(".//td[3]"));
                } catch (StaleElementReferenceException e) {
                    System.out.println("Stale element reference encountered");
                    TimeUnit.SECONDS.sleep(1);
                    tableData = driver.findElements(By.xpath("//tbody//tr"));
                    webElement = tableData.get(i);
                    checksTypeEle = webElement.findElement(By.xpath(".//td[2]"));
                    descriptionEle = webElement.findElement(By.xpath(".//td[3]"));

                }
                System.out.println(checksTypeEle.getText() + " " + descriptionEle.getText());
                boolean isValidChecksName = checksTypeEle.getText().equalsIgnoreCase(checksType);
                boolean isValidDescription = descriptionEle.getText().equalsIgnoreCase(description);
                if (isValidChecksName && isValidDescription) {
                    foundText = true;
                    break;
                }
            }
            hasNextPage = false;
        }
        return foundText;
    }

    @FindBy(xpath = "//div[@id=\"rc-tabs-0-panel-checklist\"]//span[normalize-space()='Checks']")
    WebElement checksButtonEle;
    @FindBy(xpath = "//div[@id=\"rc-tabs-0-panel-checklist\"]//span[contains(text(),'Add')]")
    WebElement checksAddButtonEle;
    @FindBy(xpath = "//input[@id='checkName']")
    WebElement checkNameInputEle;
    @FindBy(xpath = "//input[@id='checkType']")
    WebElement checkTypeInputEle;
    @FindBy(xpath = "//input[@id='priority']")
    WebElement priorityInputEle;
    @FindBy(xpath = "//span[normalize-space()='Added Successfully']")
    WebElement checksAddedSuccessMsg;

    public String createChecks(String checksName, String description, String checksType, String priority) throws InterruptedException {
        checkListButtonEle.click();
        waitForWebElementToAppear(checksButtonEle);
        checksButtonEle.click();
        waitForWebElementToAppear(checksAddButtonEle);
        checksAddButtonEle.click();
        checkNameInputEle.sendKeys(checksName);
        checkTypeInputEle.click();
        WebElement checkType = driver.findElement(By.xpath("//div[contains(text(),'" + checksType + "')]"));
        waitForWebElementToAppear(checkType);
        checkType.click();
        descriptionInputEle.sendKeys(description);
        priorityInputEle.sendKeys(priority);
        WebElement priorityEle = driver.findElement(By.xpath("//div[contains(text(),'" + priority + "')]"));
        waitForWebElementToAppear(priorityEle);
        priorityEle.click();
        saveButtonEle.click();
        Thread.sleep(500);
        return checksAddedSuccessMsg.getText();
    }

    public boolean validateChecks(String checksName, String description, String priority) throws InterruptedException {
        Thread.sleep(2000);
        boolean hasNextPage = true;
        boolean foundText = false;
        while (hasNextPage) {
            List<WebElement> tableData = driver.findElements(By.xpath("//tbody//tr"));
            for (int i = 0; i < tableData.size(); i++) {
                WebElement webElement = tableData.get(i);
                WebElement checksNameEle;
                WebElement descriptionEle;
                WebElement priorityEle;

                try {
                    checksNameEle = webElement.findElement(By.xpath(".//td[2]"));
                    descriptionEle = webElement.findElement(By.xpath(".//td[3]"));
                    priorityEle = webElement.findElement(By.xpath(".//td[4]"));
                } catch (StaleElementReferenceException e) {
                    System.out.println("Stale element reference encountered");
//                    Thread.sleep(500);
                    tableData = driver.findElements(By.xpath("//tbody//tr"));
                    webElement = tableData.get(i);
                    checksNameEle = webElement.findElement(By.xpath(".//td[2]"));
                    descriptionEle = webElement.findElement(By.xpath(".//td[3]"));
                    priorityEle = webElement.findElement(By.xpath(".//td[4]"));
                }
                boolean isValidChecksName = checksNameEle.getText().equalsIgnoreCase(checksName);
                boolean isValidDescription = descriptionEle.getText().equalsIgnoreCase(description);
                boolean isValidPriority = priorityEle.getText().equalsIgnoreCase(priority);
                if (isValidChecksName && isValidDescription && isValidPriority) {
                    foundText = true;
                    hasNextPage = false;
                    break;
                }
            }
            try {
                WebElement nextPageButton = driver.findElement(By.xpath("(//button[@class=\"ant-pagination-item-link\"])[6]"));
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


    @FindBy(xpath = "//div[@id=\"rc-tabs-0-panel-checklist\"]//span[contains(text(),'Add')]")
    WebElement checkListAddButtonEle;
    @FindBy(xpath = "//div[@id=\"rc-tabs-0-panel-checklist\"]//input[@id='checkListName']")
    WebElement checkListNameInputEle;
    @FindBy(xpath = "//div[@id=\"rc-tabs-0-panel-checklist\"]//input[@id='assets']")
    WebElement assetNameInputEle;
    @FindBy(xpath = "//div[@id=\"rc-tabs-0-panel-checklist\"]//textarea[@id='description']")
    WebElement checkListDescriptionEle;
    @FindBy(xpath = "//div[@id=\"rc-tabs-0-panel-checklist\"]//span[contains(text(),'Next')]")
    WebElement nextButtonEle;
    @FindBy(xpath = "//span[normalize-space()='Add / Remove Check Items']")
    WebElement addRemoveCheckItemsBtnEle;
    @FindBy(xpath = "//input[@placeholder='Search...']")
    WebElement searchChecksEle;
    @FindBy(xpath = "//div[@id=\"rc-tabs-0-panel-checklist\"](//input[@type='checkbox'])[2]")
    WebElement checkboxEle;
    @FindBy(xpath = "//span[normalize-space()='OK']")
    WebElement okButtonEle;

    public boolean createCheckList(String checkListName, String assetName, String description, List<String> CheckItemsList) throws InterruptedException {
        configurationEle.click();
        checkListButtonEle.click();
        waitForWebElementToAppear(checkListAddButtonEle);
        checkListAddButtonEle.click();

        waitForWebElementToAppear(checkListNameInputEle);
        checkListNameInputEle.sendKeys(checkListName);
        assetNameInputEle.click();
        Thread.sleep(500);
        WebElement desiredOption = null;
//        try {
        do {
//                Actions actions = new Actions(driver);
//                actions.sendKeys(Keys.ARROW_DOWN).build().perform();
            try {
                desiredOption = driver.findElement(By.xpath("//div[contains(text(),'" + assetName + "')]"));
            } catch (Exception e) {
                continue;
            }
        } while (desiredOption == null || !desiredOption.isDisplayed());
        desiredOption.click();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='none';", assetNameInputEle);

        waitForWebElementToAppear(checkListDescriptionEle);
        checkListDescriptionEle.sendKeys(description);
        nextButtonEle.click();
        waitForWebElementToAppear(addRemoveCheckItemsBtnEle);
        addRemoveCheckItemsBtnEle.click();
        Thread.sleep(3000);
        List<WebElement> tableRowList = driver.findElements(By.xpath("//tbody//tr//td[2]"));
        List<WebElement> checkBoxList = driver.findElements(By.xpath("//tbody//tr//td[1]"));

        for (String checkItems : CheckItemsList) {
            for (int i = 0; i < tableRowList.size(); i++) {
                WebElement webElement = tableRowList.get(i);
                if(webElement.getText().equalsIgnoreCase(checkItems)){
                    System.out.println(webElement.getText());
                    WebElement checkBoxEle = checkBoxList.get(i+1);
                    checkBoxEle.click();
                }
            }
        }
        waitForWebElementToAppear(okButtonEle);
        okButtonEle.click();
        waitForWebElementToAppear(saveButtonEle);
        saveButtonEle.click();
        boolean result = false;

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        WebElement checksAddedSuccessMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[normalize-space()='Added Successfully']")));
        if (checksAddedSuccessMsg.isDisplayed()){
            result = true;
        }
        return result;
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//            waitForWebElementToAppear(searchChecksEle);
//            searchChecksEle.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
//            wait.until(ExpectedConditions.textToBePresentInElementValue(searchChecksEle, ""));
//            searchChecksEle.sendKeys(checkItems);
//            Thread.sleep(1000);
//
//            checkboxEle.click();
//
//            searchChecksEle.clear();
//            wait.until(ExpectedConditions.textToBePresentInElementValue(searchChecksEle, ""));
//            Thread.sleep(2000);
    }

    @FindBy(xpath = "(//input[@id='ahId'])[2]")
    WebElement siteInputEle;
    @FindBy(xpath = "//span[contains(text(),'BFAL')]")
    WebElement bfalEle;
    @FindBy(xpath = "(//input[@id='assetId'])[2]")
    WebElement assetNameEle;
    @FindBy(xpath = "(//span[contains(text(),'Go')])[2]")
    WebElement goButtonEle;
    public boolean validateCheckList(String assetName, String checkListName, String description) throws InterruptedException {
//        Thread.sleep(2000);
//        siteInputEle.click();
//        waitForWebElementToAppear(bfalEle);
//        bfalEle.click();
//        assetNameEle.sendKeys(assetName);
//        WebElement assetEle = driver.findElement(By.xpath("//div[contains(text(),'" + assetName +"')]"));
//        waitForWebElementToAppear(assetEle);
//        assetEle.click();
//        goButtonEle.click();
//        Thread.sleep(1000);

        boolean hasNextPage = true;
        boolean foundText = false;
        while (hasNextPage) {
            List<WebElement> tableData = driver.findElements(By.xpath("//tbody//tr"));
            for (int i = 0; i < tableData.size(); i++) {
                WebElement webElement = tableData.get(i);
                WebElement checkListNameEle;
                WebElement assetNameEle;
                WebElement descriptionEle;
                try {
                    checkListNameEle = webElement.findElement(By.xpath(".//td[2]"));
                    assetNameEle = webElement.findElement(By.xpath(".//td[3]"));
                    descriptionEle = webElement.findElement(By.xpath(".//td[4]"));
                } catch (StaleElementReferenceException e) {
                    System.out.println("Stale element reference encountered");
                    TimeUnit.SECONDS.sleep(3);
                    tableData = driver.findElements(By.xpath("//tbody//tr"));
                    webElement = tableData.get(i);
                    checkListNameEle = webElement.findElement(By.xpath(".//td[2]"));
                    assetNameEle = webElement.findElement(By.xpath(".//td[3]"));
                    descriptionEle = webElement.findElement(By.xpath(".//td[4]"));
                }
                boolean isValidCheckListName = checkListNameEle.getText().equalsIgnoreCase(checkListName);
                boolean isValidDescription = descriptionEle.getText().equalsIgnoreCase(description);
                boolean isValidAsset = assetNameEle.getText().equalsIgnoreCase(assetName);
                if (isValidCheckListName && isValidDescription && isValidAsset) {
                    foundText = true;
                    break;
                }
            }
            try {
                WebElement nextPageButton = driver.findElement(By.xpath("(//button[@class=\"ant-pagination-item-link\"])[6]"));
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
}
