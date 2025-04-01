package Data.Builder;

import Data.Model.CardCreateRequest;
import com.github.javafaker.Faker;

import java.util.Random;

public class CardRequestBuilder {

    private CardCreateRequest request;
    private static final Faker faker = new Faker();
    private static final Random RANDOM = new Random();

    private CardRequestBuilder() {
        this.request = new CardCreateRequest();
    }

    public static CardRequestBuilder card() {
        return new CardRequestBuilder();
    }

    public CardRequestBuilder withDefaults() {
        this.request.setExpiration("0928");
        this.request.setName("Manuel Abraham");
        this.request.setNumber("9874536525975175");
        this.request.setCvc("865");
        return this;
    }

    public CardRequestBuilder withRandomNumber() {
        StringBuilder cvu = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            cvu.append(RANDOM.nextInt(10));
        }
        this.request.setNumber(cvu.toString());
        return this;
    }

    public CardRequestBuilder withExpiration(String expiration) {
        this.request.setExpiration(expiration);
        return this;
    }

    public CardRequestBuilder withName(String name) {
        this.request.setName(name);
        return this;
    }

    public CardRequestBuilder withNumber(String number) {
        this.request.setNumber(number);
        return this;
    }

    public CardRequestBuilder withCvc(String cvc) {
        this.request.setCvc(cvc);
        return this;
    }

    public CardCreateRequest build() {
        return this.request;
    }
}

