package pl.goral.ui.tests;


import org.junit.jupiter.api.BeforeEach;
import pl.goral.SeleniumTest;
import pl.goral.ui.pages.LoginPage;


public class AbstractTest extends SeleniumTest {

    protected LoginPage LoginPage;

    @BeforeEach
    public void navigate() {
        driver.get("http://localhost:8081/");
        LoginPage = new LoginPage(driver);
    }

}