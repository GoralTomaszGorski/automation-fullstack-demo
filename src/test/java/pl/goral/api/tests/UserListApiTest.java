package pl.goral.api.tests;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserListApiTest extends BaseApiTest {

    private static final String USERS_URL = "/users?page=1";

    @Test
    @DisplayName("Get users list from page 1")
    public void testGetUsersPage1() {
        given()
                .contentType(ContentType.JSON)
                .header("x-api-key", apiKey)
                .get(USERS_URL)
                .then()
                .statusCode(200)
                .body("page", equalTo(1))
                .body("per_page", equalTo(6))
                .body("total", equalTo(12))
                .body("total_pages", equalTo(2))
                .body("data", hasSize(6))
                .body("data[0].id", equalTo(1))
                .body("data[0].email", equalTo("george.bluth@reqres.in"))
                .body("data[0].first_name", equalTo("George"))
                .body("data[0].last_name", equalTo("Bluth"))
                .body("data[0].avatar", startsWith("https://reqres.in/img/faces/"))
                .body("support.url", containsString("reqres"))
                .body("support.text", not(emptyString()));
    }


}
