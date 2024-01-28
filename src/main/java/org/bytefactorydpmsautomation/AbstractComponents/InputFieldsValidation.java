package org.bytefactorydpmsautomation.AbstractComponents;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputFieldsValidation {

    WebDriver driver;
    public InputFieldsValidation(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public static boolean validateRolePage(List<WebElement> inputElementsList, List<WebElement> mandatoryEleList, HashMap<String, String> inputValidationData) {
        List<String> mandatoryFieldNames = getMandatoryFieldNames(inputValidationData);
        if (!validateMandatoryElements(mandatoryEleList, mandatoryFieldNames)) {
            System.out.println("Mismatch in mandatory field names");
            return false;
        }
        if (!validateInputElements(inputElementsList, inputValidationData)) {
            System.out.println("Mismatch in input element types");
            return false;
        }

        return true;
    }

    private static List<String> getMandatoryFieldNames(HashMap<String, String> inputValidationData) {
        List<String> mandatoryFieldNames = new ArrayList<>();
        for (Map.Entry<String, String> entry : inputValidationData.entrySet()) {
            if (entry.getKey().startsWith("MandatoryFieldName")) {
                mandatoryFieldNames.add(entry.getValue());
            }
        }
        return mandatoryFieldNames;
    }

    private static boolean validateMandatoryElements(List<WebElement> mandatoryEleList, List<String> mandatoryFieldNames) {
        for (WebElement mandatoryEle : mandatoryEleList) {
            String mandatoryFieldText = mandatoryEle.getText().trim();
            boolean isMandatory = mandatoryFieldNames.contains(mandatoryFieldText);
            if (!isMandatory) {
                return false;
            }
        }
        return true;
    }

    private static boolean validateInputElements(List<WebElement> inputElementsList, HashMap<String, String> inputValidationData) {
        for (WebElement ele : inputElementsList) {
            String eleType = ele.getAttribute("type");

            if (inputValidationData.containsValue(eleType)) {
                String expectedType = inputValidationData.get("Type");
                if (!eleType.equals(expectedType)) {
                    System.out.println("Mismatch in input element type for element with ID: " + eleId);
                    return false;
                }
            } else {
                System.out.println("No validation data found for element with ID: " + eleId);
                return false;
            }
        }
        return true;
    }
    @FindBy(xpath = "//div[@class='ant-form-item-explain-error']")
    List<WebElement> errMessageList;
    public boolean validateMandatoryFields(){
        String ErrorMessage = inputValidationData.get("ErrorMessage");
        for (WebElement errMessage: errMessageList) {
            return errMessage.getText().equalsIgnoreCase(ErrorMessage);
        }
        return false;
    }
}
