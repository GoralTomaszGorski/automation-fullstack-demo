package pl.goral.api.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import pl.goral.api.dto.UserDto;
import pl.goral.api.filters.RequestResponseLoggingFilter;
import pl.goral.config.ConfigProvider;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public abstract class BaseApiTest {

    protected static final String BASE_URL = ConfigProvider.get("api.url");
    protected static final String API_KEY = "reqres-free-v1";

    @BeforeAll
    public static void setupBase() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.filters(new RequestResponseLoggingFilter());
    }

    protected String buildRequestBody(String email, String password) {
        StringBuilder json = new StringBuilder("{");
        if (email != null) json.append("\"email\": \"").append(email).append("\"");
        if (password != null) {
            if (email != null) json.append(", ");
            json.append("\"password\": \"").append(password).append("\"");
        }
        json.append("}");
        return json.toString();
    }

    protected String registerAndLogin(UserDto user) {
        // Register
        given()
                .contentType(ContentType.JSON)
                .header("x-api-key", API_KEY)
                .body(user)
                .when()
                .post("/register")
                .then()
                .statusCode(200)
                .body("token", notNullValue());

        // Log in
        return given()
                .contentType(ContentType.JSON)
                .header("x-api-key", API_KEY)
                .body(user)
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .extract().path("token");
    }
}
