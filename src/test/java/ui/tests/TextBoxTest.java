package ui.tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ui.pages.TextBoxPage;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TextBoxTest {
    private WebDriver driver;
    private TextBoxPage textBoxPage;

    @BeforeAll
    public void setup() {
        driver = new ChromeDriver();
        textBoxPage = new TextBoxPage(driver);
    }

    @Test
    @Description("Test wypełnienia i przesłania formularza Text Box na demoqa.com")
    public void testFillAndSubmitForm() {
        textBoxPage.open();
        textBoxPage.enterFullName("Tomasz Górski");
        textBoxPage.enterEmail("tomasz.gorski@example.com");
        textBoxPage.submit();
        Assertions.assertTrue(textBoxPage.getOutputName().contains("Tomasz Górski"));
    }

    @AfterAll
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
