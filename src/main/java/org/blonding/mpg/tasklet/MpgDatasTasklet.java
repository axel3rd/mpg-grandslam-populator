package org.blonding.mpg.tasklet;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.blonding.mpg.MpgConfig;
import org.blonding.mpg.model.mpg.Dashboard;
import org.blonding.mpg.model.mpg.League;
import org.blonding.mpg.model.mpg.LeagueRanking;
import org.blonding.mpg.model.mpg.LeagueStatus;
import org.blonding.mpg.model.mpg.Team;
import org.blonding.mpg.model.mpg.UserSignIn;
import org.blonding.mpg.model.mpg.UserSignInRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class MpgDatasTasklet implements Tasklet {

    private static final Logger LOG = LoggerFactory.getLogger(MpgDatasTasklet.class);

    private static final String HEADER_AUTHORIZATION = "authorization";

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
        Dashboard dasboard = client.get().uri("/dashboard/leagues").accept(MediaType.APPLICATION_JSON).header(HEADER_AUTHORIZATION, token).retrieve()
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
                var leagueRanking = getLeagueRankingAndTeamInfos(client, token, league);
                if (leagueRanking != null) {
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

    private LeagueRanking getLeagueRankingAndTeamInfos(WebClient client, String token, League league) {
        var leagueRanking = client.get().uri("/division/" + league.getDivisionId() + "/ranking/standings").accept(MediaType.APPLICATION_JSON)
                .header(HEADER_AUTHORIZATION, token).retrieve().toEntity(LeagueRanking.class).blockOptional().orElseThrow().getBody();
        if (leagueRanking == null) {
            throw new UnsupportedOperationException("The 'leagueRanking' cannot be null here");
        }
        if (leagueRanking.getStatusCode() == 404) {
            LOG.info("League {} removed, not started", league.getId());
            return null;
        }
        LOG.info("League {} taken into account", league.getId());
        List<Team> teams = client.get().uri("/division/" + league.getDivisionId() + "/teams").accept(MediaType.APPLICATION_JSON)
                .header(HEADER_AUTHORIZATION, token).retrieve().toEntity(new ParameterizedTypeReference<List<Team>>() {
                }).blockOptional().orElseThrow().getBody();
        if (teams == null) {
            throw new UnsupportedOperationException("The 'teams' cannot be null here");
        }
        for (Entry<String, Team> entry : leagueRanking.getTeams().entrySet()) {
            var team = teams.stream().filter(p -> p.getId().equals(entry.getKey())).findFirst().orElseThrow();
            // Complete team infos (name, abbreviation) and update correct teamId and userId
            entry.getValue().setUserId(entry.getValue().getId());
            entry.getValue().setId(entry.getKey());
            entry.getValue().setName(team.getName());
            entry.getValue().setShortName(team.getShortName());
        }
        return leagueRanking;
    }
}
