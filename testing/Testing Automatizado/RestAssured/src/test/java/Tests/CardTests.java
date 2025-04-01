package Tests;

import Data.Builder.CardRequestBuilder;
import Data.Factory.CardListResponse;
import Data.Model.CardCreateRequest;
import Data.Utils.Login;
import Data.Utils.TokenManager;
import Model.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CardTests extends BaseTest {

    private static String cardNumber;

    @BeforeClass
    public void setupLogin() {
        Login.loginAndStoreToken();
    }

    @Test
    public void testCreateCardSuccessfully() {
        CardCreateRequest request = CardRequestBuilder.card()
                .withDefaults()
                .withRandomNumber()
                .build();

        CardCreateRequest response = given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .contentType("application/json")
                .body(request)
                .when()
                .post("http://localhost:8085/api/cards/" + TokenManager.userId)
                .then()
                .statusCode(201)
                .body("name", equalTo(request.getName()))
                .body("number", equalTo(request.getNumber()))
                .body("expiration", equalTo(request.getExpiration()))
                .extract()
                .as(CardCreateRequest.class);

        cardNumber = response.getNumber();
    }

    @Test(dependsOnMethods = "testCreateCardSuccessfully")
    public void testCreateDuplicateCard() {
        CardCreateRequest request = CardRequestBuilder.card()
                .withDefaults()
                .withNumber(cardNumber)
                .build();

        given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .contentType("application/json")
                .body(request)
                .when()
                .post("http://localhost:8085/api/cards/" + TokenManager.userId)
                .then()
                .statusCode(409)
                .body("error", equalTo("Ya existe una tarjeta con el número: " + request.getNumber()));
    }

    @Test
    public void testMissingExpiration() {
        CardCreateRequest request = CardRequestBuilder.card().withDefaults().build();
        request.setExpiration(null);

        given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .contentType("application/json")
                .body(request)
                .when()
                .post("http://localhost:8085/api/cards/" + TokenManager.userId)
                .then()
                .statusCode(400)
                .body("expiration", equalTo("La fecha de expiración es obligatoria"));
    }

    @Test
    public void testInvalidExpirationFormat() {
        CardCreateRequest request = CardRequestBuilder.card().withDefaults().build();
        request.setExpiration("XXXX");

        given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .contentType("application/json")
                .body(request)
                .when()
                .post("http://localhost:8085/api/cards/" + TokenManager.userId)
                .then()
                .statusCode(400)
                .body("expiration", equalTo("La fecha de expiracion tiene un formato invalido"));
    }

    @Test
    public void testMissingName() {
        CardCreateRequest request = CardRequestBuilder.card().withDefaults().build();
        request.setName(null);

        given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .contentType("application/json")
                .body(request)
                .when()
                .post("http://localhost:8085/api/cards/" + TokenManager.userId)
                .then()
                .statusCode(400)
                .body("name", equalTo("El nombre del titular es obligatorio"));
    }

    @Test
    public void testInvalidName() {
        CardCreateRequest request = CardRequestBuilder.card().withDefaults().build();
        request.setName("1234");

        given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .contentType("application/json")
                .body(request)
                .when()
                .post("http://localhost:8085/api/cards/" + TokenManager.userId)
                .then()
                .statusCode(400)
                .body("name", equalTo("El nombre solo puede contener letras y espacios"));
    }

    @Test
    public void testMissingNumber() {
        CardCreateRequest request = CardRequestBuilder.card().withDefaults().build();
        request.setNumber(null);

        given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .contentType("application/json")
                .body(request)
                .when()
                .post("http://localhost:8085/api/cards/" + TokenManager.userId)
                .then()
                .statusCode(400)
                .body("number", equalTo("El número de la tarjeta es obligatorio"));
    }

    @Test
    public void testInvalidNumber() {
        CardCreateRequest request = CardRequestBuilder.card().withDefaults().build();
        request.setNumber("123"); // Menos de 16 dígitos

        given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .contentType("application/json")
                .body(request)
                .when()
                .post("http://localhost:8085/api/cards/" + TokenManager.userId)
                .then()
                .statusCode(400)
                .body("number", equalTo("El número de tarjeta debe tener 16 dígitos"));
    }

    @Test
    public void testMissingCvc() {
        CardCreateRequest request = CardRequestBuilder.card().withDefaults().build();
        request.setCvc(null);

        given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .contentType("application/json")
                .body(request)
                .when()
                .post("http://localhost:8085/api/cards/" + TokenManager.userId)
                .then()
                .statusCode(400)
                .body("cvc", equalTo("El CVC es obligatorio"));
    }

    @Test
    public void testInvalidCvc() {
        CardCreateRequest request = CardRequestBuilder.card().withDefaults().build();
        request.setCvc("12"); // Menos de 3 dígitos

        given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .contentType("application/json")
                .body(request)
                .when()
                .post("http://localhost:8085/api/cards/" + TokenManager.userId)
                .then()
                .statusCode(400)
                .body("cvc", equalTo("El CVC debe tener 3 dígitos"));
    }


    @Test
    public void testListCardsForUser() {
        CardListResponse[] cards = given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .when()
                .get("http://localhost:8085/api/cards/" + TokenManager.userId)
                .then()
                .statusCode(200)
                .extract()
                .as(CardListResponse[].class);

        assert cards.length > 0;

        for (CardListResponse card : cards) {
            assert card.getId() != null;
            assert card.getNumber() != null;
            assert card.getName() != null;
            assert card.getExpiration() != null;
        }
    }

    @Test
    public void testListCardsEmptyForUserWithoutCards() {

        CardListResponse[] cards = given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .when()
                .get("http://localhost:8085/api/cards/" + UUID.randomUUID())
                .then()
                .statusCode(200)
                .extract()
                .as(CardListResponse[].class);

        assert cards.length == 0;
    }

    @Test
    public void testDeleteCardSuccessfully() {
        CardCreateRequest request = CardRequestBuilder.card()
                .withDefaults()
                .withRandomNumber()
                .build();

        CardCreateRequest response = given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .contentType("application/json")
                .body(request)
                .when()
                .post("http://localhost:8085/api/cards/" + TokenManager.userId)
                .then()
                .statusCode(201)
                .extract()
                .as(CardCreateRequest.class);

        UUID cardId = response.getId();

        given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .when()
                .delete("http://localhost:8085/api/cards/" + cardId)
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeleteNonExistentCard() {
        String fakeCardId = "00000000-0000-0000-0000-000000000000";

        given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .when()
                .delete("http://localhost:8085/api/cards/" + fakeCardId)
                .then()
                .statusCode(404)
                .body("error", equalTo("No existe una tarjeta con el id: " + fakeCardId));
    }


}

