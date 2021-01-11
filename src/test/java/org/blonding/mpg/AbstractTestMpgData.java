package org.blonding.mpg;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

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
        stubFor(post("/user/signIn")
                .willReturn(aResponse().withHeader("Content-Type", "application/json").withBodyFile("mpg.user-signIn.fake.json")));
        stubFor(get("/user/dashboard").willReturn(aResponse().withHeader("Content-Type", "application/json")
                .withBodyFile("mpg.dashboard.MLAX7HMK-MLEFEX6G-MN7VSYBM-MLMHBPCB." + date + ".json")));
        for (String league : leagues) {
            stubFor(get("/league/" + league + "/ranking").willReturn(
                    aResponse().withHeader("Content-Type", "application/json").withBodyFile("mpg.ranking." + league + "." + date + ".json")));
        }
    }
}
