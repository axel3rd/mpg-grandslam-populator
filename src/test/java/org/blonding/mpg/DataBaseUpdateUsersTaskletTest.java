package org.blonding.mpg;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.blonding.mpg.model.bean.MpgUser;
import org.blonding.mpg.model.db.Player;
import org.blonding.mpg.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBatchTest
@SpringBootTest
@Sql({ "/schema-test.sql", "/datas-test.sql" })
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class DataBaseUpdateUsersTaskletTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    void update() {
        ExecutionContext jobExecutionContext = new ExecutionContext();
        jobExecutionContext.put("users", getMpgUsers());
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("stepDataBaseUpdateUsers", jobExecutionContext);
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
        List<Player> players = playerRepository.findAll();
        assertEquals(8, players.size());
        for (Player player : players) {
            assertTrue(player.getName(), player.getName().endsWith("."));
        }
    }

    @Test
    void deactivate() {
        ExecutionContext jobExecutionContext = new ExecutionContext();
        List<MpgUser> users = getMpgUsers();
        users.remove(0);
        jobExecutionContext.put("users", users);
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("stepDataBaseUpdateUsers", jobExecutionContext);
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
        List<Player> players = playerRepository.findAll();
        assertEquals(8, players.size());
        players = playerRepository.findAllByActive(true);
        assertEquals(7, players.size());
        for (Player player : players) {
            assertTrue(player.getName(), player.getName().endsWith("."));
        }
    }

    @Test
    void add() {
        playerRepository.deleteAll();
        ExecutionContext jobExecutionContext = new ExecutionContext();
        jobExecutionContext.put("users", getMpgUsers());
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("stepDataBaseUpdateUsers", jobExecutionContext);
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
        List<Player> players = playerRepository.findAll();
        assertEquals(8, players.size());
        for (Player player : players) {
            assertTrue(player.getName(), player.getName().endsWith("."));
        }
    }

    private List<MpgUser> getMpgUsers() {
        List<MpgUser> users = new ArrayList<>();
        users.add(new MpgUser(1567579, "Mat V."));
        users.add(new MpgUser(1520001, "Typhaine A."));
        users.add(new MpgUser(953561, "Mansuy D."));
        users.add(new MpgUser(35635, "Bertrand P."));
        users.add(new MpgUser(600737, "Celine B."));
        users.add(new MpgUser(963519, "David C."));
        users.add(new MpgUser(1662232, "Youenn D."));
        users.add(new MpgUser(955966, "Alix L."));
        return users;
    }
}
