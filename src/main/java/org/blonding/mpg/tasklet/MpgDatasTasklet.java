package org.blonding.mpg.tasklet;

import java.util.EnumSet;
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

    @Autowired
    private MpgConfig mpgConfig;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        LOG.info("--- Retrieve MPG Datas and Leagues to use...");
        WebClient client = WebClient.builder().defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).baseUrl(mpgConfig.getUrl())
                .build();
        String token = client.post().uri("/user/sign-in").accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(new UserSignInRequest(mpgConfig.getEmail(), mpgConfig.getPassword())), UserSignInRequest.class).retrieve()
                .bodyToMono(UserSignIn.class).blockOptional().orElseThrow().getToken();
        Dashboard dasboard = client.get().uri("/dashboard/leagues").accept(MediaType.APPLICATION_JSON).header("authorization", token).retrieve()
                .toEntity(Dashboard.class).blockOptional().orElseThrow().getBody();
        if (dasboard == null) {
            throw new UnsupportedOperationException("The 'dasboard' cannot be null here");
        }
        Map<League, LeagueRanking> leagues = new HashMap<>();
        for (League league : dasboard.getLeagues()) {
            if (!EnumSet.of(LeagueStatus.GAMES, LeagueStatus.TERMINATED, LeagueStatus.KEEP).contains(league.getLeagueStatus())) {
                LOG.info("League {} removed, not in 'game' or 'terminated'", league.getId());
            } else if (mpgConfig.getLeaguesExclude().contains(league.getId())) {
                LOG.info("League {} removed, excluded", league.getId());
            } else if (!mpgConfig.getLeaguesInclude().isEmpty() && !mpgConfig.getLeaguesInclude().contains(league.getId())) {
                LOG.info("League {} removed, not in leagues included", league.getId());
            } else {
                var leagueRanking = client.get().uri("/division/" + league.getDivisionId() + "/ranking/standings").accept(MediaType.APPLICATION_JSON)
                        .header("authorization", token).retrieve().toEntity(LeagueRanking.class).blockOptional().orElseThrow().getBody();
                if (leagueRanking != null && leagueRanking.getStatusCode() == 404) {
                    LOG.info("League {} removed, not started", league.getId());
                } else {
                    LOG.info("League {} taken into account", league.getId());
                    // TODO Teams (name, abbreviation) should be completed here with "/division/mpg_division_XXXXX_3_1/teams" URL
                    leagues.put(league, leagueRanking);
                }
            }
        }
        if (leagues.size() < 2) {
            final var msg = "GrandSlam Cup requires two leagues minimum";
            LOG.error(msg);
            throw new UnsupportedOperationException(msg);
        }
        contribution.getStepExecution().getExecutionContext().put("leagues", leagues);
        return RepeatStatus.FINISHED;
    }

}
