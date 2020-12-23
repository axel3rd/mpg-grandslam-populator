package org.blonding.mpg;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.blonding.mpg.model.db.GrandSlam;
import org.blonding.mpg.repository.GrandSlamRepository;
import org.blonding.mpg.repository.LeagueRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
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
class DataBaseUpdateMiscTest {

    @Autowired
    private GrandSlamRepository grandSlamRepository;

    @Autowired
    private LeagueRepository leagueRepository;

    @Test
    void deleteLeagues() throws Exception {
        GrandSlam gs = grandSlamRepository.findByStatus("Running").stream().findFirst().orElseThrow();
        leagueRepository.deleteAll(gs.getLeagues());
        assertEquals(0, grandSlamRepository.findByStatus("Running").stream().findFirst().orElseThrow().getLeagues().size());
    }

    @Test
    void deleteGrandSlam() throws Exception {
        // Simple test method to validate foreign key in database on the "master" item that the GrandSlam is
        GrandSlam gs = grandSlamRepository.findByStatus("Running").stream().findFirst().orElseThrow();

        // TODO Why FK exception on Team ?
        grandSlamRepository.delete(gs);
        assertEquals(0, grandSlamRepository.findByStatus("Running").size());
    }

}
