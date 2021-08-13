package org.blonding.mpg;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.blonding.mpg.model.bean.MpgUser;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = { "mpg.leagues.exclude = MN7VSYBM,MLAX7HMK", "mpg.users.exclude=963519,1567579" })
class WhichUsersTaskletTest extends AbstractTestMpgData {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void defaults() throws Exception {
        mockMpgBackend("20210813", "MLAX7HMK", "MLEFEX6G", "MN7VSYBM", "LJV92C9Y", "LLK82D34");

        JobExecution jobExecutionMpgData = jobLauncherTestUtils.launchStep("stepMpgDatas");
        JobExecution jobExecutionWhichUser = jobLauncherTestUtils.launchStep("stepWhichUsers", jobExecutionMpgData.getExecutionContext());
        assertEquals(ExitStatus.COMPLETED, jobExecutionWhichUser.getExitStatus());

        // Should not in the list due to exclusions
        // 'David' (id=963519)
        // 'Mat' (id=1567579)
        @SuppressWarnings("unchecked")
        List<MpgUser> users = (List<MpgUser>) jobExecutionWhichUser.getStepExecutions().stream().findFirst().get().getExecutionContext().get("users");
        assertNotNull(users);
        assertEquals(4, users.size());
    }
}
