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

@SpringBootTest(properties = { "mpg.leagues.exclude = MLAX7HMK", "mpg.leagues.include = LJV92C9Y,MLEFEX6G", "mpg.users.exclude=000000" })
@Sql({ "/schema-test.sql", "/datas-test.sql" })
class DataBaseUpdateLeaguesTaskletTest extends AbstractTestMpgData {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private GrandSlamRepository grandSlamRepository;

    @Autowired
    private LeagueRepository leagueRepository;

    @Test
    void update() {
        mockMpgBackend("20210813", "MLAX7HMK", "MLEFEX6G", "MN7VSYBM", "LJV92C9Y", "LLK82D34");

        JobExecution jobExecutionMpgData = jobLauncherTestUtils.launchStep("stepMpgDatas");
        JobExecution jobExecutionWhichUser = jobLauncherTestUtils.launchStep("stepDataBaseUpdateLeagues", jobExecutionMpgData.getExecutionContext());
        assertEquals(ExitStatus.COMPLETED, jobExecutionWhichUser.getExitStatus());

        GrandSlam gs = grandSlamRepository.findOne(Example.of(GrandSlam.fromCurrentRunning())).orElseThrow();
        assertEquals(2, gs.getLeagues().size());
    }

    @Test
    void delete() {
        mockMpgBackend("20210813", "MLAX7HMK", "MLEFEX6G", "MN7VSYBM", "LJV92C9Y", "LLK82D34");

        GrandSlam gs = grandSlamRepository.findOne(Example.of(GrandSlam.fromCurrentRunning())).orElseThrow();

        League l = new League("M0000000", "REMOVE", "To remove", gs.getYear(), gs.getStatus(), gs.getId(), 0);
        leagueRepository.save(l);

        JobExecution jobExecutionMpgData = jobLauncherTestUtils.launchStep("stepMpgDatas");
        JobExecution jobExecutionWhichUser = jobLauncherTestUtils.launchStep("stepDataBaseUpdateLeagues", jobExecutionMpgData.getExecutionContext());
        assertEquals(ExitStatus.COMPLETED, jobExecutionWhichUser.getExitStatus());

        assertEquals(2, grandSlamRepository.findOne(Example.of(GrandSlam.fromCurrentRunning())).orElseThrow().getLeagues().size());
    }

    @Test
    void add() {
        mockMpgBackend("20210813", "MLAX7HMK", "MLEFEX6G", "MN7VSYBM", "LJV92C9Y", "LLK82D34");

        GrandSlam gs = grandSlamRepository.findOne(Example.of(GrandSlam.fromCurrentRunning())).orElseThrow();
        // PL retrieve in first, not included in configuration => 1
        leagueRepository.delete(gs.getLeagues().get(1));

        JobExecution jobExecutionMpgData = jobLauncherTestUtils.launchStep("stepMpgDatas");
        JobExecution jobExecutionWhichUser = jobLauncherTestUtils.launchStep("stepDataBaseUpdateLeagues", jobExecutionMpgData.getExecutionContext());
        assertEquals(ExitStatus.COMPLETED, jobExecutionWhichUser.getExitStatus());

        assertEquals(2, grandSlamRepository.findOne(Example.of(GrandSlam.fromCurrentRunning())).orElseThrow().getLeagues().size());
    }

}
