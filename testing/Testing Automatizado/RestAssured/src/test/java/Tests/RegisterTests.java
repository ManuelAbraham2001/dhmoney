package Tests;

import Data.Builder.RegisterRequestBuilder;
import Data.Model.RegisterRequest;
import Model.BaseTest;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RegisterTests extends BaseTest {

    private static String usedEmail;

    @Test
    public void testSuccessfulRegister() {
        RegisterRequest request = RegisterRequestBuilder.register()
                .withRandomData()
                .build();

        usedEmail = request.getEmail();

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("http://localhost:8085/api/auth/register")
                .then()
                .statusCode(201)
                .body("email", equalTo(request.getEmail()));
    }

    @Test(dependsOnMethods = "testSuccessfulRegister")
    public void testUserAlreadyExists() {
        RegisterRequest request = RegisterRequestBuilder.register()
                .withDefaults()
                .build();
        request.setEmail(usedEmail);

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("http://localhost:8085/api/auth/register")
                .then()
                .statusCode(409)
                .body("error", equalTo("Ya existe un usuario con el email: " + usedEmail));
    }

    @Test
    public void testMissingFirstName() {
        RegisterRequest request = RegisterRequestBuilder.register().withDefaults().build();
        request.setFirstName(null);

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("http://localhost:8085/api/auth/register")
                .then()
                .statusCode(400)
                .body("firstName", equalTo("El nombre es obligatorio"));
    }

    @Test
    public void testInvalidFirstName() {
        RegisterRequest request = RegisterRequestBuilder.register().withDefaults().build();
        request.setFirstName("1234");

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("http://localhost:8085/api/auth/register")
                .then()
                .statusCode(400)
                .body("firstName", equalTo("El nombre solo puede contener letras y espacios"));
    }

    @Test
    public void testMissingLastName() {
        RegisterRequest request = RegisterRequestBuilder.register().withDefaults().build();
        request.setLastName(null);

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("http://localhost:8085/api/auth/register")
                .then()
                .statusCode(400)
                .body("lastName", equalTo("El apellido es obligatorio"));
    }

    @Test
    public void testInvalidLastName() {
        RegisterRequest request = RegisterRequestBuilder.register().withDefaults().build();
        request.setLastName("1234");

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("http://localhost:8085/api/auth/register")
                .then()
                .statusCode(400)
                .body("lastName", equalTo("El apellido solo puede contener letras y espacios"));
    }

    @Test
    public void testMissingPassword() {
        RegisterRequest request = RegisterRequestBuilder.register().withDefaults().build();
        request.setPassword(null);

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("http://localhost:8085/api/auth/register")
                .then()
                .statusCode(400)
                .body("password", equalTo("La contraseña es obligatoria"));
    }

    @Test
    public void testInvalidPassword() {
        RegisterRequest request = RegisterRequestBuilder.register().withDefaults().build();
        request.setPassword("abc"); // no cumple nada

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("http://localhost:8085/api/auth/register")
                .then()
                .statusCode(400)
                .body("password", equalTo("La contraseña debe tener al menos 6 caracteres, 1 letra mayuscula y 1 numero"));
    }

    @Test
    public void testMissingPhone() {
        RegisterRequest request = RegisterRequestBuilder.register().withDefaults().build();
        request.setPhone(null);

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("http://localhost:8085/api/auth/register")
                .then()
                .statusCode(400)
                .body("phone", equalTo("El telefono es obligatorio"));
    }

    @Test
    public void testInvalidPhone() {
        RegisterRequest request = RegisterRequestBuilder.register().withDefaults().build();
        request.setPhone("abcd");

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("http://localhost:8085/api/auth/register")
                .then()
                .statusCode(400)
                .body("phone", equalTo("El telefono debe contener solo numeros"));
    }

    @Test
    public void testMissingDni() {
        RegisterRequest request = RegisterRequestBuilder.register().withDefaults().build();
        request.setDni(null);

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("http://localhost:8085/api/auth/register")
                .then()
                .statusCode(400)
                .body("dni", equalTo("El DNI es obligatorio"));
    }

    @Test
    public void testInvalidDni() {
        RegisterRequest request = RegisterRequestBuilder.register().withDefaults().build();
        request.setDni("abc123");

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("http://localhost:8085/api/auth/register")
                .then()
                .statusCode(400)
                .body("dni", equalTo("El DNI debe tener 7 u 8 digitos numericos"));
    }

    @Test
    public void testMissingEmail() {
        RegisterRequest request = RegisterRequestBuilder.register().withDefaults().build();
        request.setEmail(null);

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("http://localhost:8085/api/auth/register")
                .then()
                .statusCode(400)
                .body("email", equalTo("El email es obligatorio"));
    }

    @Test
    public void testInvalidEmail() {
        RegisterRequest request = RegisterRequestBuilder.register().withDefaults().build();
        request.setEmail("invalidoemail.com");

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("http://localhost:8085/api/auth/register")
                .then()
                .statusCode(400)
                .body("email", equalTo("debe ser una dirección de correo electrónico con formato correcto")); // Ajustalo al mensaje real
    }

}

