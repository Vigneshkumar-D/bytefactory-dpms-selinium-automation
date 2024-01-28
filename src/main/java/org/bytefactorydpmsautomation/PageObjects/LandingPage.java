package org.bytefactorydpmsautomation.PageObjects;

import org.bytefactorydpmsautomation.AbstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage extends AbstractComponent {
    WebDriver driver;
    public LandingPage(WebDriver driver){
        super(driver);
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id="username")
    WebElement userNameEle1;
    @FindBy(id="password")
    WebElement passwordEle1;
    @FindBy(xpath="//button[@type='submit']")
    WebElement submitEle1;
    @FindBy(id="basic_userName")
    WebElement userNameEle2;
    @FindBy(id="basic_password")
    WebElement passwordEle2;
//    @FindBy(css=".ant-btn.css-ru2fok.ant-btn-primary.ant-btn-block")
    @FindBy(xpath = "//span[normalize-space()='Login']")
    WebElement submitEle2;

    public void loginApplication(String userName,String password) {
        userNameEle1.sendKeys(userName);
        passwordEle1.sendKeys(password);
        submitEle1.click();
    }

    public void loginMainApplication(String userName, String password){
        userNameEle2.sendKeys(userName);
        passwordEle2.sendKeys(password);
        submitEle2.click();
    }

    public void goTo(String url) {
        driver.get(url);
    }
}
