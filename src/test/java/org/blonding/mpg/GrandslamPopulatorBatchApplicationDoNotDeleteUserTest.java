package org.blonding.mpg;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
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

@SpringBootTest(properties = { "mpg.leagues.exclude = PYUJXJM,NKCDJTKS", "mpg.users.exclude=0000" })
@Sql({ "/schema-test.sql", "/datas-test.sql" })
class GrandslamPopulatorBatchApplicationDoNotDeleteUserTest extends AbstractTestMpgData {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GrandSlamRepository grandSlamRepository;

    @Test
    void defaults() throws Exception {
        mockMpgBackend("20221103", "PJHY1S98", "PHKWNA3B", "MLMHBPCB");

        // Enforce old GrandSlam closing
        GrandSlam gsOld = grandSlamRepository.findOne(Example.of(GrandSlam.fromCurrentRunning())).orElseThrow();
        Field fieldStatus = gsOld.getClass().getDeclaredField("status");
        fieldStatus.setAccessible(true);
        fieldStatus.set(gsOld, "Closed");
        grandSlamRepository.save(gsOld);

        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            assertFalse(stepExecution.getStepName() + "=" + stepExecution.getExitStatus().getExitDescription(),
                    StringUtils.hasText(stepExecution.getExitStatus().getExitDescription()));
        }
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());

        GrandSlam gs = grandSlamRepository.findOne(Example.of(GrandSlam.fromCurrentRunning())).orElseThrow();
        List<League> leagues = gs.getLeagues();
        assertEquals(3, leagues.size());
        for (League league : leagues) {
            for (Team team : league.getTeams()) {
                assertEquals(league.getGamePlayed(), team.getVictory() + team.getDraw() + team.getDefeat());
            }
        }

        // Mansuy player should always be in database even if not in current division
        // Otherwise 'Archive' feature cannot work
        assertEquals(8, playerRepository.findAll().size());
        assertEquals(7, playerRepository.findAllByActive(true).size());

        // Re-execute twice to be sure it works
        jobExecution = jobLauncherTestUtils.launchJob();
        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            assertFalse(stepExecution.getStepName() + "=" + stepExecution.getExitStatus().getExitDescription(),
                    StringUtils.hasText(stepExecution.getExitStatus().getExitDescription()));
        }
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
        gs = grandSlamRepository.findOne(Example.of(GrandSlam.fromCurrentRunning())).orElseThrow();
        leagues = gs.getLeagues();
        assertEquals(3, leagues.size());
        for (League league : leagues) {
            for (Team team : league.getTeams()) {
                assertEquals(league.getGamePlayed(), team.getVictory() + team.getDraw() + team.getDefeat());
            }
        }
        assertEquals(8, playerRepository.findAll().size());
        assertEquals(7, playerRepository.findAllByActive(true).size());

    }

}
