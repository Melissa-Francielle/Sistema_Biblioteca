package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import util.DatabaseConnection;


public class DevolucaoDao{
        private final Connection connection;

    public DevolucaoDao() {
        try {
            this.connection = DatabaseConnection.getConnection(); 
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao conectar com o banco de dados", e);
        }
    }
    
    //método usado para criar histórico de devolução no banco de dados 
    public boolean devolucao(Integer emprestimoId) throws SQLException{
        String sqldevolve = "INSERT INTO devolucao (emprestimo_id, datadevolucao) VALUES (?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(sqldevolve)){
            stmt.setInt(1, emprestimoId);
            stmt.setDate(2, java.sql.Date.valueOf(LocalDate.now())); 
            int colunasAfetadas = stmt.executeUpdate();
            return colunasAfetadas > 0;
        }catch(SQLException e){
            e.printStackTrace();
        }
            return false;
    }

    //Usado para remover o emprestimo associado ao aluno quando realiza a devolução
    public boolean removerEmprestimoAssociadoAluno(Integer emprestimoId) throws SQLException {
        // SQL para atualizar o status do empréstimo
        String sqlAtualizarStatus = "UPDATE emprestimo SET status = 'devolvido', data_prevista = ? WHERE id = ?";
        
        // SQL para remover itens do empréstimo
        String sqlRemoverItensEmprestimo = "DELETE FROM itememprestimo WHERE emprestimo_id = ?";

        try {
            connection.setAutoCommit(false); 

          
            try (PreparedStatement stmt = connection.prepareStatement(sqlAtualizarStatus)) {
                stmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
                stmt.setInt(2, emprestimoId);
                int linhasAfetadas = stmt.executeUpdate();

                if (linhasAfetadas == 0) {
                    throw new SQLException("Erro: Nenhum empréstimo foi atualizado.");
                }
            }

            
            try (PreparedStatement stmt = connection.prepareStatement(sqlRemoverItensEmprestimo)) {
                stmt.setInt(1, emprestimoId);
                stmt.executeUpdate();
            }

            connection.commit();
            return true;

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            return false;
        } finally {
            connection.setAutoCommit(true); 
        }
    }




}