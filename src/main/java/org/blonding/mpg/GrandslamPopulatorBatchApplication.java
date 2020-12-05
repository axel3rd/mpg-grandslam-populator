package org.blonding.mpg;

import org.blonding.mpg.tasklet.MpgDatasTasklet;
import org.blonding.mpg.tasklet.WhichUsersTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
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
    public Job jobGrandSlamPopulator(JobBuilderFactory jobBuilderFactory, MpgDatasTasklet mpgDatas, WhichUsersTasklet whichUsers) {
        return jobBuilderFactory.get("jobGrandSlamPopulator").start(stepMpgDatas(mpgDatas)).next(stepWhichUsers(whichUsers)).build();
    }

    @Bean
    public ExecutionContextPromotionListener promotionListener() {
        ExecutionContextPromotionListener listener = new ExecutionContextPromotionListener();
        listener.setKeys(new String[] { "leagues" });
        return listener;
    }

}
