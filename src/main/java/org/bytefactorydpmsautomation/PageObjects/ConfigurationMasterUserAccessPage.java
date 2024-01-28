package org.bytefactorydpmsautomation.PageObjects;

import org.bytefactorydpmsautomation.AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ConfigurationMasterUserAccessPage extends AbstractComponent {
    WebDriver driver;
    public ConfigurationMasterUserAccessPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//a[normalize-space()='UserAccess']")
    WebElement userAccessEle;
    @FindBy(xpath = "//input[@id='roleName']")
    WebElement roleNameEle;
    @FindBy(xpath = "(//td[@class='ant-table-cell'])[30]//div//label[2]")
    WebElement labelEle;
    @FindBy(xpath = "//span[contains(text(),'Save')]")
    WebElement saveButtonEle;
    @FindBy(xpath = "//span[normalize-space()='UserAcess provided successfully']")
    WebElement successAlertMessageEle;
    @FindBy(xpath = "(//div[@class='ant-col css-ru2fok'])[11]")
    WebElement schedulerCheckboxEle;
    @FindBy(xpath = "(//div[@class='ant-col css-ru2fok'])[13]")
    WebElement checkListExecutionEle;
    public String provideUserAccess(String role) throws InterruptedException {
        Thread.sleep(1000);
        if(!role.equalsIgnoreCase("Technician Role")){
            userAccessEle.click();
        }
        String xpathExpression = "//div[contains(text(),'" + role + "')]";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(200));
        waitForWebElementToAppear(roleNameEle);
        Actions actions = new Actions(driver);
        actions.moveToElement(roleNameEle).click().perform();
        roleNameEle.sendKeys(role);

        WebElement roleNameEle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathExpression)));
        roleNameEle.click();
        Thread.sleep(500);
        if (role.equalsIgnoreCase("SuperUser Role")) {
            for (int i = 1; i <= 35; i+=2) {
                Thread.sleep(200);
                driver.findElement(By.xpath("(//div[@class='ant-col css-ru2fok'])[" + i + "]")).click();
            }
        }
        else if(role.equalsIgnoreCase("Manager Role")){
            int index = 1;
            for (int i = 1; i <= 33; i+=2) {
                WebElement accessInputEle = driver.findElement(By.xpath("(//div[@class='ant-col css-ru2fok'])[" + i + "]"));
                WebElement accessTitleEle = null;
                accessTitleEle =  driver.findElement(By.xpath("(//article[@class='ant-typography css-ru2fok'])[" + index + "]"));
                index++;
                if (accessTitleEle.getText().equalsIgnoreCase("SMS/Mail Configuration")) {
                    labelEle.click();
                    continue;
                }
                Thread.sleep(200);
                accessInputEle.click();
            }
        }
        else if(role.equalsIgnoreCase("Technician Role")) {
            for (int j = 1; j < 18; j++) {
                WebElement checkBoxEle = driver.findElement(By.xpath("(//input[@value='View'])[" + j + "]"));
                if(j==6){
                    Thread.sleep(200);
                    schedulerCheckboxEle.click();
                    continue;
                } else if (j==7) {
                    Thread.sleep(200);
                    checkListExecutionEle.click();
                    continue;
                }
                Thread.sleep(200);
                checkBoxEle.click();
            }
        }
        saveButtonEle.click();
        waitForWebElementToAppear(successAlertMessageEle);
        return successAlertMessageEle.getText();
    }

//    public void timeDelay(){
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
//        wait.until(webDriver -> {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            return true;
//        });
//    }

    @FindBy(xpath = "//ul[@role='menu']//li[2]")
    WebElement configurationElement;
    public boolean checkUserAccess() throws InterruptedException {
        Thread.sleep(1000);
        configurationElement.click();
        WebElement userAccessEle;
        try{
            userAccessEle = driver.findElement(By.xpath("//a[normalize-space()='UserAccess']"));
        }catch (NoSuchElementException e) {
             return false;
        }
        return userAccessEle.isDisplayed();
    }
}
