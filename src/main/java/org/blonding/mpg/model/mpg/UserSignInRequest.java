package org.blonding.mpg.model.mpg;

public class UserSignInRequest {

    private String login;

    private String password;

    private String language = "fr-FR";

    public UserSignInRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getLanguage() {
        return language;
    }

}
