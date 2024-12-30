package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.ItemEmprestimo;
import modelo.Livro;
import modelo.Titulo;
import util.DatabaseConnection;

public class ItemEmprestimoDao {

    private final Connection connection;
    public ItemEmprestimoDao() {
         try {
            this.connection = DatabaseConnection.getConnection(); // Usando a classe DatabaseConnection para obter a conexão
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao conectar com o banco de dados", e);
        }
    }

    //Usa para procurar aos itens de emprestimos 
    public List<ItemEmprestimo> procuraEmprestimo(Integer emprestimoId) throws SQLException {
        String sql = "SELECT i.id AS item_id, i.livro_id, i.emprestimo_id, i.dataprevista, i.datadevolucao, "
                   + "l.id AS livro_id, l.titulo_id, t.id AS titulo_id, t.nome AS titulo_nome "
                   + "FROM itememprestimo i "
                   + "LEFT JOIN livro l ON i.livro_id = l.id "
                   + "LEFT JOIN titulo t ON l.titulo_id = t.id "
                   + "WHERE i.emprestimo_id = ?";

        List<ItemEmprestimo> itens = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, emprestimoId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ItemEmprestimo item = new ItemEmprestimo();
                    item.setId(rs.getInt("item_id"));
                    item.setEmprestimoId(rs.getInt("emprestimo_id"));
                    item.setDataPrevista(rs.getDate("dataprevista"));
                    item.setDataDevolucao(rs.getDate("datadevolucao"));

                    // Verificar se o livro está presente
                    Integer livroId = rs.getInt("livro_id");
                    if (livroId != 0) {
                        Livro livro = new Livro(livroId);
                        livro.setTituloId(rs.getInt("titulo_id"));

                        Titulo titulo = new Titulo();
                        titulo.setId(rs.getInt("titulo_id"));
                        titulo.setNome(rs.getString("titulo_nome"));

                        livro.setTitulo(titulo);
                        item.setLivroId(livro);
                    } else {
                        System.err.println("Erro: Livro não encontrado para o item " + item.getId());
                    }

                    itens.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return itens;
    }

    //remove de itememprestimo pelo fato de estar associado a emprestimo e item
    public void removerItemEmprestimo(int livroId) throws SQLException {
        String sql = "DELETE FROM itememprestimo WHERE livro_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, livroId);
            stmt.executeUpdate();
        }
    }


}
