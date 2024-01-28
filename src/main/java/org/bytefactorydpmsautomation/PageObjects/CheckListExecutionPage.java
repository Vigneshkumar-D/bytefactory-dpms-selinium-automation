package org.bytefactorydpmsautomation.PageObjects;

import lombok.Getter;
import org.bytefactorydpmsautomation.AbstractComponents.AbstractComponent;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.*;
import java.util.NoSuchElementException;

public class CheckListExecutionPage extends AbstractComponent {
    WebDriver driver;
    @Getter
    static String execNum;
    int ticketCount = 0;
    DashBoardPage dashBoardPage;

    public CheckListExecutionPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
        dashBoardPage = new DashBoardPage(driver);
    }

    @FindBy(xpath = "//ul[@role='menu']//li[4]")
    WebElement checkListExeButtonEle;
    @FindBy(xpath = "//ul[@role='menu']//li[1]")
    WebElement dashBoardButtonEle;

    public boolean validateChecklistExecution(String assertName, String assignedToUser, String description) throws InterruptedException {
        waitForWebElementToAppear(checkListExeButtonEle);
//        dashBoardButtonEle.click();
        checkListExeButtonEle.click();
        return checkTableData(assertName, assignedToUser, description);
    }

    public Boolean checkTableData(String assertName, String assignedToUser, String description) throws InterruptedException {
        boolean hasNextPage = true;
        boolean foundText = false;
        List<WebElement> descriptionList = driver.findElements(By.xpath("//tbody//tr//td[5]"));
        List<WebElement> assertNameList = driver.findElements(By.xpath("//tbody//tr//td[6]"));
        List<WebElement>  assignedToList = driver.findElements(By.xpath("//tbody//tr//td[7]"));
        List<WebElement>  execNumList = driver.findElements(By.xpath("//tbody//tr//td[2]"));

        while (hasNextPage) {
            for (int i = 0; i < assignedToList.size(); i++) {
                WebElement assignedToElement;
                WebElement descriptionElement;
                WebElement assertNameElement;
                try {
                    descriptionElement = descriptionList.get(i);
                    assertNameElement = assertNameList.get(i);
                    assignedToElement = assignedToList.get(i);
                } catch (StaleElementReferenceException e) {
                    System.out.println("Stale element reference encountered");
                    Thread.sleep(1000);
                    descriptionElement = descriptionList.get(i);
                    assertNameElement = assertNameList.get(i);
                    assignedToElement = assignedToList.get(i);

                }
                Boolean isValidRole = assignedToElement.getText().equalsIgnoreCase(assignedToUser);
                Boolean isValidDes = descriptionElement.getText().equalsIgnoreCase(description);
                Boolean isValidAssert = assertNameElement.getText().equalsIgnoreCase(assertName);
                System.out.println(assignedToElement.getText()+" "+descriptionElement.getText()+" "+assertNameElement.getText());
                if (isValidRole && isValidDes && isValidAssert) {
                    execNum = execNumList.get(i).getText();
                    System.out.println(execNum);
                    foundText = true;
                    hasNextPage = false;
                    break;
                }
            }
            if(!hasNextPage){
                try {
                    WebElement nextPageButton = driver.findElement(By.xpath("(//button[@class=\"ant-pagination-item-link\"])[2]"));
                    if (nextPageButton.isEnabled()) {
                        nextPageButton.click();
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("No next page button found. Exiting loop.");
                }
            }
        }
        return foundText;
    }

    @FindBy(xpath = "//span[normalize-space()='Start']")
    WebElement startButtonEle;

    @FindBy(xpath = "//ul[@role='menu']//li[5]")
    WebElement rwoButtonEle;

    @FindBy(xpath = "//span[normalize-space()='Start Date']")
    WebElement orderEle;

    public void updateCheckListExecution(String assertName, String assignedToUser, String description, String status) throws InterruptedException {
        waitForWebElementToAppear(rwoButtonEle);
        rwoButtonEle.click();
        waitForWebElementToAppear(checkListExeButtonEle);
        checkListExeButtonEle.click();
        Thread.sleep(1000);
        waitForWebElementToAppear(orderEle);
        orderEle.click();
        Thread.sleep(1000);
        orderEle.click();
        boolean hasNextPage = true;
        List<WebElement> tableDataList = driver.findElements(By.xpath("//tbody//tr"));
        while (hasNextPage) {
            for (WebElement rowDataEle : tableDataList) {
                WebElement assignedToElement;
                WebElement descriptionElement;
                WebElement assertNameElement;
                WebElement openElement;
                Thread.sleep(1000);
                try {
                    descriptionElement = rowDataEle.findElement(By.xpath(".//td[5]"));
                    assertNameElement = rowDataEle.findElement(By.xpath(".//td[6]"));
                    assignedToElement = rowDataEle.findElement(By.xpath(".//td[7]"));
                    openElement = rowDataEle.findElement(By.xpath("//span[contains(text(),'Open')]"));
                } catch (StaleElementReferenceException | NoSuchElementException e) {
                    System.out.println("Stale element reference encountered");
                    Thread.sleep(1000);
                    descriptionElement = rowDataEle.findElement(By.xpath(".//td[5]"));
                    assertNameElement = rowDataEle.findElement(By.xpath(".//td[6]"));
                    assignedToElement = rowDataEle.findElement(By.xpath(".//td[7]"));
                    openElement = rowDataEle.findElement(By.xpath("//span[contains(text(),'Open')]"));
                }

                Boolean isValidRole = assignedToElement.getText().equalsIgnoreCase(assignedToUser);
                Boolean isValidDes = descriptionElement.getText().equalsIgnoreCase(description);
                Boolean isValidAssert = assertNameElement.getText().equalsIgnoreCase(assertName);

                if (isValidRole && isValidDes && isValidAssert) {
                    System.out.println(assignedToElement.getText()+" "+descriptionElement.getText()+" "+assertNameElement.getText());
                    openElement.click();
                    hasNextPage = false;
                    Thread.sleep(1000);
                    if(status.equalsIgnoreCase("InProgress")){
                        waitForWebElementToAppear(startButtonEle);
                        startButtonEle.click();
                    }
                    break;
                }
            }
        }
        try {
            Thread.sleep(1000);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void schedulerExecutionInProgress(String assertName, String assignedToUser, String description) throws InterruptedException {
        Thread.sleep(1000);
        updateCheckListExecution(assertName, assignedToUser, description, "InProgress");
    }

    public void checkListExecutionClosed(String assertName, String assignedToUser, String description, HashMap<String, String> remarkData) throws InterruptedException {
        Thread.sleep(1000);
        updateCheckListExecution(assertName, assignedToUser, description, "Closed");
        updateCheckList(remarkData);
    }

    @FindBy(xpath = "//table[@class='table table-stripped']//tbody/tr")
    List<WebElement> checkList;
    @FindBy(xpath = "//span[normalize-space()='Save Preview']")
    WebElement savePreviewEle;
    @FindBy(xpath = "//span[normalize-space()='Raise Ticket(s)']")
    WebElement raiseTicketEle;

    public void updateCheckList(HashMap<String, String> remarkData) throws InterruptedException {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<String> remarkDataList = new ArrayList<>();
        for (String key: remarkData.keySet()) {
            remarkDataList.add(remarkData.get(key));
        }
        for (int i = 0; i < checkList.size(); i++) {
            String xpathExpressionStatus = "//div[contains(@id, 'checks_" + i + "_status')]//span[contains(text(),'Yes')]";
            String xpathExpressionRemark = ".//textarea[@id='checks_" + i + "_remark']";
            if(i == checkList.size() - 1) {
                xpathExpressionStatus = "//div[contains(@id, 'checks_" + i + "_status')]//span[contains(text(),'No')]";
                driver.findElement(By.xpath(xpathExpressionStatus)).click();
                driver.findElement(By.xpath(xpathExpressionRemark)).sendKeys(remarkDataList.get(i));
            } else {
                driver.findElement(By.xpath(xpathExpressionStatus)).click();
                driver.findElement(By.xpath(xpathExpressionRemark)).sendKeys(remarkDataList.get(i));
            }
        }
        savePreviewEle.click();
        Thread.sleep(1000);
        raiseTicketEle.click();
    }

    @Override
    public void logOut(String userName) {
        super.logOut(userName);
    }

    public void login(String userName, String password) {
        super.login(userName, password);
        ticketCount = dashBoardPage.actualTicketCount();
    }
}
