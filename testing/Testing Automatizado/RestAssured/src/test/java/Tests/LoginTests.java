package Tests;

import Data.Builder.LoginRequestBuilder;
import Data.Model.LoginRequest;
import Data.Utils.TokenManager;
import Model.BaseTest;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.Base64;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LoginTests extends BaseTest {

    @Test
    public void testSuccessfulLogin() {
        LoginRequest request = LoginRequestBuilder.login()
                .withDefaults()
                .build();

        String token = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("http://localhost:8085/api/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .path("token");

    }

    @Test
    public void testLoginUserNotFound() {
        LoginRequest request = LoginRequestBuilder.login()
                .withEmail("noexiste@example.com")
                .withPassword("Aeiou1")
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("http://localhost:8085/api/auth/login")
                .then()
                .statusCode(404)
                .body("error", equalTo("Usuario no encontrado"));
    }

    @Test
    public void testIncorrectPassword() {
        LoginRequest request = LoginRequestBuilder.login()
                .withEmail("manu.20012009@gmail.com")
                .withPassword("claveMala123")
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("http://localhost:8085/api/auth/login")
                .then()
                .statusCode(400)
                .body("error", equalTo("Contraseña incorrecta"));
    }

    @Test
    public void testInvalidEmailFormat() {
        LoginRequest request = LoginRequestBuilder.login()
                .withEmail("invalidoemail.com")
                .withPassword("Aeiou1")
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("http://localhost:8085/api/auth/login")
                .then()
                .statusCode(400)
                .body("email", equalTo("debe ser una dirección de correo electrónico con formato correcto"));
    }

    @Test
    public void testInvalidPasswordFormat() {
        LoginRequest request = LoginRequestBuilder.login()
                .withEmail("manu.20012009@gmail.com")
                .withPassword("abc")
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("http://localhost:8085/api/auth/login")
                .then()
                .statusCode(400)
                .body("password", equalTo("La contraseña debe tener al menos 6 caracteres, 1 letra mayuscula y 1 numero"));
    }
}

