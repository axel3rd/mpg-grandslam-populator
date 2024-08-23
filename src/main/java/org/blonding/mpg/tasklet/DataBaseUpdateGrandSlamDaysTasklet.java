package org.blonding.mpg.tasklet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.blonding.mpg.model.bean.PlayerDayJson;
import org.blonding.mpg.model.db.GrandSlam;
import org.blonding.mpg.model.db.GrandSlamDay;
import org.blonding.mpg.model.db.League;
import org.blonding.mpg.model.db.Team;
import org.blonding.mpg.repository.GrandSlamDayRepository;
import org.blonding.mpg.repository.GrandSlamRepository;
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
public class DataBaseUpdateGrandSlamDaysTasklet implements Tasklet {

    private static final Logger LOG = LoggerFactory.getLogger(DataBaseUpdateGrandSlamDaysTasklet.class);

    private final GrandSlamRepository grandSlamRepository;

    private final GrandSlamDayRepository grandSlamDayRepository;

    @Autowired
    private DataBaseUpdateGrandSlamDaysTasklet(GrandSlamRepository grandSlamRepository, GrandSlamDayRepository grandSlamDayRepository) {
        super();
        this.grandSlamRepository = grandSlamRepository;
        this.grandSlamDayRepository = grandSlamDayRepository;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // Interact directly with DataBase without MPG data, previously updated in different tables
        LOG.info("--- Update GrandSlam days...");
        GrandSlam gs = grandSlamRepository.findOne(Example.of(GrandSlam.fromCurrentRunning())).orElseThrow();
        int day = gs.getLeagues().stream().mapToInt(League::getGamePlayed).sum();
        LOG.info("Current day: {}", day);

        // Compute global ranking and label
        Map<Integer, PlayerDayJson> players = new HashMap<>();
        List<String> labels = new ArrayList<>();
        for (League league : gs.getLeagues()) {
            labels.add(league.getType() + "j" + league.getGamePlayed());
            for (Team team : league.getTeams()) {
                PlayerDayJson player = players.get(team.getPlayerId());
                if (player == null) {
                    player = new PlayerDayJson();
                    player.setPlayer(team.getPlayerId());
                    players.put(player.getPlayer(), player);
                }
                player.setPts(player.getPts() + team.getVictory() * 3 + team.getDraw());
                player.setDiff(player.getDiff() + team.getGoalDiff());
            }
        }

        // Sort by pts and golf diff
        List<PlayerDayJson> playersOrdered = players.values().stream().sorted(Comparator.comparingInt(PlayerDayJson::getPts).thenComparingInt(PlayerDayJson::getDiff).reversed())
                .collect(Collectors.toCollection(ArrayList::new));

        // Add position
        int pos = 0;
        for (PlayerDayJson player : playersOrdered) {
            player.setPos(++pos);
        }

        String label = "J" + day + " : " + String.join(" ", labels);

        GrandSlamDay gsd = grandSlamDayRepository.findOneByGrandSlamIdAndDay(gs.getId(), day);
        if (gsd == null) {
            gsd = new GrandSlamDay(gs.getId(), day);
        }
        gsd.setLabel(label);
        gsd.setPlayers(playersOrdered);

        if (LOG.isInfoEnabled()) {
            LOG.info("Updating '{}': {}", label, getListString(playersOrdered));
        }
        grandSlamDayRepository.save(gsd);
        return RepeatStatus.FINISHED;
    }

    private static String getListString(List<PlayerDayJson> players) {
        if (players == null) {
            return "";
        }
        return Arrays.toString(players.toArray());
    }

}
