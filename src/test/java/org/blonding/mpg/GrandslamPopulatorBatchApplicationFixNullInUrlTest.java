package org.blonding.mpg;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.blonding.mpg.model.db.GrandSlam;
import org.blonding.mpg.model.db.League;
import org.blonding.mpg.model.db.Team;
import org.blonding.mpg.repository.GrandSlamRepository;
import org.blonding.mpg.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.StringUtils;

@SpringBootTest(properties = {})
@Sql({ "/schema-test.sql", "/datas-20210913-test.sql" })
class GrandslamPopulatorBatchApplicationFixNullInUrlTest extends AbstractTestMpgData {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GrandSlamRepository grandSlamRepository;

    @Test
    void defaults() throws Exception {

        // Before fix, fail with: "400 Bad Request from GET https://api.mpg.football/division/null/ranking/standings"

        mockMpgBackend("20210923", "MLMHBPCB", "MLEFEX6G", "MLAX7HMK");

        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            assertFalse(stepExecution.getStepName() + "=" + stepExecution.getExitStatus().getExitDescription(),
                    StringUtils.hasText(stepExecution.getExitStatus().getExitDescription()));
        }
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
        assertEquals(8, playerRepository.findAll().size());

        GrandSlam gs = grandSlamRepository.findOne(Example.of(GrandSlam.fromCurrentRunning())).orElseThrow();
        List<League> leagues = gs.getLeagues();
        assertEquals(3, leagues.size());
        for (League league : leagues) {
            for (Team team : league.getTeams()) {
                assertEquals(league.getGamePlayed(), team.getVictory() + team.getDraw() + team.getDefeat());
            }
        }
    }

}
