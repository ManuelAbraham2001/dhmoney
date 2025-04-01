package Data.Builder;

import Data.Model.ActivityRequest;

import java.time.LocalDate;

public class ActivityRequestBuilder {

    private ActivityRequest request;

    private ActivityRequestBuilder() {
        this.request = new ActivityRequest();
    }

    public static ActivityRequestBuilder activity() {
        return new ActivityRequestBuilder();
    }

    public ActivityRequestBuilder withDepositDefaults() {
        this.request.setAmount(1000.0);
        this.request.setName("Depósito sueldo");
        this.request.setType("Deposit");
        return this;
    }

    public ActivityRequestBuilder withTransferDefaults() {
        this.request.setAmount(1000.0);
        this.request.setName("Transferencia alquiler");
        this.request.setType("Transfer");
        this.request.setOrigin("5476742254882505031121");
        this.request.setDestination("7347578929766884572697");
        return this;
    }

    public ActivityRequest build() {
        return this.request;
    }
}

