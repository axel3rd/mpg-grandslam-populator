package org.blonding.mpg;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.blonding.mpg.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(properties = { "mpg.leagues.exclude = MN7VSYBM", "mpg.users.exclude=1570437,2237823" })
@Sql({ "/schema-test.sql", "/datas-test.sql" })
class GrandslamPopulatorBatchApplicationTests extends AbstractTestMpgData {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    void defaults() throws Exception {
        mockMpgBackend("20201128", "MLAX7HMK", "MLEFEX6G", "MLMHBPCB");

        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
        assertEquals(8, playerRepository.findAll().size());
    }

}
