package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import modelo.Emprestimo;
import modelo.ItemEmprestimo;
import util.DatabaseConnection;


public class EmprestimoDao{
        private final Connection connection;

    public EmprestimoDao() {
            try {
                this.connection = DatabaseConnection.getConnection(); // Conexão com o banco
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao conectar com o banco de dados", e);
            }
        }

    //Inserção do emprestimo no banco de dados
    public Boolean inserirEmprestimo(Long matricula, Integer livro_id) throws SQLException {
        String sqlBuscarAluno = "SELECT id FROM aluno WHERE matricula = ?"; 
        String sqlEmprestimo = "INSERT INTO emprestimo (aluno_id, multa, data_emprestimo, data_prevista) VALUES (?, ?, ?, ?) RETURNING id";
        String sqlItem = "INSERT INTO itememprestimo (emprestimo_id, livro_id, datadevolucao, dataprevista) VALUES (?, ?, ?, ?)";
        Boolean sucesso = false;

        try {
            connection.setAutoCommit(false);

            
            //Utilizando para obter o ID de aluno pela matricula 
            Long aluno_id = null;
            try (PreparedStatement stmt = connection.prepareStatement(sqlBuscarAluno)) {
                stmt.setLong(1, matricula);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        aluno_id = rs.getLong("id");
                    } else {
                        throw new SQLException("Aluno não encontrado para a matrícula fornecida: " + matricula);
                    }
                }
            }

         
            //Pega para isnerção na tabela/combo box
            Long emprestimo_id = null;
           
            try (PreparedStatement stmt = connection.prepareStatement(sqlEmprestimo, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setLong(1, aluno_id);
                stmt.setDouble(2, 0.0);
                stmt.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
                stmt.setDate(4, java.sql.Date.valueOf(LocalDate.now().plusDays(7))); // calcula para uma previsão de 7 dias 

                int linhas = stmt.executeUpdate();

                if (linhas > 0) {
                    try (ResultSet rs = stmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            emprestimo_id = rs.getLong(1);
                        }
                    }
                }
                if (emprestimo_id == null) {
                    throw new SQLException("Falha ao inserir empréstimo, ID não gerado.");
                }
            }

            // Insere na tabela itememprestimo
            ItemEmprestimo item = new ItemEmprestimo();
            try (PreparedStatement stmt_two = connection.prepareStatement(sqlItem)) {
                stmt_two.setLong(1, emprestimo_id);
                stmt_two.setInt(2, livro_id);
                stmt_two.setDate(3, java.sql.Date.valueOf(LocalDate.now().plusDays(3))); // Define a data prevista como datadevolucao
                stmt_two.setDate(4, java.sql.Date.valueOf(LocalDate.now().plusDays(7))); // Exemplo: previsão para 7 dias
                int linhasItem = stmt_two.executeUpdate();

                if (linhasItem <= 0) {
                    throw new SQLException("Falha ao inserir item de empréstimo.");
                }
            }

            connection.commit();
            sucesso = true;
        } catch (SQLException e) {
            connection.rollback(); 
            throw new RuntimeException("Erro ao inserir as informações: " + e.getMessage(), e);
        } finally {
            connection.setAutoCommit(true); 
        }

        return sucesso;
    }
    
    //Procura o emprestimo associado a matricula
    public List<Emprestimo> procuraMatriculaEmprestimo(Long matricula) throws SQLException{
        String procura = "SELECT e.id, e.data_emprestimo, e.data_prevista, e.multa FROM emprestimo e "
                + "JOIN aluno a ON e.aluno_id = a.id WHERE a.matricula=?";
        List<Emprestimo> emprestimos = new ArrayList<>();
        try{
            PreparedStatement stmt = connection.prepareStatement(procura);
            stmt.setLong(1, matricula);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Emprestimo emprestimo = new Emprestimo();
                emprestimo.setId(rs.getInt("id"));
                emprestimo.setDataEmprestimo(rs.getDate("data_emprestimo"));
                emprestimo.setDataPrevista(rs.getDate("data_prevista"));
                emprestimo.setMulta(rs.getDouble("multa"));
                
                emprestimos.add(emprestimo);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return emprestimos;
    }
    
    //verfica se os emprestimos do aluno
    public boolean verificarEmprestimo(Long matricula) {
        String sql = "SELECT * FROM emprestimo e JOIN aluno a ON e.aluno_id = a.id WHERE a.matricula =?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setLong(1, matricula);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }      
    
    //Obtem a matricula pelo ID 
    public Long obterMatriculaPorId(Integer idEmprestimo) throws SQLException {
        String sql = "SELECT a.matricula " +
                     "FROM emprestimo e " +
                     "JOIN aluno a ON e.aluno_id = a.id " +
                     "WHERE e.id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idEmprestimo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("matricula"); 
                }
            }
        }
        return null; 
    }

    // verifica o emprestimo usando o retorno Date
    public Date verificarEmprestimoDate(Integer idEmprestimo) throws SQLException {
        String sql = "SELECT e.data_prevista " +
                     "FROM emprestimo e " +
                     "WHERE e.id = ?"; 

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idEmprestimo); 
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDate("data_prevista");
                }
            }
        }
        return null; 
    }

    //obtendo o emprestimo associado ao ID 
    public Integer obterLivroPorEmprestimoId(Integer emprestimoId) throws SQLException {
        String sql = "SELECT livro_id FROM itememprestimo WHERE emprestimo_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, emprestimoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("livro_id");
                } else {
                    throw new SQLException("Livro não encontrado para o empréstimo fornecido.");
                }
            }
        }
    }

    //Faz mapeamento, nesse caso não esta usando a classe QueryUtil
    public HashMap<String, Integer> getEmprestimosMapPorMatricula(Long matricula) throws SQLException {
        String buscaQuery = "SELECT e.id, " +
                            "       (e.id || ' - ' || t.nome || ' - ' || p.nome) AS emprestimo_info " +
                            "FROM emprestimo e " +
                            "JOIN aluno a ON e.aluno_id = a.id " +
                            "JOIN pessoa p ON a.pessoa_id = p.id " +
                            "JOIN itememprestimo i ON e.id = i.emprestimo_id " +
                            "JOIN livro l ON i.livro_id = l.id " +
                            "JOIN titulo t ON l.titulo_id = t.id " +
                            "WHERE a.matricula = ?";

        HashMap<String, Integer> emprestimosMap = new HashMap<>();

        try (PreparedStatement stmt = connection.prepareStatement(buscaQuery)) {
            stmt.setLong(1, matricula);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    emprestimosMap.put(rs.getString("emprestimo_info"), rs.getInt("id"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar empréstimos para a matrícula " + matricula, e);
        }

        return emprestimosMap;
    }

}