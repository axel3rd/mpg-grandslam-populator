package org.blonding.mpg;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MpgConfig {

    @Value("${mpg.email}")
    private String email;

    @Value("${mpg.password}")
    private String password;

    @Value("${mpg.url}")
    private String url;

    @Value("${mpg.leagues.include:}#{T(java.util.Collections).emptyList()}")
    private List<String> leaguesInclude;

    @Value("${mpg.leagues.exclude:}#{T(java.util.Collections).emptyList()}")
    private List<String> leaguesExclude;

    @Value("${mpg.users.include:}#{T(java.util.Collections).emptyList()}")
    private List<Long> usersInclude;

    @Value("${mpg.users.exclude:}#{T(java.util.Collections).emptyList()}")
    private List<Long> usersExclude;

    @Value("${job.check.only:false}")
    private boolean onlyCheckDatas;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public List<String> getLeaguesInclude() {
        return leaguesInclude;
    }

    public List<String> getLeaguesExclude() {
        return leaguesExclude;
    }

    public List<Long> getUsersInclude() {
        return usersInclude;
    }

    public List<Long> getUsersExclude() {
        return usersExclude;
    }

    public boolean isOnlyCheckDatas() {
        return onlyCheckDatas;
    }
}
