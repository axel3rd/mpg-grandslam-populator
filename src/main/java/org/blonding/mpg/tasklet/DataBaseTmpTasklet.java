package org.blonding.mpg.tasklet;

import java.util.List;

import org.blonding.mpg.model.db.Player;
import org.blonding.mpg.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataBaseTmpTasklet implements Tasklet {

    private static final Logger LOG = LoggerFactory.getLogger(DataBaseTmpTasklet.class);

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        LOG.info("Check DataBase consistency ...");
        List<Player> players = playerRepository.findAll();

        return RepeatStatus.FINISHED;
    }

}
