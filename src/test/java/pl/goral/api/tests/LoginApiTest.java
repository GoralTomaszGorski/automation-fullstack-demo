package pl.goral.api.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import pl.goral.api.dto.UserDto;
import pl.goral.api.dto.generators.UserGenerator;
import pl.goral.config.ConfigProvider;
import pl.goral.api.filters.RequestResponseLoggingFilter;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LoginApiTest extends BaseApiTest {

    private final String LOGIN_ENDPOINT = "/login";
    private UserDto user = UserGenerator.generate();

    @Test
    @DisplayName("Login without password - returns 'Missing password'")
    void testMissingPassword() {
        String requestBody = buildRequestBody(user.getEmail(), null);

        given()
                .contentType(ContentType.JSON)
                .header("x-api-key", API_KEY)
                .body(requestBody)
                .when()
                .post(LOGIN_ENDPOINT)
                .then()
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }
}
