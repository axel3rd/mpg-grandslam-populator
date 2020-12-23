package org.blonding.mpg;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import org.springframework.data.domain.Example;
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

        GrandSlam gs = grandSlamRepository.findOne(Example.of(GrandSlam.fromCurrentRunning())).orElseThrow();
        assertEquals(3, gs.getLeagues().size());
    }

    @Test
    void delete() throws Exception {
        mockMpgBackend("20201128", "MLAX7HMK", "MLEFEX6G", "MLMHBPCB");

        GrandSlam gs = grandSlamRepository.findOne(Example.of(GrandSlam.fromCurrentRunning())).orElseThrow();

        League l = new League("M0000000", "REMOVE", "To remove", gs.getYear(), gs.getStatus(), gs.getId(), 0);
        leagueRepository.save(l);

        JobExecution jobExecutionMpgData = jobLauncherTestUtils.launchStep("stepMpgDatas");
        JobExecution jobExecutionWhichUser = jobLauncherTestUtils.launchStep("stepDataBaseUpdateLeagues", jobExecutionMpgData.getExecutionContext());
        assertEquals(ExitStatus.COMPLETED, jobExecutionWhichUser.getExitStatus());

        assertEquals(3, gs.getLeagues().size());
    }

    @Test
    void add() throws Exception {
        mockMpgBackend("20201128", "MLAX7HMK", "MLEFEX6G", "MLMHBPCB");

        GrandSlam gs = grandSlamRepository.findOne(Example.of(GrandSlam.fromCurrentRunning())).orElseThrow();
        leagueRepository.delete(gs.getLeagues().get(0));

        JobExecution jobExecutionMpgData = jobLauncherTestUtils.launchStep("stepMpgDatas");
        JobExecution jobExecutionWhichUser = jobLauncherTestUtils.launchStep("stepDataBaseUpdateLeagues", jobExecutionMpgData.getExecutionContext());
        assertEquals(ExitStatus.COMPLETED, jobExecutionWhichUser.getExitStatus());

        assertEquals(3, gs.getLeagues().size());
    }

}
