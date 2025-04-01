package Tests;

import Data.Builder.ActivityRequestBuilder;
import Data.Factory.ActivityResponse;
import Data.Model.ActivityRequest;
import Data.Utils.Login;
import Data.Utils.TokenManager;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ActivityTests {

    private static UUID activityId;
    private final String validOrigin = "5476742254882505031121"; // CVU válido del usuario logueado
    private final String validDestination = "ALIAS.DE.PRUEBA"; // alias válido de otra cuenta

    @BeforeClass
    public void setupLogin() {
        Login.loginAndStoreToken();
    }

    @Test
    public void testSuccessfulDeposit() {
        ActivityRequest request = ActivityRequestBuilder.activity()
                .withDepositDefaults()
                .build();

        ActivityResponse response = given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .contentType("application/json")
                .body(request)
                .when()
                .post("http://localhost:8085/api/activities/" + TokenManager.userId)
                .then()
                .statusCode(201)
                .body("type", equalTo("Deposit"))
                .extract()
                .as(ActivityResponse.class);

        activityId = response.getId();

        assert response.getAmount().equals(request.getAmount());
        assert response.getType().equals("Deposit");
        assert response.getUserId().equals(TokenManager.userId);
    }

    @Test
    public void testDepositWithNegativeAmount() {
        ActivityRequest request = ActivityRequestBuilder.activity()
                .withDepositDefaults()
                .build();
        request.setAmount(-500.0);

        given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .contentType("application/json")
                .body(request)
                .when()
                .post("http://localhost:8085/api/activities/" + TokenManager.userId)
                .then()
                .statusCode(400)
                .body("error", equalTo("El monto debe ser mayor a 0"));
    }

    @Test
    public void testDepositWithoutAmount() {
        ActivityRequest request = ActivityRequestBuilder.activity()
                .withDepositDefaults()
                .build();
        request.setAmount(null);

        given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .contentType("application/json")
                .body(request)
                .when()
                .post("http://localhost:8085/api/activities/" + TokenManager.userId)
                .then()
                .statusCode(400)
                .body("amount", equalTo("El monto es obligatorio"));
    }

    @Test
    public void testActivityWithoutType() {
        ActivityRequest request = ActivityRequestBuilder.activity()
                .withDepositDefaults()
                .build();
        request.setType(null);

        given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .contentType("application/json")
                .body(request)
                .when()
                .post("http://localhost:8085/api/activities/" + TokenManager.userId)
                .then()
                .statusCode(400)
                .body("type", equalTo("El tipo de actividad es obligatorio"));
    }

    @Test
    public void testActivityWithInvalidType() {
        ActivityRequest request = ActivityRequestBuilder.activity()
                .withDepositDefaults()
                .build();
        request.setType("PagoDeServicios");

        given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .contentType("application/json")
                .body(request)
                .when()
                .post("http://localhost:8085/api/activities/" + TokenManager.userId)
                .then()
                .statusCode(400)
                .body("type", equalTo("El tipo debe ser 'Deposit' o 'Transfer'"));
    }

    @Test
    public void testListActivitiesByUserId() {
        ActivityResponse[] activities = given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .when()
                .get("http://localhost:8085/api/activities/users/" + TokenManager.userId)
                .then()
                .statusCode(200)
                .extract()
                .as(ActivityResponse[].class);

        assert activities.length >= 0; // puede estar vacío
        for (ActivityResponse activity : activities) {
            assert activity.getUserId().equals(TokenManager.userId);
        }
    }

    @Test
    public void testListActivitiesByInvalidUserId() {
        String fakeUserId = "f8967409-024f-4dc9-900f-07174ebc4287";

        given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .when()
                .get("http://localhost:8085/api/activities/users/" + fakeUserId)
                .then()
                .statusCode(404)
                .body("error", equalTo("Usuario no encontrado con el id: " + fakeUserId));
    }

    @Test(dependsOnMethods = "testSuccessfulDeposit")
    public void testGetActivityById() {
        ActivityResponse response = given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .when()
                .get("http://localhost:8085/api/activities/" + activityId)
                .then()
                .statusCode(200)
                .extract()
                .as(ActivityResponse.class);

        assert response.getId().equals(activityId);
    }

    @Test
    public void testGetActivityByInvalidId() {
        String fakeId = "56b3b141-5e41-4c41-bcd5-be091e9362e5";

        given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .when()
                .get("http://localhost:8085/api/activities/" + fakeId)
                .then()
                .statusCode(404)
                .body("error", equalTo("No existe una transaccion con el id: " + fakeId));
    }

    @Test
    public void testGetActivityByBadFormatId() {
        String invalidId = "not-a-uuid";

        given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .when()
                .get("http://localhost:8085/api/activities/" + invalidId)
                .then()
                .statusCode(400);
    }

    @Test
    public void testSuccessfulTransfer() {
        ActivityRequest request = ActivityRequestBuilder.activity()
                .withTransferDefaults()
                .build();

        request.setAmount(-500.0);
        request.setOrigin(validOrigin);
        request.setDestination(validDestination);

        float f = (float)-500.0;

        given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .contentType("application/json")
                .body(request)
                .when()
                .post("http://localhost:8085/api/activities/" + TokenManager.userId)
                .then()
                .statusCode(201)
                .body("type", equalTo("Transfer"))
                .body("origin", equalTo(request.getOrigin()))
                .body("destination", equalTo(request.getDestination()))
                .body("amount", equalTo(f));
    }

    @Test
    public void testTransferWithoutOrigin() {
        ActivityRequest request = ActivityRequestBuilder.activity()
                .withTransferDefaults()
                .build();
        request.setOrigin(null);

        given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .contentType("application/json")
                .body(request)
                .when()
                .post("http://localhost:8085/api/activities/" + TokenManager.userId)
                .then()
                .statusCode(400)
                .body("error", equalTo("La cuenta de origen es obligatoria."));
    }

    @Test
    public void testTransferWithoutDestination() {
        ActivityRequest request = ActivityRequestBuilder.activity()
                .withTransferDefaults()
                .build();
        request.setDestination(null);

        given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .contentType("application/json")
                .body(request)
                .when()
                .post("http://localhost:8085/api/activities/" + TokenManager.userId)
                .then()
                .statusCode(400)
                .body("error", equalTo("La cuenta de destino es obligatoria."));
    }

    @Test
    public void testTransferWithPositiveAmount() {
        ActivityRequest request = ActivityRequestBuilder.activity()
                .withTransferDefaults()
                .build();
        request.setAmount(500.0);

        given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .contentType("application/json")
                .body(request)
                .when()
                .post("http://localhost:8085/api/activities/" + TokenManager.userId)
                .then()
                .statusCode(400)
                .body("error", equalTo("El monto de la transferencia debe ser negativo"));
    }

    @Test
    public void testTransferWithSameOriginAndDestination() {
        ActivityRequest request = ActivityRequestBuilder.activity()
                .withTransferDefaults()
                .build();
        request.setOrigin(validOrigin);
        request.setDestination(validOrigin);

        given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .contentType("application/json")
                .body(request)
                .when()
                .post("http://localhost:8085/api/activities/" + TokenManager.userId)
                .then()
                .statusCode(400)
                .body("error", equalTo("No es posible hacer transferencias a una misma cuenta."));
    }

    @Test
    public void testTransferFromUnauthorizedAccount() {
        ActivityRequest request = ActivityRequestBuilder.activity()
                .withTransferDefaults()
                .build();
        request.setOrigin("9999999999999999999999");

        given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .contentType("application/json")
                .body(request)
                .when()
                .post("http://localhost:8085/api/activities/" + TokenManager.userId)
                .then()
                .statusCode(403)
                .body("error", equalTo("No puedes transferir desde una cuenta que no te pertenece"));
    }

    @Test
    public void testTransferToNonExistentAccount() {
        ActivityRequest request = ActivityRequestBuilder.activity()
                .withTransferDefaults()
                .build();
        request.setDestination("NO.EXISTE.ALIAS");

        given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .contentType("application/json")
                .body(request)
                .when()
                .post("http://localhost:8085/api/activities/" + TokenManager.userId)
                .then()
                .statusCode(400)
                .body("error", equalTo("La cuenta de destino no existe"));
    }

    @Test
    public void testTransferWithInsufficientFunds() {
        ActivityRequest request = ActivityRequestBuilder.activity()
                .withTransferDefaults()
                .build();
        request.setAmount(-999999.0);

        given()
                .header("Authorization", "Bearer " + TokenManager.token)
                .contentType("application/json")
                .body(request)
                .when()
                .post("http://localhost:8085/api/activities/" + TokenManager.userId)
                .then()
                .statusCode(400)
                .body("error", equalTo("Fondos insuficientes"));
    }

}
