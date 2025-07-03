package pl.goral.api.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.goral.api.dto.UserDto;
import pl.goral.api.filters.RequestResponseLoggingFilter;
import pl.goral.config.ConfigProvider;


public abstract class BaseApiTest {

    protected static String baseUrl;
    protected static String apiKey;
    protected static String email = ConfigProvider.get("credentials.email");;
    protected static String password = ConfigProvider.get("credentials.password");

    protected static String token;
    protected static UserDto user;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @BeforeAll
    public static void globalSetup() {
        baseUrl = ConfigProvider.get("api.url");
        apiKey = ConfigProvider.get("api.key");

        RestAssured.baseURI = baseUrl;
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.filters(new RequestResponseLoggingFilter());
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