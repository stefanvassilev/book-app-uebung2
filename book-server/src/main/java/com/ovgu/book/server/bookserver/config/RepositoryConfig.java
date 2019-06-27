package com.ovgu.book.server.bookserver.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnCloudPlatform;
import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

@Configuration
@Profile("cf")
public class RepositoryConfig extends AbstractCloudConfig {

    @Bean
    public DataSource cfDataSource() {
        CloudFactory cloudFactory = new CloudFactory();
        Cloud cloud = cloudFactory.getCloud();
        return cloud.getSingletonServiceConnector(DataSource.class, null);
    }

}
