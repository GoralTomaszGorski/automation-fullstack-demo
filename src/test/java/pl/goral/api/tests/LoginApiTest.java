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
        String requestBody = buildRequestBody(email, password);

        given()
                .contentType(ContentType.JSON)
                .header("x-api-key", apiKey)
                .body(requestBody)
                .when()
                .post(REGISTER_URL)
                .then()
                .statusCode(200);
        Assertions.assertNotNull(token, "Token should not be null");
        logger.info("Token: " + token);
    }

    @Test
    @Order(2)
    @DisplayName("Successful login - returns token")
    public void testSuccessfulLogin() {
        String requestBody = buildRequestBody(email, password);

        given()
                .contentType(ContentType.JSON)
                .header("x-api-key", apiKey)
                .body(requestBody)
                .when()
                .post(REGISTER_URL)
                .then()
                .statusCode(200);
        Assertions.assertNotNull(token, "Token should not be null");
        logger.info("Token: " + token);
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
