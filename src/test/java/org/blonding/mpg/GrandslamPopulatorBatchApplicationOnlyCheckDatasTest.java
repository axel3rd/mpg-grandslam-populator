package org.blonding.mpg;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = { "mpg.leagues.exclude = MLAX7HMK", "job.check.only=true" })
class GrandslamPopulatorBatchApplicationOnlyCheckDatasTest extends AbstractTestMpgData {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void defaults() throws Exception {
        mockMpgBackend("20210813", "MLAX7HMK", "MLEFEX6G", "MN7VSYBM", "LJV92C9Y", "LLK82D34");

        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }

}
