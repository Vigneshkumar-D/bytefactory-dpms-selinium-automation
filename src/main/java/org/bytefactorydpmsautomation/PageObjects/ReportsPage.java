package org.bytefactorydpmsautomation.PageObjects;

import org.bytefactorydpmsautomation.AbstractComponents.AbstractComponent;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ReportsPage extends AbstractComponent {
    WebDriver driver;
    public ReportsPage(WebDriver driver){
        super(driver);
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "(//button[@class='ant-pagination-item-link'])[2]")
    WebElement nextPageButtonEle;

    public String getWoNum(){
        String workOrderNum = null;
        boolean hasNextPage = true;
        while (hasNextPage) {
            List<WebElement> tableData = driver.findElements(By.xpath("//tbody//tr//td[2]"));
            for (int i = 0; i < tableData.size(); i++) {
                WebElement rowData = tableData.get(i);
                boolean isValidExecNum = rowData.getText().equalsIgnoreCase(CheckListExecutionPage.getExecNum());
                if (isValidExecNum) {
                    workOrderNum = rowData.findElement(By.xpath(".//td[2]")).getText();
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
        }
        return workOrderNum;
    }

    @FindBy(xpath = "//ul[@role='menu']//li[6]")
    WebElement reportsEle;
    public boolean reportsStatus(String status, String woNum) throws InterruptedException {
        Thread.sleep(1000);
        waitForWebElementToAppear(reportsEle);
        Actions actions = new Actions(driver);
        actions.moveToElement(reportsEle).click().perform();
        reportsEle.click();
        boolean hasNextPage = true;
        boolean foundText = false;

        while (hasNextPage) {
            List<WebElement> tableData =  driver.findElements(By.xpath("//tbody//tr"));
            try {
                for (int i = 0; i < tableData.size(); i++) {
                    WebElement rowData = tableData.get(i);
                    WebElement woNumElement;
                    WebElement statusElement;

                    try {
                        woNumElement = rowData.findElement(By.xpath(".//td[2]"));
                        statusElement = rowData.findElement(By.xpath(".//td[6]"));
                    } catch (StaleElementReferenceException e) {
                        System.out.println("Stale element reference encountered");
                        tableData = driver.findElements(By.xpath("//tbody//tr"));
                        rowData = tableData.get(i);
                        TimeUnit.SECONDS.sleep(3);
                        woNumElement = rowData.findElement(By.xpath(".//td[2]"));
                        statusElement = rowData.findElement(By.xpath(".//td[6]"));
                    }
                    boolean isValidWoNum = woNumElement.getText().equalsIgnoreCase(woNum);
                    boolean isValidStatus = statusElement.getText().equalsIgnoreCase(status);
                    System.out.println(isValidWoNum+ " " + isValidStatus);
                    if (isValidWoNum && isValidStatus) {
                        System.out.println("Found Element: "+woNumElement.getText() + " " + statusElement.getText());
                        foundText = true;
                        hasNextPage = false;
                        break;
                    }
                }
                hasNextPage = false;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return  foundText;
    }
    @FindBy(xpath = "")
    WebElement ele;
    public boolean checkReportOpenedStatus(String status, String woNum) throws InterruptedException {
        Thread.sleep(1000);
        return reportsStatus(status, woNum);
    }
    public boolean checkReportAssignedStatus(String status, String woNum) throws InterruptedException {
        Thread.sleep(1000);
        return reportsStatus(status, woNum);
    }
    public boolean checkReportResolvedStatus(String status, String woNum) throws InterruptedException {
        Thread.sleep(1000);
        return reportsStatus(status, woNum);
    }
    public boolean checkReportRejectedStatus(String status, String woNum) throws InterruptedException {
        Thread.sleep(1000);
        return reportsStatus(status, woNum);
    }
    public boolean checkReportCompletedStatus(String status, String woNum) throws InterruptedException {
        Thread.sleep(1000);
        return reportsStatus(status, woNum);
    }
}
