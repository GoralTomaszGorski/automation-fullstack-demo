package pl.goral.ui.pages;

import pl.goral.config.ConfigProvider;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class LoginPage extends BasePage {

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@type='submit' and contains(text(), 'Sign in')]")
    private WebElement signInButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }


    public LoginPage openPage() {
        driver.get(ConfigProvider.get("frontend.url"));
        return this;
    }

    public <T extends BasePage> T attemptLogin(String username, String password, Class<T> expectedPage) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        signInButton.click();
        return getInstance(expectedPage);
    }

    public void verifyLoginUrl() {
        wait.until(ExpectedConditions.urlContains("/login"));
    }
}
