package org.blonding.mpg.tasklet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.blonding.mpg.model.db.GrandSlam;
import org.blonding.mpg.model.db.MpgUser;
import org.blonding.mpg.model.db.Player;
import org.blonding.mpg.model.db.Team;
import org.blonding.mpg.model.mpg.League;
import org.blonding.mpg.model.mpg.LeagueRanking;
import org.blonding.mpg.repository.GrandSlamRepository;
import org.blonding.mpg.repository.LeagueRepository;
import org.blonding.mpg.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataBaseUpdateRankingTeamsTasklet implements Tasklet {

    private static final Logger LOG = LoggerFactory.getLogger(DataBaseUpdateRankingTeamsTasklet.class);

    @Autowired
    private GrandSlamRepository grandSlamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private LeagueRepository leagueRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        LOG.info("--- Update ranking teams...");
        @SuppressWarnings("unchecked")
        List<MpgUser> users = (List<MpgUser>) contribution.getStepExecution().getJobExecution().getExecutionContext().get("users");
        if (users == null) {
            throw new UnsupportedOperationException("Object 'users' cannot be null here");
        }
        @SuppressWarnings("unchecked")
        Map<League, LeagueRanking> leaguesMpg = (Map<League, LeagueRanking>) contribution.getStepExecution().getJobExecution().getExecutionContext()
                .get("leagues");
        if (leaguesMpg == null) {
            throw new UnsupportedOperationException("Object 'leaguesMpgOriginal' cannot be null here");
        }

        Map<Long, Long> players2userMpgMap = playerRepository.findAll().stream().collect(Collectors.toMap(Player::getId, Player::getMpgId));

        // Create a tuple of leagues/players to update
        Map<String, List<Long>> updatesTodo = new HashMap<>();
        for (League league : leaguesMpg.keySet()) {
            updatesTodo.put(league.getId(), users.stream().map(MpgUser::getMpgId).collect(Collectors.toList()));
        }

        List<GrandSlam> gsRunning = grandSlamRepository.findByStatus("Running");
        if (gsRunning.size() > 1) {
            throw new UnsupportedOperationException("Multiple GrandSlam are currently running, not supported");
        }
        GrandSlam gs = gsRunning.stream().findFirst().orElseThrow();
        List<org.blonding.mpg.model.db.League> leagues = leagueRepository.findByGrandSlamId(gs.getId());
        for (org.blonding.mpg.model.db.League league : leagues) {
            for (Team team : league.getTeams()) {
                // No delete to manage here, players and leagues previous updates have done deletion (if any) by foreign key
                MpgUser user = users.stream().filter(u -> u.getMpgId() == players2userMpgMap.get(team.getPlayerId())).findFirst().orElseThrow();
                LOG.info("Ranking update for '{} / {}' with ...", league.getMpgId(), user.getName());

                // TODO : Not a correct way todo that
                boolean removed = updatesTodo.get(league.getMpgId()).remove(team.getPlayerId());
                if (!removed) {
                    throw new UnsupportedOperationException("Not removed, problem");
                }
            }
        }
        for (Entry<String, List<Long>> e : updatesTodo.entrySet()) {
            String league = e.getKey();
            for (Long userMpgId : e.getValue()) {
                LOG.info("Ranking add for '{} / {}' with ...", league, userMpgId);
            }
        }
        return RepeatStatus.FINISHED;
    }

}
