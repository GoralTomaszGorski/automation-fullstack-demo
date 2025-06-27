package pl.goral.api.tests;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginApiTest extends BaseApiTest {

    private static final String LOGIN_URL = "/login";

    @Test
    @DisplayName("Successful login returns token")
    public void testSuccessfulLogin() {
        String body = buildRequestBody("eve.holt@reqres.in", "cityslicka");

        String token = given()
                .contentType(ContentType.JSON)
                .header("x-api-key", apiKey)
                .body(body)
                .when()
                .post(LOGIN_URL)
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .extract()
                .path("token");

        Assertions.assertNotNull(token);
        logger.info("Login Token: " + token);
    }

    @ParameterizedTest
    @DisplayName("Login errors with wrong or missing credentials")
    @CsvFileSource(resources = "/login-data.csv", numLinesToSkip = 1)
    void testLoginErrors(String email, String password, String expectedError) {
        String body = buildRequestBody(email, password);

        given()
                .contentType(ContentType.JSON)
                .header("x-api-key", apiKey)
                .body(body)
                .when()
                .post(LOGIN_URL)
                .then()
                .statusCode(400)
                .body("error", equalTo(expectedError));
    }
}
