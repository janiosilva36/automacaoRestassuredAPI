package plataformaFilmes;

import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class PlataformaFilmesTest {

    static String token;
    @Test
    public void validarLogin() {
        RestAssured.baseURI = "http://localhost:8081/";


        String json = "{" +
                " \"email\": \"aluno@email.com\"," +
                " \"senha\": \"123456\"" +
                "}";

        Response response = post(json, ContentType.JSON, "auth");

        assertEquals(200, response.statusCode());
        token = response.jsonPath().get("token");
        String tipo = response.jsonPath().get("tipo");
        assertEquals(tipo, "Bearer");

        System.out.println(token);


    }

    @BeforeAll
    public static void validarLoginMap(){
        RestAssured.baseURI = "http://localhost:8081/";
        Map<String, String> map = new HashMap<>();
        map.put("email", "aluno@email.com");
        map.put("senha", "123456");

        Response response = post(map, ContentType.JSON, "auth");

        assertEquals(200, response.statusCode());
        token = response.jsonPath().get("token");
        String tipo = response.jsonPath().get("tipo");
        assertEquals(tipo, "Bearer");

        System.out.println(token);




    }
    @Test
    public void validarConsultaCategorias(){
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + token);

        Response response = get(header, "categorias");
        assertEquals(200, response.getStatusCode());

        System.out.println(response.jsonPath().get().toString());
    }

    private static Response get(Map<String, String> header, String endpoint) {
        return RestAssured.given()
                .relaxedHTTPSValidation()
                .headers(header)
                .when()
                .get(endpoint)
                .thenReturn();
    }

    public static Response post(Object json, ContentType contentType, String endpoint){

       return RestAssured.given()
                .relaxedHTTPSValidation()
                .contentType(contentType)
                .body(json)
                .when()
                .post(endpoint)
                .thenReturn();

    }
}
