package util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;

public class DatabaseConnectionTest {

    @Test
    public void testConexao() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            assertNotNull(connection, "A conexão com o banco de dados falhou.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
