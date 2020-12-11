package org.blonding.mpg.tasklet;

import java.util.HashMap;
import java.util.Map;

import org.blonding.mpg.MpgConfig;
import org.blonding.mpg.model.mpg.Dashboard;
import org.blonding.mpg.model.mpg.League;
import org.blonding.mpg.model.mpg.LeagueRanking;
import org.blonding.mpg.model.mpg.LeagueStatus;
import org.blonding.mpg.model.mpg.UserSignIn;
import org.blonding.mpg.model.mpg.UserSignInRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class MpgDatasTasklet implements Tasklet {

    private static final Logger LOG = LoggerFactory.getLogger(MpgDatasTasklet.class);

    private static final String MPG_CLIENT_VERSION = "6.9.0";

    @Autowired
    private MpgConfig mpgConfig;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        LOG.info("--- Retrieve MPG Datas and Leagues to use...");
        WebClient client = WebClient.builder().defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).baseUrl(mpgConfig.getUrl())
                .build();
        String token = client.post().uri("/user/signIn").accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(new UserSignInRequest(mpgConfig.getEmail(), mpgConfig.getPassword())), UserSignInRequest.class).retrieve()
                .bodyToMono(UserSignIn.class).block().getToken();
        Dashboard dasboard = client.get().uri("/user/dashboard").accept(MediaType.APPLICATION_JSON).header("client-version", MPG_CLIENT_VERSION)
                .header("authorization", token).retrieve().toEntity(Dashboard.class).block().getBody();

        Map<League, LeagueRanking> leagues = new HashMap<>();
        for (League league : dasboard.getLeagues()) {
            if (!LeagueStatus.GAMES.equals(league.getLeagueStatus())) {
                LOG.info("League {} removed, not in game", league.getId());
            } else if (mpgConfig.getLeaguesExclude().contains(league.getId())) {
                LOG.info("League {} removed, excluded", league.getId());
            } else if (!mpgConfig.getLeaguesInclude().isEmpty() && !mpgConfig.getLeaguesInclude().contains(league.getId())) {
                LOG.info("League {} removed, not in leagues included", league.getId());
            } else {
                LOG.info("League {} taken into account", league.getId());
                leagues.put(league,
                        client.get().uri("/league/" + league.getId() + "/ranking").accept(MediaType.APPLICATION_JSON)
                                .header("client-version", MPG_CLIENT_VERSION).header("authorization", token).retrieve().toEntity(LeagueRanking.class)
                                .block().getBody());
            }
        }
        if (leagues.size() < 2) {
            final String msg = "GrandSlam Cup requires two leagues minimum";
            LOG.error(msg);
            throw new UnsupportedOperationException(msg);
        }
        contribution.getStepExecution().getExecutionContext().put("leagues", leagues);
        return RepeatStatus.FINISHED;
    }

}
