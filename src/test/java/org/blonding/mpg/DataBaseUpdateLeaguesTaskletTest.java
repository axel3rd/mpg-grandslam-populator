package org.blonding.mpg;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.blonding.mpg.model.db.GrandSlam;
import org.blonding.mpg.model.db.League;
import org.blonding.mpg.repository.GrandSlamRepository;
import org.blonding.mpg.repository.LeagueRepository;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(properties = { "mpg.leagues.exclude = MN7VSYBM", "mpg.users.exclude=1570437,2237823" })
@Sql({ "/schema-test.sql", "/datas-test.sql" })
class DataBaseUpdateLeaguesTaskletTest extends AbstractTestMpgData {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private GrandSlamRepository grandSlamRepository;

    @Autowired
    private LeagueRepository leagueRepository;

    @Test
    void update() throws Exception {
        mockMpgBackend("20201128", "MLAX7HMK", "MLEFEX6G", "MLMHBPCB");

        JobExecution jobExecutionMpgData = jobLauncherTestUtils.launchStep("stepMpgDatas");
        JobExecution jobExecutionWhichUser = jobLauncherTestUtils.launchStep("stepDataBaseUpdateLeagues", jobExecutionMpgData.getExecutionContext());
        assertEquals(ExitStatus.COMPLETED, jobExecutionWhichUser.getExitStatus());

        GrandSlam gs = grandSlamRepository.findByStatus("Running").get(0);
        List<League> leagues = leagueRepository.findByGrandSlamId(gs.getId());
        assertEquals(3, leagues.size());
    }

    @Test
    void delete() throws Exception {
        mockMpgBackend("20201128", "MLAX7HMK", "MLEFEX6G", "MLMHBPCB");

        GrandSlam gs = grandSlamRepository.findByStatus("Running").get(0);

        League l = new League("M0000000", "REMOVE", "To remove", gs.getYear(), gs.getStatus(), gs.getId(), Long.valueOf(0));
        leagueRepository.save(l);

        JobExecution jobExecutionMpgData = jobLauncherTestUtils.launchStep("stepMpgDatas");
        JobExecution jobExecutionWhichUser = jobLauncherTestUtils.launchStep("stepDataBaseUpdateLeagues", jobExecutionMpgData.getExecutionContext());
        assertEquals(ExitStatus.COMPLETED, jobExecutionWhichUser.getExitStatus());

        List<League> leagues = leagueRepository.findByGrandSlamId(gs.getId());
        assertEquals(3, leagues.size());
    }

    @Test
    void add() throws Exception {
        mockMpgBackend("20201128", "MLAX7HMK", "MLEFEX6G", "MLMHBPCB");

        GrandSlam gs = grandSlamRepository.findByStatus("Running").get(0);
        leagueRepository.delete(leagueRepository.findByGrandSlamId(gs.getId()).get(0));

        JobExecution jobExecutionMpgData = jobLauncherTestUtils.launchStep("stepMpgDatas");
        JobExecution jobExecutionWhichUser = jobLauncherTestUtils.launchStep("stepDataBaseUpdateLeagues", jobExecutionMpgData.getExecutionContext());
        assertEquals(ExitStatus.COMPLETED, jobExecutionWhichUser.getExitStatus());

        List<League> leagues = leagueRepository.findByGrandSlamId(gs.getId());
        assertEquals(3, leagues.size());
    }

}
