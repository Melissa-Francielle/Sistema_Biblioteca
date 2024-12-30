package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import util.DatabaseConnection;

public class DebitoDao {
    
    private final Connection connection;

    public DebitoDao() {
        try {
            this.connection = DatabaseConnection.getConnection(); 
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao conectar com o banco de dados", e);
        }
    }
    
    //Remover os debitos de uma matricula 
    public void removerDebitosPorMatricula(Long matricula) throws SQLException {
        String sql = "DELETE FROM debito WHERE aluno_id = (SELECT id FROM aluno WHERE matricula = ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, matricula);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Débitos removidos com sucesso.");
            } else {
                System.out.println("Nenhum débito encontrado para a matrícula fornecida.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao remover débitos: " + e.getMessage());
            throw e; 
        }
    }
    
    //cria os debitos do aluno caso necessário.
    public void criarDebito(Long matricula, Double valor) throws SQLException {
        String sql = "INSERT INTO debito (aluno_id, data, valor) VALUES (?, CURRENT_TIMESTAMP, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, matricula);
            stmt.setDouble(2, valor);
            stmt.executeUpdate();
        }
    }
    
}