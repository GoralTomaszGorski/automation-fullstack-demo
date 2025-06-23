package pl.goral.api.filters;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class RequestResponseLoggingFilter implements Filter {

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {

        System.out.println("\n===== REST ASSURED REQUEST =====");
        System.out.println(requestSpec.getMethod() + " " + requestSpec.getURI());
        System.out.println("Headers: " + requestSpec.getHeaders());
        System.out.println("Body: " + requestSpec.getBody());

        Response response = ctx.next(requestSpec, responseSpec);

        System.out.println("\n===== REST ASSURED RESPONSE =====");
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Headers: " + response.getHeaders());
        System.out.println("Body: " + response.getBody().prettyPrint());

        return response;
    }
}
