//package com.example.LearnAuthentication.config;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableConfigurationProperties
//public class DataBaseConfig {
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource.primary")
//    public JndiPropertyHolder primary() {
//        return new JndiPropertyHolder();
//    }
//
//    @Bean
//    @Primary
//    public DataSource primaryDataSource() {
//        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
//        DataSource dataSource = dataSourceLookup.getDataSource(primary().getJndiName());
//        return dataSource;
//    }
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource.postgres")
//    public JndiPropertyHolder secondary() {
//        return new JndiPropertyHolder();
//    }
//
//    @Bean
//    public DataSource postgresDataSource() {
//        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
//        DataSource dataSource = dataSourceLookup.getDataSource(secondary().getJndiName());
//        return dataSource;
//    }
//
//
//    @Bean
//    @Primary
//    @Qualifier("jdbcTemplatePrimary")
//    public JdbcTemplate primaryJdbcTemplate() {
//        return new JdbcTemplate(primaryDataSource());
//    }
//
//
//
//    @Bean
//    @Qualifier("jdbcTemplatePostGres")
//    public JdbcTemplate postgresJdbcTemplate() {
//        return new JdbcTemplate(postgresDataSource());
//    }
//
//    private static class JndiPropertyHolder {
//        private String jndiName;
//
//        public String getJndiName() {
//            return jndiName;
//        }
//
//        public void setJndiName(String jndiName) {
//            this.jndiName = jndiName;
//        }
//    }
//
//}
//
