package org.blonding.mpg;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.blonding.mpg.model.db.MpgUser;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = { "mpg.leagues.exclude = MN7VSYBM", "mpg.users.exclude=1570437,2237823" })
class WhichUsersTaskletTest extends AbstractTestMpgData {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void defaults() throws Exception {
        mockMpgBackend("20201128", "MLAX7HMK", "MLEFEX6G", "MLMHBPCB");

        JobExecution jobExecutionMpgData = jobLauncherTestUtils.launchStep("stepMpgDatas");
        JobExecution jobExecutionWhichUser = jobLauncherTestUtils.launchStep("stepWhichUsers", jobExecutionMpgData.getExecutionContext());
        assertEquals(ExitStatus.COMPLETED, jobExecutionWhichUser.getExitStatus());

        // Should not in the list due to exclusions
        // 'Maxime B.' (id=2237823)
        // 'Tony S.' (id=1570437)
        @SuppressWarnings("unchecked")
        List<MpgUser> users = (List<MpgUser>) jobExecutionWhichUser.getStepExecutions().stream().findFirst().get().getExecutionContext().get("users");
        assertNotNull(users);
        assertEquals(8, users.size());
    }
}
