package dao;

import modelo.Autor;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DatabaseConnection;

public class AutorDao {

    private final Connection connection;
    public AutorDao() {
         try {
            this.connection = DatabaseConnection.getConnection(); // Usando a classe DatabaseConnection para obter a conexão
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao conectar com o banco de dados", e);
        }
    }
    
    //Inserção do autor 
    public void inserindoAutor(Autor autor){
        String inserindoQuery = "INSERT INTO autor (nome, sobrenome, titulacao) VALUES  (?,?,?)";
        try(PreparedStatement stmt = connection.prepareStatement(inserindoQuery)){
            stmt.setString(1, autor.getNomeAutor());
            stmt.setString(2, autor.getSobrenome());
            stmt.setString(3, autor.getTitulacao());
            int linhasAfetadas = stmt.executeUpdate(); 
            System.out.println("Linhas afetadas: " + linhasAfetadas);
    }   catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Erro ao inserir autor no banco de dados: " + ex.getMessage());
        }
    }
    
    //método para editar autor 
    public void modificaAutor(int _id, String _nome1, String _nome2, String _titulacao){
        String editaQuery = "UPDATE autor SET nome = ?, sobrenome =?, titulacao=? WHERE id = ?";
        try(PreparedStatement stmt = connection.prepareStatement(editaQuery)){
            stmt.setString(1, _nome1);
            stmt.setString(2, _nome2);
            stmt.setString(3, _titulacao);
            stmt.setInt(4, _id);
            stmt.executeUpdate();
    }   catch (SQLException e) {
            e.printStackTrace();
        throw new RuntimeException("Erro ao modificar autor no banco de dados: " + e.getMessage());
        }
    }
    
    //Usa para listar os autores
    public ArrayList<Autor> listarAutores(){
        ArrayList<Autor> autorList = new ArrayList<>();
        String selecionaQuery = "SELECT * FROM autor";
        PreparedStatement stmt;
        ResultSet rs;
        
        try{
            stmt = connection.prepareStatement(selecionaQuery);
            rs = stmt.executeQuery();
            
            Autor autor;
            
            while(rs.next()){
                //Os parametros devem ter os mesmos do banco de dados
                autor = new Autor();
                autor.setCodigo(rs.getInt("id"));
                autor.setNomeAutor(rs.getString("nome"));
                autor.setSobrenome(rs.getString("sobrenome"));
                autor.setTitulacao(rs.getString("titulacao"));
                
                autorList.add(autor);
            }
        }catch(SQLException ex){
            Logger.getLogger(AutorDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return autorList;
    }
  
    //Usa o mapeamento para pegar os autores presentes no banco de dados 
    public HashMap<String, Integer> getAutoresMap(){
        HashMap<String, Integer> map = new HashMap<>();
        String buscaQuery = "SELECT * FROM autor ";
        PreparedStatement stmt;
        ResultSet rs;
        try{
            stmt = connection.prepareStatement(buscaQuery);
            rs = stmt.executeQuery();
            
            Autor autor;
            while(rs.next()){
                Integer autorId = rs.getInt("id");
                String nomeAutor = rs.getString("nome");
                
                map.put(nomeAutor, autorId);
            }
            
        }catch(SQLException ex){
            Logger.getLogger(AutorDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
    }
}
    