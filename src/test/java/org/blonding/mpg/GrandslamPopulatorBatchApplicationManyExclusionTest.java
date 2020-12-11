package org.blonding.mpg;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.blonding.mpg.model.db.GrandSlam;
import org.blonding.mpg.model.db.League;
import org.blonding.mpg.repository.GrandSlamRepository;
import org.blonding.mpg.repository.LeagueRepository;
import org.blonding.mpg.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.StringUtils;

@SpringBootTest(properties = { "mpg.leagues.exclude = MN7VSYBM,MLEFEX6G", "mpg.users.exclude=1570437,2237823,963519,1567579" })
@Sql({ "/schema-test.sql", "/datas-test.sql" })
class GrandslamPopulatorBatchApplicationManyExclusionTest extends AbstractTestMpgData {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GrandSlamRepository grandSlamRepository;

    @Autowired
    private LeagueRepository leagueRepository;

    @Test
    void defaults() throws Exception {
        mockMpgBackend("20201128", "MLAX7HMK", "MLEFEX6G", "MLMHBPCB");

        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            assertFalse(stepExecution.getStepName() + "=" + stepExecution.getExitStatus().getExitDescription(),
                    StringUtils.hasText(stepExecution.getExitStatus().getExitDescription()));
        }
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
        assertEquals(6, playerRepository.findAll().size());

        GrandSlam gs = grandSlamRepository.findByStatus("Running").get(0);
        List<League> leagues = leagueRepository.findByGrandSlamId(gs.getId());
        assertEquals(2, leagues.size());
    }

}
