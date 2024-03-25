package edu.java.scrapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContainerTest extends IntegrationTest {
    @Test
    void containerStartupTest() {
        assertTrue(POSTGRES.isRunning());
    }

    @Test
    @SneakyThrows
    void tableMigrationTest() {
        Connection c =
            DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());

        PreparedStatement stmt = c.prepareStatement(
            "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public' AND table_type = 'BASE TABLE'");

        ResultSet resultSet = stmt.executeQuery();

        List<String> tables = new ArrayList<>();
        while (resultSet.next()) {
            tables.add(resultSet.getString("table_name"));
        }

        assertAll(
            () -> assertTrue(tables.contains("chat")),
            () -> assertTrue(tables.contains("link")),
            () -> assertTrue(tables.contains("chat_link_mapping"))
        );

    }
}
