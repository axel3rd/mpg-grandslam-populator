package org.blonding.mpg;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.batch.JpaBatchConfigurer;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings("deprecation")
@Configuration
public class BatchConfiguration extends JpaBatchConfigurer {

    protected BatchConfiguration(BatchProperties properties, DataSource dataSource, TransactionManagerCustomizers transactionManagerCustomizers,
            EntityManagerFactory entityManagerFactory) {
        super(properties, dataSource, transactionManagerCustomizers, entityManagerFactory);
    }

    @Override
    protected JobRepository createJobRepository() throws Exception {
        // No other way to not use SpringBatch metadata in DataBase, with the use of another DataSource for Business
        // Extends DefaultBatchConfigurer and using 'setDataSource' which define nothing disable the transactionnal context
        return new MapJobRepositoryFactoryBean().getJobRepository();
    }

}