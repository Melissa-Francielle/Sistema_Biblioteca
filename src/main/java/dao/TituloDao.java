
package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Titulo;
import util.DatabaseConnection;
import util.QueryUtil;


public class TituloDao {
    private final Connection connection;
    public TituloDao() {
         try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao conectar com o banco de dados", e);
        }
    }

    public void inserindoLivro(Titulo titulo){
       String inserindoQuery = "INSERT INTO titulo (nome, ano, autor_id, area_id, prazo, edicao, isbn) VALUES  (?,?,?,?,?,?,?)";
       try(PreparedStatement stmt = connection.prepareStatement(inserindoQuery)){
          stmt.setString(1, titulo.getNome());
          stmt.setInt(2, titulo.getAno());
           stmt.setInt(3, titulo.getAutorId());
            stmt.setInt(4, titulo.getAreaId());
             stmt.setInt(5, titulo.getPrazo());
          stmt.setInt(6, titulo.getEdicao());
          stmt.setString(7, titulo.getIsbn());
          

          
          
          stmt.executeUpdate();
       }catch (SQLException ex) {
            Logger.getLogger(TituloDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    //Mapeamento do titulo
    public HashMap<String, Integer> getTitulosMap() {
        String query = "SELECT * FROM titulo";
        return QueryUtil.getResultMap(connection, query, "nome", "id");
    }
    
}
