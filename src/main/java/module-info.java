module app {
    // Dependências do JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;

    // Testes (se aplicável ao ambiente principal)
    requires org.junit.jupiter.api;
    requires java.base;

    // Abertura de pacotes para JavaFX (reflexão via FXMLLoader)
    opens app to javafx.fxml;

    // Exportação de pacotes
    exports app;
    exports util;
    exports modelo;
    exports dao;
}
