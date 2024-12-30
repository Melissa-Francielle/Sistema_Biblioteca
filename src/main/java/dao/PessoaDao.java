package dao;

import modelo.Pessoa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.DatabaseConnection;

public class PessoaDao {

    private Connection connection;
    
        // Construtor para inicializar a conexão com o banco
    public PessoaDao() {
         try {
            this.connection = DatabaseConnection.getConnection(); 
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao conectar com o banco de dados", e);
        }; 
    }

    // Método para inserir uma pessoa
    public void inserir(Pessoa pessoa) {
        String sql = "INSERT INTO pessoa (nome, endereco) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getEndereco());

            stmt.executeUpdate();

            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pessoa.setCodigo(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Pessoa buscarPorCodigo(Integer codigo) {
        Pessoa pessoa = null;
        String sql = "SELECT * FROM pessoa WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    pessoa = new Pessoa();
                    pessoa.setCodigo(rs.getInt("id"));
                    pessoa.setNome(rs.getString("nome"));
                    pessoa.setEndereco(rs.getString("endereco"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pessoa;
    }

    // Método para listar todas as pessoas
    public List<Pessoa> listarTodos() {
        List<Pessoa> lista = new ArrayList<>();
        String sql = "SELECT * FROM pessoa";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setCodigo(rs.getInt("codigo"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setEndereco(rs.getString("endereco"));
                lista.add(pessoa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Método para atualizar uma pessoa
    public void atualizar(Pessoa pessoa) {
        String sql = "UPDATE pessoa SET nome = ?, endereco = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getEndereco());
            stmt.setInt(3, pessoa.getCodigo());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para excluir uma pessoa
    public void excluir(Integer codigo) {
        String sql = "DELETE FROM pessoa WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, codigo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
