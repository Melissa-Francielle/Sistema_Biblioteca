package app;
/*
    Essa classe não foi implementada com interface, mas ela possui apenas o código.
    Para funcionamento futuro só há necessidade de criar a interface e passar a "scene" para o código na classe APP
*/
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import util.ValidadorFactory;


public class LoginController {
    
    //Botões FXML
    @FXML
    private PasswordField campoSenha;

    @FXML
    private TextField campoLogin;
    
    //Usa validador para verificar se há campos vazios 
    ValidadorFactory.Validador validador = ValidadorFactory.criarValidadorTexto();

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    //Usa a conexão com o banco de dados 
    public Connection connectDB(){
        try {
            Class.forName("org.postgresql.Driver");
            Connection connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sistemaBiblioteca", "postgres", "1234");
            return connect;
        } catch(Exception e){
            e.printStackTrace();
            
        }
        return null;
    }
    
    public void login(){
        //A partir da conexão com o banco pega os login e senhas correspondentes 
        Alert alert = new Alert(AlertType.ERROR);
        connect = connectDB();
         if (connect == null) {
            alert.setContentText("Falha na conexão com o banco de dados");
            alert.show();           
            return;
        }
            //validar
            validador.validar(campoLogin);
            validador.validar(campoSenha);
            String selectData = "SELECT * FROM bibliotecario WHERE login = ? AND senha = ?";
            
            try{
                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, campoLogin.getText());
                prepare.setString(2, campoSenha.getText());

                result = prepare.executeQuery(); //realiza a operação
                if(result.next()){
                    App.setRoot("menuView");
                }
                else{
                   alert.setContentText("INCORRECT");
                   alert.show();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            
        }
        
} 

