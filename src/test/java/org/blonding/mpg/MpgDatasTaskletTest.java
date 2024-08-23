package org.blonding.mpg;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.blonding.mpg.model.mpg.League;
import org.blonding.mpg.model.mpg.LeagueRanking;
import org.blonding.mpg.model.mpg.Rank;
import org.blonding.mpg.model.mpg.Team;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = { "mpg.leagues.exclude = MNNOTEXIST" })
class MpgDatasTaskletTest extends AbstractTestMpgData {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void defaults() {
        // The first 'MLAX7HMK' is not started
        mockMpgBackend("20210813", "MLAX7HMK", "MLEFEX6G", "MN7VSYBM", "LJV92C9Y", "LLK82D34");

        JobExecution jobExecution = jobLauncherTestUtils.launchStep("stepMpgDatas");
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());

        @SuppressWarnings("unchecked")
        Map<League, LeagueRanking> leagues = (Map<League, LeagueRanking>) jobExecution.getStepExecutions().stream().findFirst().get().getExecutionContext().get("leagues");
        assertNotNull(leagues);

        assertEquals(4, leagues.size());
        for (LeagueRanking ranking : leagues.values()) {

            assertNotNull(ranking.getTeams());
            for (Team team : ranking.getTeams().values()) {
                assertNotNull(team.getName());
                assertFalse(team.getName().isBlank());
                assertNotNull(team.getFirstName());
                assertFalse(team.getFirstName().isBlank());
                assertNotNull(team.getLastName());
                assertFalse(team.getLastName().isBlank());
            }

            assertNotNull(ranking.getRanks());
            for (Rank rank : ranking.getRanks()) {
                assertNotNull(rank.getTeamId());
                assertFalse(rank.getTeamId().isBlank());
            }
        }
    }
}
