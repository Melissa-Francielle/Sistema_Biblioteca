package dao;

import modelo.Bibliotecario;
import java.sql.*;
import util.DatabaseConnection;

public class BibliotecarioDao {
    private final Connection connection;

    // Construtor para inicializar a conexão com o banco
    public BibliotecarioDao() {
         try {
            this.connection = DatabaseConnection.getConnection(); 
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao conectar com o banco de dados", e);
        }; 
    }
    // Método para inserir um bibliotecario
    public void inserir(Bibliotecario bibliotecario) {
        String sql = "INSERT INTO bibliotecario (nome, endereco, login, senha) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, bibliotecario.getNome());
            stmt.setString(2, bibliotecario.getEndereco());
            stmt.setString(3, bibliotecario.getLogin());
            stmt.setString(4, bibliotecario.getSenha());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    
}
