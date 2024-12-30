package dao;

import modelo.Area;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.DatabaseConnection;
import util.QueryUtil;

public class AreaDao {
    private final Connection connection;
    public AreaDao() { 
         try {
            this.connection = DatabaseConnection.getConnection(); 
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao conectar com o banco de dados", e);
        }
    }
    
    //mapea as areas dentro do banco de dados.
    public HashMap<String, Integer> getAreasMap(){
        String buscaQuery = "SELECT * FROM area ";
        return QueryUtil.getResultMap(connection, buscaQuery, "nome", "id");

    }
}