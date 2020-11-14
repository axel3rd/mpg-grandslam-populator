package org.blonding.mpg.model.mpg;

public class UserSignInRequest {

    private String email;

    private String password;

    public UserSignInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
