package com.dh.users_service.DTO;

public class CreateAccountDTO {
    private String firstName;
    private String lastName;

    public CreateAccountDTO() {
    }

    public CreateAccountDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
