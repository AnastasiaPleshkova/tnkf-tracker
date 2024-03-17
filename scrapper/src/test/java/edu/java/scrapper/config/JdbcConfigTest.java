package edu.java.scrapper.config;

import edu.java.scrapper.IntegrationTest;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

@Profile("test")
@Configuration
public class JdbcConfigTest {

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(DataSourceBuilder.create()
            .url(IntegrationTest.POSTGRES.getJdbcUrl())
            .username(IntegrationTest.POSTGRES.getUsername())
            .password(IntegrationTest.POSTGRES.getPassword())
            .build());

    }

}
