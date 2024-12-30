/*
    Essa classe não foi implementada totalmente ela não possui interface, apenas código.
    Mas ela responsável por adicionar o bibliotecario no sistema, o bibliotecario é o responsável
pela realização de logins no sistema
*/
package app;


import dao.BibliotecarioDao;
import java.io.IOException;
import modelo.Bibliotecario;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import static util.AlertMessage.exibirAlerta;
import util.ValidadorFactory;

public class GerenciarBibliotecarioController {
    private final BibliotecarioDao bibliotecaDao;
    
    //Usa factory como validador novamente 
    ValidadorFactory.Validador validador = ValidadorFactory.criarValidadorTexto();
    
    //botões FXML
    @FXML
    private TextField campoLogin;
    
    @FXML
    private TextField campoSenha;
    
    @FXML
    private TextField cpNome;
    
    @FXML
    private TextField cpEndereco;
    
    public GerenciarBibliotecarioController() {
        this.bibliotecaDao = new BibliotecarioDao();
    }
    
    @FXML
    private void inserir(Bibliotecario biliotecario){
        
        try{
            //pega os dados do bibliotecario para inserir no banco passando login e senha 
            //esse login e senha pode ser usado na interface de acesso ao sistema 
            String login = campoLogin.getText();
            String pass = campoSenha.getText();
            String nomeBibliotecario = cpNome.getText();
            String enderecoBibliotecario = cpEndereco.getText();
            
            //usa o factory para validação 
            validador.validar(campoLogin);
            validador.validar(campoSenha);
            
            
            Bibliotecario bibliotecario = new Bibliotecario (nomeBibliotecario, enderecoBibliotecario, login, pass);
            bibliotecaDao.inserir(bibliotecario); //Insere o bibliotecario no sistema
            
            exibirAlerta(AlertType.INFORMATION, "Sucesso", "Cadastro realizado");
        }catch(Exception e){
            e.printStackTrace();
            exibirAlerta(AlertType.ERROR, "ERROR", "Infelizmente algo aconteceu! Tente novamente");
        }
    } 
    
    private void limparCampos(){
        campoLogin.clear();
        campoSenha.clear();
        cpNome.clear();
        cpEndereco.clear();
    }
    @FXML
    private void voltarAoMenu() throws IOException{
        App.setRoot("menuView");
    }
}
