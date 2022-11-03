package org.blonding.mpg.tasklet;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.blonding.mpg.model.bean.MpgUser;
import org.blonding.mpg.model.db.Player;
import org.blonding.mpg.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DataBaseUpdateUsersTasklet implements Tasklet {

    private static final Logger LOG = LoggerFactory.getLogger(DataBaseUpdateUsersTasklet.class);

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        LOG.info("--- Update players names...");
        @SuppressWarnings("unchecked")
        List<MpgUser> users = (List<MpgUser>) contribution.getStepExecution().getJobExecution().getExecutionContext().get("users");
        if (users == null) {
            throw new UnsupportedOperationException("Object 'users' cannot be null here");
        }
        Map<Long, String> usersMap = users.stream().collect(Collectors.toMap(MpgUser::getMpgId, MpgUser::getName));

        List<Player> players = playerRepository.findAll();
        for (Iterator<Player> it = players.iterator(); it.hasNext();) {
            Player player = it.next();
            String userName = usersMap.get(player.getMpgId());
            if (!StringUtils.hasText(userName)) {
                // Deactivate if not in current MPG game
                if (player.isActive()) {
                    LOG.info("Player deactivated: '{}'", player.getName());
                    player.setActive(false);
                }
            } else if (userName.equals(player.getName())) {
                // Nothing but enforce active player
                it.remove();
                player.setActive(true);
                usersMap.remove(player.getMpgId());
            } else {
                // Update name
                LOG.info("Player name update: '{}' -> '{}'", player.getName(), userName);
                player.setName(userName);
                player.setActive(true);
                usersMap.remove(player.getMpgId());
            }
        }
        for (Entry<Long, String> e : usersMap.entrySet()) {
            LOG.info("Player added: '{}'", e.getValue());
            players.add(new Player(e.getKey(), e.getValue()));
        }
        playerRepository.saveAll(players);
        return RepeatStatus.FINISHED;
    }

}
