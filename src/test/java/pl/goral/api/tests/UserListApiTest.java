package pl.goral.api.tests;

import io.restassured.http.ContentType;
import pl.goral.api.dto.UserDto;
import pl.goral.api.dto.generators.UserGenerator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.time.LocalDate;
import java.util.Map;

public class UserListApiTest extends BaseApiTest {

    private static final String LIST_URL = "/users";
    private static final String SINGLE_URL = "/users/{id}";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    @DisplayName("Get users list from page 1")
    public void testGetUsersPage1() {
        given()
                .contentType(ContentType.JSON)
                .header("x-api-key", apiKey)
                .get(LIST_URL)
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

    @Test
    @DisplayName("Get users list from page 2 with 3 users per page")
    public void testGetUsersPage2WithPerPage3() {
        given()
                .contentType(ContentType.JSON)
                .header("x-api-key", apiKey)
                .queryParam("page", 2)
                .queryParam("per_page", 3)
                .when()
                .get(LIST_URL)
                .then()
                .statusCode(200)
                .body("page", equalTo(2))
                .body("per_page", equalTo(3))
                .body("data", hasSize(3))
                .body("data[0].email", containsString("@reqres.in"))
                .body("data[0].first_name", not(emptyOrNullString()))
                .body("data[0].avatar", startsWith("https://reqres.in/img/faces/"));
    }

    @Test
    @DisplayName("Get single user by ID")
    public void testGetUserById() {
        given()
                .contentType(ContentType.JSON)
                .header("x-api-key", apiKey)
                .pathParam("id", 2)
                .when()
                .get(SINGLE_URL)
                .then()
                .statusCode(200)
                .body("data.id", equalTo(2))
                .body("data.email", equalTo("janet.weaver@reqres.in"))
                .body("data.first_name", equalTo("Janet"))
                .body("data.last_name", equalTo("Weaver"))
                .body("data.avatar", startsWith("https://reqres.in/img/faces/"))
                .body("support.url", not(emptyString()))
                .body("support.text", not(emptyString()));
    }

    @Test
    @DisplayName("Get non-existing user - expect 404")
    public void testGetNonExistingUser() {
        given()
                .contentType(ContentType.JSON)
                .header("x-api-key", apiKey)
                .pathParam("id", 23)
                .when()
                .get(SINGLE_URL)
                .then()
                .statusCode(404)
                .body(equalTo("{}"));
    }

    @Test
    @DisplayName("Create user using UserGenerator")
    public void testCreateUserWithGenerator() throws Exception {
        UserDto user = UserGenerator.generate();

        String requestBody = MAPPER.writeValueAsString(Map.of(
                "name", user.getUsername(),
                "password", user.getPassword(),
                "email", user.getEmail(),
                "job", user.getJob()));

        given()
                .contentType(ContentType.JSON)
                .header("x-api-key", apiKey)
                .body(requestBody)
                .when()
                .post(LIST_URL)
                .then()
                .statusCode(201)
                .body("name", equalTo(user.getUsername()))
                .body("job", equalTo(user.getJob()))
                .body("id", notNullValue())
                .body("createdAt", notNullValue());
    }

    @Test
    @DisplayName("Update existing user using UserGenerator")
    public void testUpdateUserWithGenerator() throws Exception {
        UserDto user = UserGenerator.generate();

        // JSON only for need fields
        String requestBody = MAPPER.writeValueAsString(Map.of(
                "name", user.getUsername(),
                "job", user.getJob(),
                "email", user.getEmail(),
                "password", user.getPassword()));

        String todayDate = LocalDate.now().toString(); // example. "2025-06-27"

        given()
                .contentType(ContentType.JSON)
                .header("x-api-key", apiKey)
                .body(requestBody)
                .pathParam("id", 1)
                .when()
                .put(SINGLE_URL)
                .then()
                .statusCode(200)
                .body("name", equalTo(user.getUsername()))
                .body("job", equalTo(user.getJob()))
                .body("email", equalTo(user.getEmail()))
                .body("password", equalTo(user.getPassword()))
                .body("updatedAt", startsWith(todayDate));
    }

    @Test
    @DisplayName("Partially update user using PATCH")
    public void testPatchUserJobOnly() throws Exception {
        String newJob = "Test Engineer";
        String todayDate = LocalDate.now().toString();

        given()
                .contentType(ContentType.JSON)
                .header("x-api-key", apiKey)
                .body(Map.of("job", newJob))
                .pathParam("id", 1)
                .when()
                .patch(SINGLE_URL)
                .then()
                .statusCode(200)
                .body("job", equalTo(newJob))
                .body("updatedAt", startsWith(todayDate));
    }

    @Test
    @DisplayName("Delete user by ID - expect 204 No Content")
    public void testDeleteUserById() {
        given()
                .contentType(ContentType.JSON)
                .header("x-api-key", apiKey)
                .pathParam("id", 5)
                .when()
                .delete(SINGLE_URL)
                .then()
                .statusCode(204)
                .body(emptyString());
    }


}
