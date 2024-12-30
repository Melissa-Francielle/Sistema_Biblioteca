package util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DaoJDBC<T extends Persistivel> {

    private final Class<T> classe;

    public DaoJDBC(Class<T> classe) {
        this.classe = classe;
    }

    public T alterar(T objeto) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "UPDATE tabela SET coluna1 = ?, coluna2 = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                // Configure os parâmetros da consulta
                stmt.setString(1, objeto.getMetodo());
                stmt.setString(2, objeto.getMetodo());
                stmt.setInt(3, objeto.getCodigo());

                // Execute a atualização
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return objeto;
    }

    public T buscarPorCodigo(Object id) {
        T objeto = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM tabela WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setObject(1, id);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        // Converta os dados do ResultSet em um objeto da classe T
                        objeto = classe.getDeclaredConstructor().newInstance();
                        objeto.setCodigo(rs.getInt("id"));
                        objeto.setMetodo(rs.getString("coluna1"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objeto;
    }

    public void excluir(T objeto) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM tabela WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, objeto.getCodigo());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void inserir(T objeto) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO tabela (coluna1, coluna2) VALUES (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, objeto.getMetodo());
                stmt.setString(2, objeto.getMetodo());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<T> listarTodos() {
        List<T> lista = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM tabela";
            try (PreparedStatement stmt = connection.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    T objeto = classe.getDeclaredConstructor().newInstance();
                    objeto.setCodigo(rs.getInt("id"));
                    objeto.setMetodo(rs.getString("coluna1"));
                    lista.add(objeto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
