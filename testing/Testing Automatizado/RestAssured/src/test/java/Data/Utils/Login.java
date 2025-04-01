package Data.Utils;

import Data.Builder.LoginRequestBuilder;
import Data.Model.LoginRequest;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;

import static io.restassured.RestAssured.given;

import java.util.Base64;
import java.util.UUID;

public class Login {

    public static void loginAndStoreToken() {
        LoginRequest request = LoginRequestBuilder.login()
                .withDefaults()
                .build();

        String token = given()
                .contentType("application/json")
                .body(request)
                .when()
                .post("http://localhost:8085/api/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .path("token");

        TokenManager.token = token;

        // Extraer userId del token (campo "sub")
        String[] parts = token.split("\\.");
        String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
        UUID userId = UUID.fromString(payload.replaceAll(".*\"sub\":\"([^\"]+)\".*", "$1"));

        TokenManager.userId = userId;

        System.out.println("✅ TOKEN GUARDADO");
        System.out.println("🔐 TOKEN: " + token);
        System.out.println("👤 USER ID: " + userId);
    }
}

