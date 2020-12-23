package org.blonding.mpg.tasklet;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.blonding.mpg.model.bean.MpgUser;
import org.blonding.mpg.model.db.GrandSlam;
import org.blonding.mpg.model.db.Player;
import org.blonding.mpg.model.db.Team;
import org.blonding.mpg.model.mpg.League;
import org.blonding.mpg.model.mpg.LeagueRanking;
import org.blonding.mpg.model.mpg.Rank;
import org.blonding.mpg.repository.GrandSlamRepository;
import org.blonding.mpg.repository.PlayerRepository;
import org.blonding.mpg.repository.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

@Component
public class DataBaseUpdateRankingTeamsTasklet implements Tasklet {

    private static final Logger LOG = LoggerFactory.getLogger(DataBaseUpdateRankingTeamsTasklet.class);

    @Autowired
    private GrandSlamRepository grandSlamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

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

        List<Player> players = playerRepository.findAll();
        Map<Integer, Long> players2usersMpgMap = players.stream().collect(Collectors.toMap(Player::getId, Player::getMpgId));
        Map<Long, Integer> usersMpg2PlayersMap = players.stream().collect(Collectors.toMap(Player::getMpgId, Player::getId));

        // Create a tuple of leagues/MpgUser to update
        Set<String> updatesTodo = new HashSet<>();
        List<Long> mpgUsersString = users.stream().map(MpgUser::getMpgId).collect(Collectors.toList());
        for (League league : leaguesMpg.keySet()) {
            for (Long uid : mpgUsersString) {
                updatesTodo.add(league.getId() + "_" + uid);
            }
        }

        GrandSlam gs = grandSlamRepository.findOne(Example.of(GrandSlam.fromCurrentRunning())).orElseThrow();
        List<org.blonding.mpg.model.db.League> leagues = gs.getLeagues();
        for (org.blonding.mpg.model.db.League league : leagues) {
            for (Team team : league.getTeams()) {
                // No delete to manage here, players and leagues previous updates have done deletion (if any) by foreign key
                MpgUser user = users.stream().filter(u -> u.getMpgId() == players2usersMpgMap.get(team.getPlayerId())).findFirst().orElseThrow();
                Rank rank = getRank(leaguesMpg, league.getMpgId(), user.getMpgId());
                LOG.info("Ranking update for '{} / {}' with victory={} draw={} defeat={} diff={}", league.getMpgId(), user.getName(),
                        rank.getVictory(), rank.getDraw(), rank.getDefeat(), rank.getDifference());
                org.blonding.mpg.model.mpg.Team teamMpg = getTeam(leaguesMpg, league.getMpgId(), user.getMpgId());
                team.setName(teamMpg.getName());
                team.setShortName(teamMpg.getShortName());
                team.setVictory(rank.getVictory());
                team.setDefeat(rank.getDefeat());
                team.setDraw(rank.getDraw());
                team.setDefeat(rank.getDefeat());
                team.setGoalDiff(rank.getDifference());
                teamRepository.save(team);
                boolean removed = updatesTodo.remove(league.getMpgId() + "_" + user.getMpgId());
                if (!removed) {
                    throw new UnsupportedOperationException("Not removed, problem");
                }
            }
        }
        for (String id : updatesTodo) {
            String league = id.split("_")[0];
            Long userUid = Long.valueOf(id.split("_")[1]);
            MpgUser user = users.stream().filter(u -> u.getMpgId() == userUid).findFirst().orElseThrow();
            Rank rank = getRank(leaguesMpg, league, userUid);
            LOG.info("Ranking add for '{} / {}' with victory={} draw={} defeat={} diff={}", league, user.getName(), rank.getVictory(), rank.getDraw(),
                    rank.getDefeat(), rank.getDifference());
            Team team = new Team();
            org.blonding.mpg.model.mpg.Team teamMpg = getTeam(leaguesMpg, league, user.getMpgId());
            team.setPlayerId(usersMpg2PlayersMap.get(userUid));
            team.setLeagueId(getLeagueDbId(leagues, league));
            team.setName(teamMpg.getName());
            team.setShortName(teamMpg.getShortName());
            team.setVictory(rank.getVictory());
            team.setDefeat(rank.getDefeat());
            team.setDraw(rank.getDraw());
            team.setDefeat(rank.getDefeat());
            team.setGoalDiff(rank.getDifference());
            teamRepository.save(team);
        }
        return RepeatStatus.FINISHED;
    }

    private static int getLeagueDbId(List<org.blonding.mpg.model.db.League> leagues, String league) {
        for (org.blonding.mpg.model.db.League l : leagues) {
            if (league.equals(l.getMpgId())) {
                return l.getId();
            }
        }
        throw new UnsupportedOperationException(String.format("League id not found for league '%s'", league));
    }

    private static Rank getRank(Map<League, LeagueRanking> leaguesMpg, String league, Long user) {
        for (Entry<League, LeagueRanking> entry : leaguesMpg.entrySet()) {
            if (entry.getKey().getId().equals(league)) {
                for (Rank rank : entry.getValue().getRanks()) {
                    if (rank.getTeamId().equals(String.format("mpg_team_%s$$mpg_user_%s", league, user))) {
                        return rank;
                    }
                }
            }
        }
        throw new UnsupportedOperationException(String.format("Rank not found for league '%s' and user '%s'", league, user));
    }

    private static org.blonding.mpg.model.mpg.Team getTeam(Map<League, LeagueRanking> leaguesMpg, String league, Long user) {
        for (Entry<League, LeagueRanking> entry : leaguesMpg.entrySet()) {
            if (entry.getKey().getId().equals(league)) {
                org.blonding.mpg.model.mpg.Team t = entry.getValue().getTeams().get(String.format("mpg_team_%s$$mpg_user_%s", league, user));
                if (t != null) {
                    return t;
                }
            }
        }
        throw new UnsupportedOperationException(String.format("Rank not found for league '%s' and user '%s'", league, user));
    }

}
