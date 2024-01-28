package org.bytefactorydpmsautomation.PageObjects;

import org.bytefactorydpmsautomation.AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DashBoardPage extends AbstractComponent {
    WebDriver driver;
    int actualTicketCount = 0;
    public DashBoardPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//ul[@role='menu']//li[1]")
    WebElement dashboardEle;
    public List<Integer> checkListExecutionScheduledStatus() throws InterruptedException {
        Thread.sleep(2000);
        waitForWebElementToAppear(dashboardEle);
        dashboardEle.click();
        String scheduledXpath = "(//*[contains(@cy, \"18.776166666666672\")])[1]";
        return checkListExecutionStatus(scheduledXpath);
    }

    public List<Integer> checkListExecutionInProgressStatus() throws InterruptedException {
        Thread.sleep(2000);
        waitForWebElementToAppear(dashboardEle);
        dashboardEle.click();
        Thread.sleep(500);
        dashboardEle.click();
        String inProgressXpath = "(//*[contains(@cy, \"48.828500000000005\")])[1]";
        return checkListExecutionStatus(inProgressXpath);
    }

    public List<Integer> checkListExecutionClosedStatus() throws InterruptedException {
        Thread.sleep(2000);
        waitForWebElementToAppear(dashboardEle);
        dashboardEle.click();
        Thread.sleep(500);
        dashboardEle.click();
        String closedXpath = "(//*[contains(@cy, \"78.88083333333334\")])[1]";
        return checkListExecutionStatus(closedXpath);
    }


    public WebElement getDayEle(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        String dayOfWeek = currentDateTime.getDayOfWeek().toString();
        By xpath = null;
        switch (dayOfWeek){
            case "MONDAY":
                xpath = By.xpath("(//*[contains(@id, 'SvgjsText')])[8]");
            case "TUESDAY":
                xpath = By.xpath("(//*[contains(@id, 'SvgjsText')])[9]");
            case "WEDNESDAY":
                xpath = By.xpath("(//*[contains(@id, 'SvgjsText')])[10]");
            case "THURSDAY":
                xpath = By.xpath("(//*[contains(@id, 'SvgjsText')])[11]");
            case "FRIDAY":
                xpath = By.xpath("(//*[contains(@id, 'SvgjsText')])[12]");
            case "SATURDAY":
                xpath = By.xpath("(//*[contains(@id, 'SvgjsText')])[13]");
            case "SUNDAY":
                xpath = By.xpath("(//*[contains(@id, 'SvgjsText')])[14]");
        }
        return driver.findElement(xpath);
    }

//    @FindBy(xpath = "(//*[contains(@id, 'SvgjsText')])[10]")
//    WebElement ticketCountEle;
    public boolean checkUpdatedTicketCount() throws InterruptedException {
        Thread.sleep(2000);
        WebElement ticketCountEle = this.getDayEle();
        waitForWebElementToAppear(dashboardEle);
        dashboardEle.click();
        dashboardEle.click();
        waitForWebElementToAppear(ticketCountEle);
        String ticketCountText = ticketCountEle.getText();
        int updatedTicketCount = Integer.parseInt(ticketCountText);
        System.out.println(updatedTicketCount + " "+(actualTicketCount+1));
        return (actualTicketCount+1) == updatedTicketCount;
    }

    public int actualTicketCount() {
        WebElement ticketCountEle = this.getDayEle();
        waitForWebElementToAppear(dashboardEle);
        dashboardEle.click();
        waitForWebElementToAppear(ticketCountEle);
        String ticketCountText = ticketCountEle.getText();
        actualTicketCount = Integer.parseInt(ticketCountText);
        return actualTicketCount ;
    }

    public boolean resolutionWorkOrderStatus() throws InterruptedException {
        boolean hasNextPage = true;
        boolean foundText = false;
        while (hasNextPage) {
            List<WebElement> tableData =  driver.findElements(By.xpath("//tbody//tr"));
            for (int i = 0; i < tableData.size(); i++) {
                WebElement webElement = tableData.get(i);
                WebElement execNumElement;
                try {
                    execNumElement = webElement.findElement(By.xpath(".//td[2]"));
                } catch (StaleElementReferenceException e) {
                    System.out.println("Stale element reference encountered");
                    TimeUnit.SECONDS.sleep(3);
                    tableData = driver.findElements(By.xpath("//tbody//tr"));
                    webElement = tableData.get(i);
                    execNumElement = webElement.findElement(By.xpath(".//td[2]"));
                }
                boolean isValidExecNum = execNumElement.getText().equalsIgnoreCase(CheckListExecutionPage.getExecNum());
                if (isValidExecNum) {
                    foundText = true;
                    break;
                }
            }
            hasNextPage = false;
        }
        return foundText;
    }

    @FindBy(xpath = "//span[normalize-space()='Opened']")
    WebElement openedButtonEle;
    public boolean dashBoardRWOOpenedStatus() throws InterruptedException {
        waitForWebElementToAppear(dashboardEle);
        dashboardEle.click();
        Thread.sleep(1000);
        waitForWebElementToAppear(openedButtonEle);
        openedButtonEle.click();
        return resolutionWorkOrderStatus();
    }
    @FindBy(xpath = "//span[normalize-space()='Assigned']")
    WebElement assignedButtonEle;
    public boolean dashBoardRWOAssignedStatus() throws InterruptedException {
        waitForWebElementToAppear(dashboardEle);
        dashboardEle.click();
        Thread.sleep(1000);
        waitForWebElementToAppear(assignedButtonEle);
        assignedButtonEle.click();
        return resolutionWorkOrderStatus();
    }

    @FindBy(xpath = "//span[normalize-space()='Resolved']")
    WebElement resolvedButtonEle;
    public boolean dashBoardRWOResolvedStatus() throws InterruptedException {
        waitForWebElementToAppear(dashboardEle);
        dashboardEle.click();
        Thread.sleep(1000);
        waitForWebElementToAppear(resolvedButtonEle);
        resolvedButtonEle.click();
        return resolutionWorkOrderStatus();
    }

    @FindBy(xpath = "//span[normalize-space()='Rejected']")
    WebElement rejectedButtonEle;
    public boolean dashBoardRWORejectedStatus() throws InterruptedException {
        waitForWebElementToAppear(dashboardEle);
        dashboardEle.click();
        Thread.sleep(1000);
        waitForWebElementToAppear(rejectedButtonEle);
        rejectedButtonEle.click();
        return resolutionWorkOrderStatus();
    }

    @FindBy(xpath = "//span[normalize-space()='Completed']")
    WebElement completedButtonEle;
    public boolean dashBoardRWOCompletedStatus() throws InterruptedException {
        waitForWebElementToAppear(dashboardEle);
        dashboardEle.click();
        Thread.sleep(1000);
        waitForWebElementToAppear(completedButtonEle);
        completedButtonEle.click();
        return resolutionWorkOrderStatus();
    }

}
