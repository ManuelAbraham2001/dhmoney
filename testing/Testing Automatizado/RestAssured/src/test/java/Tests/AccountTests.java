package Tests;

import Data.Factory.AccountResponse;
import Data.Model.AccountUpdateAliasRequest;
import Data.Utils.Login;
import Data.Utils.TokenManager;
import Model.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AccountTests extends BaseTest {

    @BeforeClass
    public void setupLogin() {
        Login.loginAndStoreToken();
    }

    @Test
    public void testGetAccountByUserId() {
        AccountResponse response = given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .when()
                .get("http://localhost:8085/api/accounts/" + TokenManager.userId)
                .then()
                .statusCode(200)
                .extract()
                .as(AccountResponse.class);

        assert response.getId() != null;
        assert response.getCvu() != null;
        assert response.getAlias() != null;
        assert response.getBalance() >= 0;
        assert response.getUserId().equals(TokenManager.userId.toString());
    }

    @Test
    public void testGetAccountByInvalidUserId() {
        String fakeUserId = "00000000-0000-0000-0000-000000000000";

        given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .when()
                .get("http://localhost:8085/api/accounts/" + fakeUserId)
                .then()
                .statusCode(500); // según tu definición
    }

    @Test
    public void testGetAllAccounts() {
        AccountResponse[] accounts = given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .when()
                .get("http://localhost:8085/api/accounts")
                .then()
                .statusCode(200)
                .extract()
                .as(AccountResponse[].class);

        assert accounts.length > 0;
    }

    @Test
    public void testUpdateAliasSuccessfully() {
        AccountUpdateAliasRequest request = new AccountUpdateAliasRequest();
        request.setAlias("SDJHDJ.kjsdkj.sdfsf");

        AccountResponse response = given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .contentType("application/json")
                .body(request)
                .when()
                .patch("http://localhost:8085/api/accounts/" + TokenManager.userId)
                .then()
                .statusCode(200)
                .extract()
                .as(AccountResponse.class);

        assert response.getAlias().equals(request.getAlias());
    }

    @Test
    public void testUpdateAliasMissingAlias() {
        given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .contentType("application/json")
                .body("{}")
                .when()
                .patch("http://localhost:8085/api/accounts/" + TokenManager.userId)
                .then()
                .statusCode(400)
                .body("alias", equalTo("El alias es obligatorio"));
    }

    @Test
    public void testUpdateAliasInvalidAlias() {
        AccountUpdateAliasRequest request = new AccountUpdateAliasRequest();
        request.setAlias("ALIASDEPRUEBA"); // sin puntos

        given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .contentType("application/json")
                .body(request)
                .when()
                .patch("http://localhost:8085/api/accounts/" + TokenManager.userId)
                .then()
                .statusCode(400)
                .body("alias", equalTo("Alias invalido"));
    }

    @Test
    public void testUpdateAliasUserNotFound() {
        AccountUpdateAliasRequest request = new AccountUpdateAliasRequest();
        request.setAlias("ALIAS.DE.PRUEBA");

        String fakeUserId = "00000000-0000-0000-0000-000000000000";

        given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .contentType("application/json")
                .body(request)
                .when()
                .patch("http://localhost:8085/api/accounts/" + fakeUserId)
                .then()
                .statusCode(500);
    }
}
