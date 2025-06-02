package com.neutec.blog.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.SharedEntityManagerCreator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "com.neutec.blog.db.entity")
@EnableJpaRepositories(
    entityManagerFactoryRef = "mysqlEntityManagerFactory",
    transactionManagerRef = "mysqlTransactionManager",
    basePackages = "com.neutec.blog.db.dao")
public class JpaConfig {
    // JPA 相關配置
    @Primary
    @Bean(name = "mysqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
        EntityManagerFactoryBuilder builder, DataSource mysqlDataSource) {

        Map<String, String> mysqlJpaProperties = new HashMap<>();

//        mysqlJpaProperties.put(DIALECT, MySQLDialect.class.getName());
//        mysqlJpaProperties.put(PHYSICAL_NAMING_STRATEGY, PhysicalNamingStrategyStandardImpl.class.getName());
//        mysqlJpaProperties.put(ENABLE_LAZY_LOAD_NO_TRANS,"true");
//        mysqlJpaProperties.put("hibernate.proc.param_null_passing", "true");
        return builder
            .dataSource(mysqlDataSource)
            .packages("com.neutec.blog.db.entity")
            .persistenceUnit("mysql")
            .properties(mysqlJpaProperties)
            .build();
    }

    @Primary
    @Bean(name = "mysqlEntityManager")
    public EntityManager mysqlEntityManager(@Qualifier("mysqlEntityManagerFactory") LocalContainerEntityManagerFactoryBean factory) {
        return SharedEntityManagerCreator.createSharedEntityManager(Objects.requireNonNull(factory.getObject()));
    }

    @Primary
    @Bean(name = "mysqlTransactionManager")
    public PlatformTransactionManager mysqlTransactionManager(
        @Qualifier("mysqlEntityManagerFactory") EntityManagerFactory mysqlEntityManagerFactory) {

        return new JpaTransactionManager(mysqlEntityManagerFactory);
    }

}
