package pl.goral.api.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import pl.goral.config.ConfigProvider;
import pl.goral.api.filters.RequestResponseLoggingFilter;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginApiTest {

    private static final String BASE_URL = ConfigProvider.get("api.url");
    private static final String VALID_EMAIL = ConfigProvider.get("credentials.email");
    private static final String VALID_PASSWORD = ConfigProvider.get("credentials.password");
    private static final String LOGIN_ENDPOINT = "/login";
    private static final String API_KEY = "reqres-free-v1";

    private static String token;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.filters(new RequestResponseLoggingFilter());
    }

    @Test
    @Order(1)
    @DisplayName("Successful login - returns token")
    public void testSuccessfulLogin() {
        String requestBody = buildRequestBody(VALID_EMAIL, VALID_PASSWORD);

        Response response = given()
                .contentType(ContentType.JSON)
                .header("x-api-key", API_KEY)
                .body(requestBody)
                .when()
                .post(LOGIN_ENDPOINT)
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .extract().response();

        token = response.jsonPath().getString("token");
        System.out.println("\n===== TOKEN CAPTURED =====\n" + token);
    }

    @Test
    @Order(2)
    @DisplayName("Login without password - returns error 'Missing password'")
    public void testLoginMissingPassword() {
        String requestBody = buildRequestBody(VALID_EMAIL, null);

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

    private String buildRequestBody(String email, String password) {
        StringBuilder json = new StringBuilder("{");
        boolean first = true;
        if (email != null) {
            json.append("\"email\": \"").append(email).append("\"");
            first = false;
        }
        if (password != null) {
            if (!first) json.append(", ");
            json.append("\"password\": \"").append(password).append("\"");
        }
        json.append("}");
        return json.toString();
    }

    public static String getToken() {
        return token;
    }
}