package edu.java.scrapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContainerTest extends IntegrationTest {
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
            "SELECT EXISTS (SELECT FROM information_schema.tables WHERE table_name = ?)");

        assertAll(
            () -> {
                stmt.setString(1, "chat");
                assertTrue(stmt.executeQuery().next());
            },
            () -> {
                stmt.setString(1, "link");
                assertTrue(stmt.executeQuery().next());
            },
            () -> {
                stmt.setString(1, "chat_link_mapping");
                assertTrue(stmt.executeQuery().next());
            }
        );
    }
}
