package org.bytefactorydpmsautomation.PageObjects;

import org.apache.poi.ss.formula.functions.WeekNum;
import org.bytefactorydpmsautomation.AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;
import java.util.NoSuchElementException;

public class SchedulerPage extends AbstractComponent {
    WebDriver driver;
    public static String schedulerId;
    public SchedulerPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//ul[@role='menu']//li[3]")
    WebElement schedulerEle;

    @FindBy(xpath = "//input[@id='userId']")
    WebElement assignedToEle;
    @FindBy(xpath = "//div[@class='ant-select-selection-overflow']")
    WebElement checkListEle;
    @FindBy(xpath = "//input[@id='frequency']")
    WebElement frequencyEle;

    @FindBy(xpath = "//input[@id='scheduleDate']")
    WebElement startDateEle;
    @FindBy(xpath = "//input[@id='endDate']")
    WebElement endDateEle;
    @FindBy(xpath = "//input[@id='assetId']")
    WebElement assertEle;
//    @FindBy(xpath = "//table[@class='ant-picker-content']")
//    WebElement startDateEle;
    @FindBy(xpath = "//input[@id='startTime']")
    WebElement startTimeEle;
    @FindBy(xpath = "(//span[contains(text(),'OK')])[3]")
    WebElement startTimeOkButtonEle;
//    @FindBy(xpath = "//a[@class='ant-picker-now-btn'][normalize-space()='Now']")
//    WebElement nowEle;
    @FindBy(xpath = "//input[@id='endTime']")
    WebElement endTimeEle;
    @FindBy(xpath = "(//span[contains(text(),'OK')])[4]")
    WebElement endTimeOkButtonEle;
    @FindBy(xpath = "//textarea[@id='description']")
    WebElement descriptionEle;
    @FindBy(xpath = "//span[normalize-space()='Save']")
    WebElement saveButtonEle;
    @FindBy(xpath = "(//span[normalize-space()='OK'])[1]")
    WebElement startDateOkButtonEle;

    @FindBy(xpath = "(//span[normalize-space()='OK'])[2]")
    WebElement endDateOkButtonEle;
    @FindBy(xpath = "//input[@id='assetId']")
    WebElement AssertInputEle;
    public void createScheduler(String schedulerDate,String assertName, String checkList, String frequency, String assignedToUser,
                                String startDate, String endDate, String startTime, String endTime, String description) throws InterruptedException {
        waitForWebElementToAppear(schedulerEle);
        schedulerEle.click();
        Thread.sleep(1000);

        Thread.sleep(1000);
        String schedulerDay = schedulerDate.substring(0,2);
        WebElement schedulerDateEle = driver.findElement(By.xpath("//button[normalize-space()='"+schedulerDay+"']/.."));
        schedulerDateEle.click();

        waitForWebElementToAppear(AssertInputEle);
        AssertInputEle.sendKeys(assertName);
        WebElement assertNameElement = driver.findElement(By.xpath("//div[contains(text(),'" + assertName + "')]"));
        assertNameElement.click();

        checkListEle.click();
        WebElement checkListInput = driver.findElement(By.xpath("//div[contains(text(),'" + checkList + "')]"));
        waitForWebElementToAppear(checkListInput);
        checkListInput.click();

        frequencyEle.click();
        WebElement frequencyInput = driver.findElement(By.xpath("//div[@class='ant-select-item-option-content'][normalize-space()='" + frequency + "']"));
        waitForWebElementToAppear(frequencyInput);
        frequencyInput.click();

        assignedToEle.click();
        assignedToEle.sendKeys(assignedToUser);
        WebElement assignedToUserInput = driver.findElement(By.xpath("//div[contains(text(),'" + assignedToUser + "')]"));
        waitForWebElementToAppear(assignedToUserInput);
        assignedToUserInput.click();

        startDateEle.click();
        startDateEle.clear();
        startDateEle.sendKeys(startDate);
        Thread.sleep(1000);
        startDateOkButtonEle.click();

        endDateEle.click();
        endDateEle.clear();
        endDateEle.sendKeys(endDate);
        Thread.sleep(1000);
        endDateOkButtonEle.click();


        startTimeEle.click();
        startTimeEle.sendKeys(startTime);
        startTimeOkButtonEle.click();
        Thread.sleep(1000);

        endTimeEle.sendKeys(endTime);
        endTimeOkButtonEle.click();

        descriptionEle.sendKeys(description);
        saveButtonEle.click();
    }

    @FindBy(xpath = "//span[normalize-space()='Day']")
    WebElement dayButtonEle;
    @FindBy(xpath = "//div[@class='rbc-events-container']/div")
    List<WebElement> todaySchedulersEle;
//    @FindBy(xpath = ".//h3[@style='margin-bottom: 1px;']")
//    WebElement subElement;

    @FindBy(xpath = "//div[@class='ant-card-head-title']")
    WebElement schedulerIdEle;
    @FindBy(xpath = "(//div[@class=\"rbc-event-content\"])[1]")
    WebElement daySchedulerEle;

    public boolean validateScheduler(String assignedToUser, String Description) throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("scheduler");
        waitForWebElementToAppear(dayButtonEle);
        Actions actions = new Actions(driver);
        actions.moveToElement(dayButtonEle).click().perform();
        boolean foundText = false;
        for (WebElement webElement : todaySchedulersEle) {
            WebElement headingElement = webElement.findElement(By.xpath(".//h3[@style='margin-bottom: 1px;']"));
            WebElement paraElement = webElement.findElement(By.xpath(".//p[@style='margin-bottom: 0px;']"));
            System.out.println(headingElement.getText());
            try {
                if (headingElement.getText().equalsIgnoreCase(assignedToUser) && paraElement.getText().equalsIgnoreCase(Description)) {
                    paraElement.click();
                    waitForWebElementToAppear(schedulerIdEle);
                    schedulerId = schedulerIdEle.getText();
                    foundText = true;
                    break;
                }
            } catch (NoSuchElementException e) {
                System.out.println("h3 element not found within this div");
            }
        }
        return foundText;
    }
}
