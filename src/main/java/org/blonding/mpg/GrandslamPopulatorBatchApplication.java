package org.blonding.mpg;

import org.blonding.mpg.tasklet.DataBaseUpdateLeaguesTasklet;
import org.blonding.mpg.tasklet.DataBaseUpdateUsersTasklet;
import org.blonding.mpg.tasklet.MpgDatasTasklet;
import org.blonding.mpg.tasklet.WhichUsersTasklet;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.listener.ExecutionContextPromotionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
@SpringBootApplication
public class GrandslamPopulatorBatchApplication {

    @Autowired
    private StepBuilderFactory steps;

    GrandslamPopulatorBatchApplication() {
        super();
    }

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(GrandslamPopulatorBatchApplication.class, args)));
    }

    @Bean
    public Step stepMpgDatas(MpgDatasTasklet mpgDatas) {
        return steps.get("stepMpgDatas").listener(promotionListener()).tasklet(mpgDatas).build();
    }

    @Bean
    public Step stepWhichUsers(WhichUsersTasklet whichUsers) {
        return steps.get("stepWhichUsers").listener(promotionListener()).tasklet(whichUsers).build();
    }

    @Bean
    public Step stepDataBaseUpdateUsers(DataBaseUpdateUsersTasklet dataBaseUpdateUsers) {
        return steps.get("stepDataBaseUpdateUsers").listener(promotionListener()).tasklet(dataBaseUpdateUsers).build();
    }

    @Bean
    public Step stepDataBaseUpdateLeagues(DataBaseUpdateLeaguesTasklet dataBaseUpdateLeagues) {
        return steps.get("stepDataBaseUpdateLeagues").listener(promotionListener()).tasklet(dataBaseUpdateLeagues).build();
    }

    @Bean
    public Job jobGrandSlamPopulator(JobBuilderFactory jobBuilderFactory, MpgDatasTasklet mpgDatas, WhichUsersTasklet whichUsers,
            DataBaseUpdateUsersTasklet dataBaseUpdateUsers, DataBaseUpdateLeaguesTasklet dataBaseUpdateLeagues) {
        JobBuilder jobBuilder = jobBuilderFactory.get("jobGrandSlamPopulator");
        Step stepMpgDatas = stepMpgDatas(mpgDatas);
        Step stepWhichUsers = stepWhichUsers(whichUsers);
        Step stepDataBaseUpdateUsers = stepDataBaseUpdateUsers(dataBaseUpdateUsers);
        Step stepDataBaseUpdateLeagues = stepDataBaseUpdateLeagues(dataBaseUpdateLeagues);
        return jobBuilder.start(stepMpgDatas).next(stepWhichUsers).on(ExitStatus.STOPPED.getExitCode()).end().on(ExitStatus.COMPLETED.getExitCode())
                .to(stepDataBaseUpdateUsers).next(stepDataBaseUpdateLeagues).end().build();
    }

    @Bean
    public ExecutionContextPromotionListener promotionListener() {
        ExecutionContextPromotionListener listener = new ExecutionContextPromotionListener();
        listener.setKeys(new String[] { "leagues", "users" });
        return listener;
    }

}
