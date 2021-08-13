package org.blonding.mpg;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(properties = { "mpg.leagues.exclude = MLAX7HMK,MLEFEX6G,MN7VSYBM,LJV92C9Y", "mpg.users.exclude=0000" })
@Sql({ "/schema-test.sql", "/datas-test.sql" })
class GrandslamPopulatorBatchApplicationTwoLeaguesTest extends AbstractTestMpgData {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void defaults() throws Exception {
        mockMpgBackend("20210813", "MLAX7HMK", "MLEFEX6G", "MN7VSYBM", "LJV92C9Y", "LLK82D34");

        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        assertEquals(ExitStatus.FAILED, jobExecution.getExitStatus());
        String exit = jobExecution.getStepExecutions().iterator().next().getExitStatus().getExitDescription();
        assertTrue(exit, exit.contains("GrandSlam Cup requires two leagues minimum"));
    }

}
