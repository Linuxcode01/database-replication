    package com.chandan.databasereplication.config;

    import org.springframework.beans.factory.annotation.Qualifier;
    import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
    import org.springframework.boot.context.properties.ConfigurationProperties;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.jdbc.core.JdbcTemplate;

    import javax.sql.DataSource;

    @Configuration
    public class DatabaseConfig {

        @Bean
        @ConfigurationProperties("spring.datasource.primary")
        public DataSourceProperties primaryProperties() {
            return new DataSourceProperties();
        }

        @Bean
        public DataSource primaryDataSource() {
            return primaryProperties()
                    .initializeDataSourceBuilder()
                    .build();
        }

        @Bean
        @ConfigurationProperties("spring.datasource.replica1")
        public DataSourceProperties replica1Properties() {
            return new DataSourceProperties();
        }

        @Bean
        public DataSource replica1DataSource() {
            return replica1Properties()
                    .initializeDataSourceBuilder()
                    .build();
        }

        @Bean
        @ConfigurationProperties("spring.datasource.replica2")
        public DataSourceProperties replica2Properties() {
            return new DataSourceProperties();
        }

        @Bean
        public DataSource replica2DataSource() {
            return replica2Properties()
                    .initializeDataSourceBuilder()
                    .build();
        }

        @Bean("primaryJdbc")
        public JdbcTemplate primaryJdbc(
                @Qualifier("primaryDataSource") DataSource ds) {
            return new JdbcTemplate(ds);
        }

        @Bean("replica1Jdbc")
        public JdbcTemplate replica1Jdbc(
                @Qualifier("replica1DataSource") DataSource ds) {
            return new JdbcTemplate(ds);
        }

        @Bean("replica2Jdbc")
        public JdbcTemplate replica2Jdbc(
                @Qualifier("replica2DataSource") DataSource ds) {
            return new JdbcTemplate(ds);
        }
    }
