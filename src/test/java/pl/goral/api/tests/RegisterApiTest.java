package pl.goral.api.tests;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegisterApiTest extends BaseApiTest {

    private static final String REGISTER_URL = "/register";
    private static String token;

    @Test
    @Order(1)
    @DisplayName("Successful register - returns token")
    public void testSuccessfulRegister() {
        String requestBody = buildRequestBody(email, password);

        token = given()
                .contentType(ContentType.JSON)
                .header("x-api-key", apiKey)
                .body(requestBody)
                .when()
                .post(REGISTER_URL)
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .extract()
                .path("token");

        Assertions.assertNotNull(token, "Token should not be null");
        logger.info("Token: " + token);
    }

    @Test
    @Order(2)
    @DisplayName("Register without password - returns error 'Missing password'")
    public void testRegisterMissingPassword() {
        String requestBody = buildRequestBody(email, null);

        given()
                .contentType(ContentType.JSON)
                .header("x-api-key", apiKey)
                .body(requestBody)
                .when()
                .post(REGISTER_URL)
                .then()
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }

    @Test
    @Order(3)
    @DisplayName("Register without login - returns error 'Missing email or username'")
    public void testRegisterBadLogin() {
        String requestBody = buildRequestBody(null, password);

        given()
                .contentType(ContentType.JSON)
                .header("x-api-key", apiKey)
                .body(requestBody)
                .when()
                .post(REGISTER_URL)
                .then()
                .statusCode(400)
                .body("error", equalTo("Missing email or username"));
    }

}
