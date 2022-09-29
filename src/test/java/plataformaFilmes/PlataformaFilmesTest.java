package plataformaFilmes;

import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PlataformaFilmesTest {

    @Test
    public void validarLogin() {
        RestAssured.baseURI = "http://localhost:8081/";


        String json = "{" +
                " \"email\": \"aluno@email.com\"," +
                " \"senha\": \"123456\"" +
                "}";

        Response response = post(json, ContentType.JSON, "auth");

        assertEquals(200, response.statusCode());
        String token = response.jsonPath().get("token");
        String tipo = response.jsonPath().get("tipo");
        assertEquals(tipo, "Bearer");

        System.out.println(token);


    }
    public Response post(Object json, ContentType contentType, String endpoint){

       return RestAssured.given()
                .relaxedHTTPSValidation()
                .contentType(contentType)
                .body(json)
                .when()
                .post(endpoint)
                .thenReturn();
    }
}
