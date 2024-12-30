
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DatabaseConnection;
import util.QueryUtil;

public class LivroDao {
   
 
    private final Connection connection;
    public LivroDao() {
         try {
            this.connection = DatabaseConnection.getConnection(); 
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao conectar com o banco de dados", e);
        }
    }
    
    //Inserção do livro no banco de dados 
    public void inserindoLivro(Boolean _disponivel, Boolean _exemplar, Integer _titulo_id, Integer _qtd){
       String inserindoQuery = "INSERT INTO livro (disponivel, exemplar, titulo_id, quantidade) VALUES  (?,?,?,?)";
       try(PreparedStatement stmt = connection.prepareStatement(inserindoQuery)){
           stmt.setBoolean(1, _disponivel);
           stmt.setBoolean(2, _exemplar);
           stmt.setInt(3, _titulo_id);
           stmt.setInt(4, _qtd);
           stmt.executeUpdate();
       }catch (SQLException ex) {
            Logger.getLogger(LivroDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    //Método responsável por disponibilizar o livro no banco para poder ser emprestado novamente
    public void marcarDisponivel(Integer id) throws SQLException {
        try {
            connection.setAutoCommit(false); 

            
            String marcaDisponivel = "UPDATE livro SET disponivel = 'true' WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(marcaDisponivel);
            stmt.setInt(1, id);
            stmt.executeUpdate();

           
            String marcarPrazo = "UPDATE titulo SET prazo = NULL WHERE id = (SELECT titulo_id FROM livro WHERE id = ?)";
            PreparedStatement stmt2 = connection.prepareStatement(marcarPrazo);
            stmt2.setInt(1, id);
            stmt2.executeUpdate();

           
            connection.commit();

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            throw e; 
        }
    }

       
    //da mesma forma é usado para deixar indisponivel nao podendo ser emprestado 
    public void marcaIndisponivel(int id) throws SQLException{
        try{
            connection.setAutoCommit(false); 
            String marcaDisponivel = "UPDATE livro SET disponivel = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(marcaDisponivel);
            stmt.setBoolean(1, false);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            String marcarprazo = "UPDATE titulo SET prazo = NULL WHERE id = (SELECT titulo_id FROM livro WHERE id = ?)";
            PreparedStatement stmt2 = connection.prepareStatement(marcarprazo);
            stmt2.setInt(1, id);
            stmt2.executeUpdate();
            
            connection.commit();
            
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); 
                }catch (SQLException ex) {
                    ex.printStackTrace();
                } 
            }
            e.printStackTrace();
        }
        
    }
    
    //pega a disponibilidade do livro para verificação se ele está disponivel ou não 
    public Boolean pegaDisponibilidade(Integer livro_id){
        Boolean disponivel = false;
        
        String sqlBool = "SELECT disponivel FROM livro WHERE id = ?";
        try{
            PreparedStatement stmt = connection.prepareStatement(sqlBool);
            stmt.setInt(1, livro_id);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                disponivel = rs.getBoolean("disponivel");
            }
            
            
            return disponivel;
        }catch (SQLException e) {
            Logger.getLogger(LivroDao.class.getName()).log(Level.SEVERE, null, e);
            return true;
        }
    }
    
    //verifica se o livro existe e a quantidade dele
    public boolean verificarLivro(Integer id) throws SQLException{
        String sql = "SELECT COUNT(*) FROM livro WHERE id =?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                int count = rs.getInt(1);
                return count > 0;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }
    
    //Usado para o mapeamento do livro 
    public HashMap<String, Integer> getLivrosMap(){
        String buscaQuery = "SELECT * FROM titulo";
        return QueryUtil.getResultMap(connection, buscaQuery, "nome", "id");
    }
 
    
}
