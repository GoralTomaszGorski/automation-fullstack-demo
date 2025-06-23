package api;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import ui.utils.ApiClient;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RegisterApiTest {
    private ApiClient apiClient = new ApiClient();

    @Test
    @Description("Test rejestracji użytkownika na API reqres.in")
    public void testRegisterUserSuccess() {
        Response response = apiClient.registerUser("eve.holt@reqres.in", "pistol");
        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertTrue(response.jsonPath().getString("token") != null);
    }

    @Test
    @Description("Test rejestracji bez hasła - spodziewany błąd")
    public void testRegisterUserMissingPassword() {
        Response response = apiClient.registerUser("sydney@fife", "");
        Assertions.assertEquals(400, response.getStatusCode());
        Assertions.assertEquals("Missing password", response.jsonPath().getString("error"));
    }
}