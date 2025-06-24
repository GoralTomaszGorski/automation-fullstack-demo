package pl.goral.api.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import pl.goral.api.dto.UserDto;
import pl.goral.api.dto.generators.UserGenerator;
import pl.goral.config.ConfigProvider;
import pl.goral.api.filters.RequestResponseLoggingFilter;

import static io.restassured.RestAssured.given;

public abstract class BaseApiTest {

    protected static String baseUrl;
    protected static String apiKey;

    protected static String token;
    protected static UserDto user = UserGenerator.generate();

    @BeforeAll
    public static void globalSetup() {
        baseUrl = ConfigProvider.get("api.url");
        apiKey = "reqres-free-v1";

        RestAssured.baseURI = baseUrl;
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.filters(new RequestResponseLoggingFilter());
    }

    protected String loginOrRegisterAndGetToken(String url) {
        String requestBody = buildRequestBody(user.getEmail(), user.getPassword());

        Response response = given()
                .contentType(ContentType.JSON)
                .header("x-api-key", apiKey)
                .body(requestBody)
                .when()
                .post(url)
                .then()
                .statusCode(200)
                .extract().response();

        token = response.jsonPath().getString("token");
        return token;
    }

    protected String buildRequestBody(String email, String password) {
        StringBuilder json = new StringBuilder("{");
        boolean first = true;
        if (email != null) {
            json.append("\"email\": \"").append(email).append("\"");
            first = false;
        }
        if (password != null) {
            if (!first)
                json.append(", ");
            json.append("\"password\": \"").append(password).append("\"");
        }
        json.append("}");
        return json.toString();
    }
}
