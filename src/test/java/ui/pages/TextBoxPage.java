package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TextBoxPage {
    private WebDriver driver;

    private By fullNameField = By.id("userName");
    private By emailField = By.id("userEmail");
    private By submitButton = By.id("submit");
    private By outputName = By.id("name");

    public TextBoxPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("https://demoqa.com/text-box");
    }

    public void enterFullName(String fullName) {
        driver.findElement(fullNameField).sendKeys(fullName);
    }

    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void submit() {
        driver.findElement(submitButton).click();
    }

    public String getOutputName() {
        return driver.findElement(outputName).getText();
    }
}
