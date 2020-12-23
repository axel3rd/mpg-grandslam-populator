package org.blonding.mpg.tasklet;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.blonding.mpg.model.db.GrandSlam;
import org.blonding.mpg.model.mpg.League;
import org.blonding.mpg.model.mpg.LeagueRanking;
import org.blonding.mpg.model.mpg.Rank;
import org.blonding.mpg.repository.GrandSlamRepository;
import org.blonding.mpg.repository.LeagueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataBaseUpdateLeaguesTasklet implements Tasklet {

    private static final Logger LOG = LoggerFactory.getLogger(DataBaseUpdateLeaguesTasklet.class);

    @Autowired
    private GrandSlamRepository grandSlamRepository;

    @Autowired
    private LeagueRepository leagueRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        LOG.info("--- Update leagues informations...");
        List<GrandSlam> gsRunning = grandSlamRepository.findByStatus("Running");
        if (gsRunning.size() > 1) {
            throw new UnsupportedOperationException("Multiple GrandSlam are currently running, not supported");
        }
        GrandSlam gs = gsRunning.stream().findFirst().orElseThrow();

        @SuppressWarnings("unchecked")
        Map<League, LeagueRanking> leaguesMpgOriginal = (Map<League, LeagueRanking>) contribution.getStepExecution().getJobExecution()
                .getExecutionContext().get("leagues");
        if (leaguesMpgOriginal == null) {
            throw new UnsupportedOperationException("Object 'leaguesMpgOriginal' cannot be null here");
        }
        // Copy to new map
        Map<League, LeagueRanking> leaguesMpg = leaguesMpgOriginal.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        List<org.blonding.mpg.model.db.League> leagues = gs.getLeagues();
        for (Iterator<org.blonding.mpg.model.db.League> it = leagues.iterator(); it.hasNext();) {
            org.blonding.mpg.model.db.League league = it.next();
            Entry<League, LeagueRanking> leagueMpgEntry = getLeagueMpgById(leaguesMpg, league.getMpgId());
            if (leagueMpgEntry == null) {
                LOG.info("League removed: {} - {}", league.getMpgId(), league.getName());
                it.remove();
                leagueRepository.delete(league);
            } else {
                Long played = getLeagueGamePlayed(leagueMpgEntry.getValue().getRanks());
                LOG.info("League update with games played: {} ({}) -> {}", leagueMpgEntry.getKey().getId(), leagueMpgEntry.getKey().getName(),
                        played);
                league.setName(leagueMpgEntry.getKey().getName());
                league.setType(leagueMpgEntry.getKey().getChampionship().name());
                league.setGamePlayed(played);
                leaguesMpg.remove(leagueMpgEntry.getKey());
            }
        }
        for (Entry<League, LeagueRanking> l : leaguesMpg.entrySet()) {
            Long played = getLeagueGamePlayed(l.getValue().getRanks());
            LOG.info("League add with games played: {} ({}) -> {}", l.getKey().getId(), l.getKey().getName(), played);
            leagues.add(new org.blonding.mpg.model.db.League(l.getKey().getId(), l.getKey().getChampionship().name(), l.getKey().getName(),
                    gs.getYear(), gs.getStatus(), gs.getId(), played));
        }
        leagueRepository.saveAll(leagues);
        return RepeatStatus.FINISHED;
    }

    private static Long getLeagueGamePlayed(List<Rank> ranks) {
        Long gamePlayed = Long.valueOf(-1);
        for (Rank r : ranks) {
            if (r.getPlayed() > gamePlayed) {
                gamePlayed = Long.valueOf(r.getPlayed());
            }
        }
        return gamePlayed;
    }

    private static Entry<League, LeagueRanking> getLeagueMpgById(Map<League, LeagueRanking> leaguesMpg, String mpgId) {
        for (Entry<League, LeagueRanking> e : leaguesMpg.entrySet()) {
            if (e.getKey().getId().equals(mpgId)) {
                return e;
            }
        }
        return null;
    }
}
