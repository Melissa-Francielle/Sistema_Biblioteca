package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QueryUtil{

    public static HashMap<String, Integer> getResultMap(Connection connection, String query, String keyColumn, String valueColumn) {
        HashMap<String, Integer> resultMap = new HashMap<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String key = rs.getString(keyColumn);
                Integer value = rs.getInt(valueColumn);
                resultMap.put(key, value);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultMap;
    }
    
}
