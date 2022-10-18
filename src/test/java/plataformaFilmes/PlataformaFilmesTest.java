package plataformaFilmes;

import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.junit.jupiter.api.Assertions.*;

import maps.LoginMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.RestUtils;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class PlataformaFilmesTest {

    static String token;
    @Test
    public void validarLogin() {
        RestUtils.setBaseURI("http://localhost:8081/");

        String json = "{" +
                " \"email\": \"aluno@email.com\"," +
                " \"senha\": \"123456\"" +
                "}";

        Response response = RestUtils.post(json, ContentType.JSON, "auth");

        assertEquals(200, response.statusCode());
        token = response.jsonPath().get("token");
        String tipo = response.jsonPath().get("tipo");
        assertEquals(tipo, "Bearer");

        System.out.println(token);


    }

    @BeforeAll
    public static void validarLoginMap(){
        RestUtils.setBaseURI("http://localhost:8081/");
        LoginMap.initLogin();


        Response response = RestUtils.post(LoginMap.getLogin(), ContentType.JSON, "auth");

        assertEquals(200, response.statusCode());
        LoginMap.token = response.jsonPath().get("token");
        String tipo = response.jsonPath().get("tipo");
        assertEquals(tipo, "Bearer");






    }
    @Test
    public void validarConsultaCategorias(){


        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + LoginMap.token);

        Response response = RestUtils.get(header, "categorias");
        assertEquals(200, response.getStatusCode());

        System.out.println(response.jsonPath().get().toString());

        System.out.println(response.jsonPath().get("tipo[2]").toString());

        assertEquals("Terror", response.jsonPath().get("tipo[2]"));

        List<String> ListTipo = response.jsonPath().get("tipo");
        assertTrue(ListTipo.contains("Terror"));
    }



}
