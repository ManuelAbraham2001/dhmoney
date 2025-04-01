package Data.Builder;

import Data.Model.RegisterRequest;
import com.github.javafaker.Faker;

public class RegisterRequestBuilder {

    private RegisterRequest request;
    private static final Faker faker = new Faker();

    private RegisterRequestBuilder() {
        this.request = new RegisterRequest();
    }

    public static RegisterRequestBuilder register() {
        return new RegisterRequestBuilder();
    }

    public RegisterRequestBuilder withDefaults() {
        this.request.setFirstName("Manuel");
        this.request.setLastName("Abraham");
        this.request.setPassword("Aeiou1");
        this.request.setPhone("1154678347");
        this.request.setDni("43768976");
        this.request.setEmail("manu.20012009@gmail.com");
        return this;
    }

    public RegisterRequestBuilder withRandomData() {
        this.request.setFirstName(faker.name().firstName());
        this.request.setLastName(faker.name().lastName());
        this.request.setPassword("Aeiou1"); // fija por validación
        this.request.setPhone(faker.phoneNumber().subscriberNumber(10));
        this.request.setDni(String.valueOf(faker.number().numberBetween(20000000, 50000000)));
        this.request.setEmail(faker.internet().emailAddress());
        return this;
    }

    public RegisterRequest build() {
        return this.request;
    }
}
