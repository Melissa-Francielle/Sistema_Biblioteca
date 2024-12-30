
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.ItemDevolucao;
import modelo.Livro;
import util.DatabaseConnection;


public class ItemDevolucaoDao{
    
    private final Connection connection;
    public ItemDevolucaoDao() {
         try {
            this.connection = DatabaseConnection.getConnection(); 
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao conectar com o banco de dados", e);
        }
    }
    
    public boolean inserir(ItemDevolucao item){
        String sql = "INSERT INTO itemDevolucao(livro_id, datadevolucao) VALUES (?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, item.getId());
            stmt.setDate(2, new java.sql.Date(item.getDataDevolucao().getTime()));
            stmt.execute();
            return true;   
        } catch (SQLException ex){
            Logger.getLogger(ItemDevolucaoDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public ItemDevolucao salvar(ItemDevolucao itemDevolucao) throws SQLException{
        String sql = "INSERT INTO itemdevolucao (datadevolucao, valor, diasatraso, multa, devolucao_id, livro_id) VALUES (?,?,?,?,?,?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, new java.sql.Date(itemDevolucao.getDataDevolucao().getTime()));
            stmt.setDouble(2, itemDevolucao.getValor());
            stmt.setInt(3, itemDevolucao.getDiasAtraso());
            stmt.setDouble(4, itemDevolucao.getMulta());
            stmt.setInt(5, itemDevolucao.getDevolucaoId());
            stmt.setInt(6, itemDevolucao.getLivroId());
            int rowsAffected = stmt.executeUpdate();

           
            if (rowsAffected > 0) {
                
                try (var generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        itemDevolucao.setId(generatedKeys.getInt(1)); 
                    }
                }   
                return itemDevolucao;
            }
        }catch(SQLException ex){
            throw new SQLException("Falha ao salvar o item de devolução.");
    
        }
        return null;
    }
        public List<ItemDevolucao> carregarItensDevolvidos(Long matricula) throws SQLException {
            List<ItemDevolucao> itensDevolucao = new ArrayList<>();
            String sql = "SELECT i.id, i.livro_id, l.titulo, i.data_emprestimo, i.data_devolucao " +
                         "FROM item_emprestimo i " +
                         "JOIN livros l ON i.livro_id = l.id " +
                         "WHERE i.aluno_id = ? AND i.data_devolucao IS NOT NULL"; 

            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setLong(1, matricula); 

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        
                        Integer livroId = rs.getInt("livro_id");
                        String titulo = rs.getString("titulo");
                        Date dataDevolucaoSql = rs.getDate("data_devolucao"); 

                        
                        Livro livro = new Livro();
                        livro.setId(livroId);
                        livro.setTitulo(livro.getTitulo());

                       
                        ItemDevolucao item = new ItemDevolucao();
                        item.setLivro(livro);
                        if (dataDevolucaoSql != null) {
                           item.setDataDevolucao(dataDevolucaoSql);                         }
                        itensDevolucao.add(item);
                    }
                }
            }

            return itensDevolucao;
        }
}
    
