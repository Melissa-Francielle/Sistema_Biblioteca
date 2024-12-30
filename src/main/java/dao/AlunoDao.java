package dao;

import modelo.Aluno;
import java.sql.*;
import java.util.HashMap;
import util.DatabaseConnection;
import util.QueryUtil;

public class AlunoDao {

    private final Connection connection;
    
    //Construtor para realizara conexão com o banco de dados
    public AlunoDao() {
         try {
            this.connection = DatabaseConnection.getConnection(); 
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao conectar com o banco de dados", e);
        }; 
    }
    
    //Método usado para inserir o aluno
    public void inserir(Aluno aluno) throws SQLException {
        String sqlPessoa = "INSERT INTO pessoa (nome, endereco) VALUES (?, ?) RETURNING id";
        String sqlAluno = "INSERT INTO aluno (pessoa_id, matricula, cpf) VALUES (?, ?, ?)";

        try {
            /*
                Inicia a transação pelo fato de aluno pegar informações de pessoa deve ser 
                atualizado a tabela de pessoa tbm juntamente de aluno.
            */
            connection.setAutoCommit(false); 

           
            int pessoaId;
            try (PreparedStatement stmt = connection.prepareStatement(sqlPessoa)) {
                stmt.setString(1, aluno.getNome());
                stmt.setString(2, aluno.getEndereco());
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    pessoaId = rs.getInt(1); // Obtém o ID gerado
                } else {
                    throw new SQLException("Erro ao inserir pessoa, ID não retornado.");
                }
            }

            try (PreparedStatement stmt = connection.prepareStatement(sqlAluno)) {
                stmt.setInt(1, pessoaId); 
                stmt.setLong(2, aluno.getMatricula());
                stmt.setString(3, aluno.getCpf());
                stmt.executeUpdate();
            }

            connection.commit(); 
        } catch (SQLException e) {
            connection.rollback(); //usa caso dê algum erro
            throw new RuntimeException("Erro ao inserir aluno: " + e.getMessage(), e);
        } finally {
            connection.setAutoCommit(true); 
        }
    }
    
    //Método usado para atualizar o aluno
    public void atualizar(Aluno aluno) throws SQLException {
        String sqlPessoa = "UPDATE pessoa SET nome = ?, endereco = ? WHERE id = ?";
        String sqlAluno = "UPDATE aluno SET matricula = ?, cpf = ? WHERE pessoa_id = ?";

        try {
            connection.setAutoCommit(false);

            
            try (PreparedStatement stmt = connection.prepareStatement(sqlPessoa)) {
                stmt.setString(1, aluno.getNome());
                stmt.setString(2, aluno.getEndereco());
                stmt.setInt(3, aluno.getCodigo()); // ID da pessoa
                stmt.executeUpdate();
            }

           
            try (PreparedStatement stmt = connection.prepareStatement(sqlAluno)) {
                stmt.setLong(1, aluno.getMatricula());
                stmt.setString(2, aluno.getCpf());
                stmt.setInt(3, aluno.getCodigo()); // pessoa_id
                stmt.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException("Erro ao atualizar aluno: " + e.getMessage(), e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    //Método responsável por fazer a busca dos alunos pelo ID 
    public Aluno buscaId(Integer _id) throws SQLException{
        String buscaQuery = "SELECT aluno.id, aluno.matricula, aluno.cpf, pessoa.nome, pessoa.endereco " +
                 "FROM aluno " +
                 "JOIN pessoa ON aluno.pessoa_id = pessoa.codigo " +
                 "WHERE aluno.id = ?";
        PreparedStatement stmt;
        ResultSet rs;
            stmt = connection.prepareStatement(buscaQuery);
            rs = stmt.executeQuery();
            Aluno aluno;
            if (rs.next()){
                aluno = new Aluno();
                aluno.setCodigo(rs.getInt("id"));
                aluno.setMatricula(rs.getLong("matricula"));
                aluno.setCpf(rs.getString("cpf"));
                aluno.setNome(rs.getString("nome"));
                aluno.setEndereco("endereco");
                return aluno;
            }
            else{
                return null;
            }
    }
    
    //método responsável por deletar da tabela aluno
    public void deletar(int codigo) throws SQLException {
        String sql = "DELETE FROM aluno WHERE pessoa_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            stmt.executeUpdate();
        }

        sql = "DELETE FROM pessoa WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            stmt.executeUpdate();
        }
    }
    
    //método responsável para mapeamento dos alunos presentes no banco de dados
    public HashMap<String, Integer> getAlunoMap(){
        String query = "SELECT * FROM pessoa";
        return QueryUtil.getResultMap(connection, query, "nome", "id");
    }
    
    //método responsável pela verificação do aluno, pela matricula.
    public Boolean verificarAluno(Long id) throws SQLException{
        String sqlVerificar = "SELECT COUNT(*) FROM aluno WHERE matricula=?";
        try(PreparedStatement stmt = connection.prepareStatement(sqlVerificar)){
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                int count = rs.getInt(1);
                return count > 0;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    //Verifica aluno para devolução do livro
        public Boolean verificarAlunoDevolucao(Long matricula) throws SQLException{
        String sqlVerificar = "SELECT a.matricula, p.nome FROM aluno a JOIN pessoa p ON a.pessoa_id = p.id WHERE a.matricula = ?";

                

        try(PreparedStatement stmt = connection.prepareStatement(sqlVerificar)){
            stmt.setLong(1, matricula);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                Aluno aluno = new Aluno();
                
                aluno.setNome(rs.getString("nome"));
                aluno.setMatricula(rs.getLong("matricula"));
                
                return true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}

