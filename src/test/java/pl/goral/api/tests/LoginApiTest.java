package pl.goral.api.tests;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginApiTest extends BaseApiTest {

    private static final String LOGIN_URL = "/login";
    private static final String REGISTER_URL = "/register";

    @Test
    @Order(1)
    @DisplayName("Successful register - returns token")
    public void testSuccessfulRegister() {
        String token = loginOrRegisterAndGetToken(REGISTER_URL);
        Assertions.assertNotNull(token, "Token should not be null");
        System.out.println("Token: " + token);
    }

    @Test
    @Order(2)
    @DisplayName("Successful login - returns token")
    public void testSuccessfulLogin() {
        String token = loginOrRegisterAndGetToken(LOGIN_URL);
        Assertions.assertNotNull(token, "Token should not be null");
        System.out.println("Token: " + token);
    }

    @Test
    @Order(3)
    @DisplayName("Login without password - returns error 'Missing password'")
    public void testLoginMissingPassword() {
        String requestBody = buildRequestBody(email, null);

        given()
                .contentType(ContentType.JSON)
                .header("x-api-key", apiKey)
                .body(requestBody)
                .when()
                .post("/login")
                .then()
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }
}
