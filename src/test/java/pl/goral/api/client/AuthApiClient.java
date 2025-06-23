package pl.goral.api.client;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import pl.goral.api.dto.UserDto;
import pl.goral.config.ConfigProvider;

import static io.restassured.RestAssured.given;

public class AuthApiClient {

    private final String baseUri;
    private final String apiKey;

    public AuthApiClient(String baseUri, String apiKey) {
        this.baseUri = baseUri;
        this.apiKey = apiKey;
    }

    public ValidatableResponse register(UserDto user) {
        return given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .header("x-api-key", apiKey)
                .body(user)
                .when()
                .post("/register")
                .then();
    }

    public ValidatableResponse login(UserDto user) {
        return given()
                .baseUri(baseUri)
                .contentType(ContentType.JSON)
                .header("x-api-key", apiKey)
                .body(user)
                .when()
                .post("/login")
                .then();
    }
}

