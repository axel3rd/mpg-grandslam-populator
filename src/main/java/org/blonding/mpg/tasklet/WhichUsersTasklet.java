package org.blonding.mpg.tasklet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.blonding.mpg.MpgConfig;
import org.blonding.mpg.model.db.MpgUser;
import org.blonding.mpg.model.mpg.League;
import org.blonding.mpg.model.mpg.LeagueRanking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WhichUsersTasklet implements Tasklet {

    private static final Logger LOG = LoggerFactory.getLogger(WhichUsersTasklet.class);

    @Autowired
    private MpgConfig mpgConfig;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        LOG.info("--- Calculate users intersection...");
        @SuppressWarnings("unchecked")
        Map<League, LeagueRanking> leagues = (Map<League, LeagueRanking>) chunkContext.getStepContext().getStepExecution().getJobExecution()
                .getExecutionContext().get("leagues");
        if (leagues == null) {
            throw new UnsupportedOperationException("Object 'leagues' cannot be null here");
        }
        List<MpgUser> usersTmp = new ArrayList<>();
        for (LeagueRanking lr : leagues.values()) {
            List<MpgUser> currentTeamUsers = lr.getTeams().values().stream()
                    .map(team -> new MpgUser(Long.valueOf(team.getUserId().substring(team.getUserId().lastIndexOf('_') + 1)),
                            team.getFirstName() + " " + team.getLastName()))
                    .collect(Collectors.toList());
            if (usersTmp.isEmpty()) {
                usersTmp.addAll(currentTeamUsers);
            } else {
                usersTmp.retainAll(currentTeamUsers);
            }
        }
        List<MpgUser> users = new ArrayList<>();
        for (MpgUser u : usersTmp) {
            if (mpgConfig.getUsersExclude().contains(u.getMpgId())) {
                LOG.info("User '{}' (id={}) removed, excluded", u.getName(), u.getMpgId());
            } else if (!mpgConfig.getUsersInclude().isEmpty() && !mpgConfig.getUsersInclude().contains(u.getMpgId())) {
                LOG.info("User '{}' (id={}) removed, not in users included", u.getName(), u.getMpgId());
            } else {
                LOG.info("User '{}' (id={}) taken into account", u.getName(), u.getMpgId());
                users.add(u);
            }
        }
        if (mpgConfig.isOnlyCheckDatas()) {
            contribution.getStepExecution().setExitStatus(ExitStatus.STOPPED);
        }
        if (users.size() < 2) {
            final String msg = "GrandSlam Cup requires two users minimum";
            LOG.error(msg);
            throw new UnsupportedOperationException(msg);
        }
        contribution.getStepExecution().getExecutionContext().put("users", users);
        return RepeatStatus.FINISHED;
    }

}
