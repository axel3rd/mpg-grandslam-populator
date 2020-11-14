package org.blonding.mpg;

import org.blonding.mpg.tasklet.DataBaseTmpTasklet;
import org.blonding.mpg.tasklet.MpgDatasTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
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

    @Autowired
    private DataBaseTmpTasklet taskTmpkDB;

    @Autowired
    private MpgDatasTasklet mpgDatas;

    GrandslamPopulatorBatchApplication() {
        super();
    }

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(GrandslamPopulatorBatchApplication.class, args)));
    }

    @Bean
    public Flow flowTmpDB() {
        return new FlowBuilder<Flow>("flowCheckDB").start(steps.get("stepCheckDB").tasklet(taskTmpkDB).build()).build();
    }

    @Bean
    public Flow flowMpgDatas() {
        return new FlowBuilder<Flow>("flowMpgDatas").start(steps.get("stepMpgDatas").tasklet(mpgDatas).build()).build();
    }

    @Bean
    public Job jobGrandSlamPopulator(JobBuilderFactory jobBuilderFactory) {
        return jobBuilderFactory.get("jobGrandSlamPopulator").start(flowMpgDatas()).end().build();
    }

}
