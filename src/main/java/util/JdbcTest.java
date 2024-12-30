package util;

//JdbcTestPostgreSQL.java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


//Classe de teste de conexão do BD + o driver do postgres
class JdbcTest {
   public static void main (String args[]) {
      try {
         Class.forName("org.postgresql.Driver");
      }
      catch (ClassNotFoundException e) {
         System.err.println(e);
         System.exit(-1);
      }
      try {
         //Abre a conexão 
         Connection connection = DriverManager.getConnection(
                 
         //Sintaxe: "jdbc:postgresql://dbhost:port/dbname", "user", "dbpass");
         "jdbc:postgresql://127.0.0.1:5432/sistemabiblioteca", "postgres", "admin");

         
         String query = "SELECT datname FROM pg_database WHERE datistemplate = false";

         Statement statement = connection.createStatement();
         ResultSet rs = statement.executeQuery(query);

        
         while ( rs.next() )
            
            System.out.println("PostgreSQL Query result: " + rs.getString ("datname"));
         connection.close();
      }
      catch (java.sql.SQLException e) {
         System.err.println(e);
         System.exit(-1);
      }
   }
}