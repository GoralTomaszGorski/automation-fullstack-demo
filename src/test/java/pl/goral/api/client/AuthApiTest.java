package pl.goral.api.client;

import org.junit.jupiter.api.*;
import pl.goral.api.dto.UserDto;
import pl.goral.api.dto.generators.UserGenerator;
import pl.goral.api.tests.BaseApiTest;

import static org.hamcrest.Matchers.*;

public class AuthApiTest extends BaseApiTest {

    private AuthApiClient apiClient;

    @BeforeEach
    void initClient() {
        apiClient = new AuthApiClient(BASE_URL, API_KEY);
    }

    @Test
    @DisplayName("Register and login user - token flow")
    void testRegisterAndLoginFlow() {
        UserDto user = UserGenerator.generate();

        apiClient.register(user)
                .statusCode(200)
                .body("id", notNullValue())
                .body("token", notNullValue());

        String token = apiClient.login(user)
                .statusCode(200)
                .body("token", notNullValue())
                .extract().path("token");

        Assertions.assertNotNull(token);
    }
}

