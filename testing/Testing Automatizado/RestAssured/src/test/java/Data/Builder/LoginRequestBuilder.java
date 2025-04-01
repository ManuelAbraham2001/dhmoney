package Data.Builder;

import Data.Model.LoginRequest;

public class LoginRequestBuilder {

    private LoginRequest request;

    private LoginRequestBuilder() {
        this.request = new LoginRequest();
    }

    public static LoginRequestBuilder login() {
        return new LoginRequestBuilder();
    }

    public LoginRequestBuilder withDefaults() {
        this.request.setEmail("manu.20012009@gmail.com");
        this.request.setPassword("Aeiou1");
        return this;
    }

    public LoginRequestBuilder withEmail(String email) {
        this.request.setEmail(email);
        return this;
    }

    public LoginRequestBuilder withPassword(String password) {
        this.request.setPassword(password);
        return this;
    }

    public LoginRequest build() {
        return this.request;
    }
}

