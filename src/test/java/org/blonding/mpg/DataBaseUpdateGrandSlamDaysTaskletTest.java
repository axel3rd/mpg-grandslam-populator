package org.blonding.mpg;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.blonding.mpg.model.db.GrandSlam;
import org.blonding.mpg.repository.GrandSlamRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBatchTest
@SpringBootTest
@Sql({ "/schema-test.sql", "/datas-test.sql" })
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class DataBaseUpdateGrandSlamDaysTaskletTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private GrandSlamRepository grandSlamRepository;

    @Test
    void add() throws Exception {
        int initial = grandSlamRepository.findOne(Example.of(GrandSlam.fromCurrentRunning())).orElseThrow().getGrandSlamDays().size();
        ExecutionContext jobExecutionContext = new ExecutionContext();
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("stepDataBaseUpdateGrandSlamDays", jobExecutionContext);
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
        int second = grandSlamRepository.findOne(Example.of(GrandSlam.fromCurrentRunning())).orElseThrow().getGrandSlamDays().size();
        assertEquals(initial + 1, second);
    }
}
