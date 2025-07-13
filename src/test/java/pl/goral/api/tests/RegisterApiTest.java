package pl.goral.api.tests;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegisterApiTest extends BaseApiTest {

    private static final String REGISTER_URL = "/register";
    private static String token;

    @Test
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

    @ParameterizedTest
    @DisplayName("Try Register: 1. without password 2. without login 3. wrong email")
    @CsvFileSource(resources = "/register-data.csv", numLinesToSkip = 1)
    void testRegisterErrors(String email, String password, String expectedError) {
        String body = buildRequestBody(email, password);

        given()
                .contentType(ContentType.JSON)
                .header("x-api-key", apiKey)
                .body(body)
                .when()
                .post(REGISTER_URL)
                .then()
                .statusCode(400)
                .body("error", equalTo(expectedError));
    }
}
