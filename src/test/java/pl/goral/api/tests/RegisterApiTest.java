package pl.goral.api.tests;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;
import pl.goral.api.filters.RequestResponseLoggingFilter;
import pl.goral.config.ConfigProvider;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegisterApiTest {

    private static final String BASE_URL = ConfigProvider.get("api.url");
    private static final String VALID_EMAIL = ConfigProvider.get("credentials.email");
    private static final String VALID_PASSWORD = ConfigProvider.get("credentials.password");
    private static final String REGISTER_ENDPOINT = "/register";
    private static final String API_KEY = "reqres-free-v1";



    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.filters(new RequestResponseLoggingFilter());
    }



    private ValidatableResponse register(String email, String password) {
        String requestBody = buildRequestBody(email, password);

        return given()
                .contentType(ContentType.JSON)
                .header("x-api-key", API_KEY)
                .body(requestBody)
                .when()
                .post(REGISTER_ENDPOINT)
                .then();
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


    @Test
    @Order(1)
    @DisplayName("Successful registration - returns id and token")
    public void testSuccessfulRegistration() {
        register(VALID_EMAIL, VALID_PASSWORD)
                .statusCode(200)
                .body("id", notNullValue())
                .body("token", notNullValue());
    }

    @Test
    @Order(2)
    @DisplayName("Registration without password - returns error 'Missing password'")
    public void testRegistrationMissingPassword() {
        register(VALID_EMAIL, null)
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }

    @Test
    @Order(3)
    @DisplayName("Registration without email - returns error 'Missing email or username'")
    public void testRegistrationMissingEmail() {
        register(null, VALID_PASSWORD)
                .statusCode(400)
                .body("error", anyOf(equalTo("Missing email or username"), equalTo("Missing email")));
    }

    @Test
    @Order(4)
    @DisplayName("Registration with empty body - returns error 'Missing email or username'")
    public void testRegistrationEmptyBody() {
        register(null, null)
                .statusCode(400)
                .body("error", anyOf(equalTo("Missing email or username"), equalTo("Missing email")));
    }
}

