package ui.tests;


import org.junit.jupiter.api.BeforeEach;


public class AbstractTest extends SeleniumTest {

    protected LoginPage LoginPage;

    @BeforeEach
    public void navigate() {
        driver.get("http://localhost:8081/");
        LoginPage = new LoginPage(driver);
    }

}