package org.blonding.mpg;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.blonding.mpg.model.mpg.Dashboard;
import org.blonding.mpg.model.mpg.League;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thomaskasene.wiremock.junit.WireMockStubs;

@RunWith(SpringRunner.class)
@SpringBatchTest
@WireMockStubs
public class AbstractTestMpgData {

    @BeforeClass
    @DynamicPropertySource
    static void initialize(DynamicPropertyRegistry registry) {
        String baseUrl = String.format("http://localhost:%s", System.getProperty("wiremock.server.port"));
        registry.add("mpg.url", () -> baseUrl);
    }

    protected void mockMpgBackend(String date, String... leagues) {
        try {
            stubFor(post("/user/sign-in")
                    .willReturn(aResponse().withHeader("Content-Type", "application/json").withBodyFile("mpg.user-signIn.fake.json")));
            String leagueFile = "mpg.dashboard." + date + "." + String.join("-", leagues) + ".json";
            createGetJsonStub("/dashboard/leagues", leagueFile);

            Dashboard dashboard = new ObjectMapper().readValue(new File("src/test/resources/__files", leagueFile), Dashboard.class);

            for (League league : dashboard.getLeagues()) {
                if (!Arrays.stream(leagues).anyMatch(league.getId()::equals)) {
                    continue;
                }
                createGetJsonStub("/division/" + league.getDivisionId() + "/ranking/standings",
                        "mpg.ranking." + date + "." + league.getDivisionId() + ".json");
                createGetJsonStub("/division/" + league.getDivisionId() + "/teams", "mpg.teams." + date + "." + league.getDivisionId() + ".json");
            }
        } catch (IOException e) {
            throw new UnsupportedOperationException("Problem in Dashboard file", e);
        }
    }

    private void createGetJsonStub(String url, String file) {
        File f = new File("src/test/resources/__files", file);
        if (!f.exists()) {
            throw new UnsupportedOperationException(String.format("Test file '%s' doesn't exist for url '%s', it cannot work properly", file, url));
        }
        stubFor(get(url).willReturn(aResponse().withHeader("Content-Type", "application/json").withBodyFile(file)));
    }

}
