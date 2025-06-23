package ui.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiClient {
    private static final String BASE_URL = "https://reqres.in/api";

    public Response registerUser(String email, String password) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .body("{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}")
                .post("/register");
    }
}