   
package app;
/*
    Classe não implementada com interface somente o código. 
    No caso, essa classe pode ser desevolvida uma interface para ela, para que possa 
    ser feita a remoção do aluno caso necessario.
*/
import dao.AlunoDao;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import static util.AlertMessage.exibirAlerta;

public class RemoveAlunoController {

    private final AlunoDao alunoDao;
    
   //Botões FXML
    @FXML
    private Button botaoSearch;
    @FXML
    private TextField campoCodigo;
    @FXML
    private TextField campoNome;
    @FXML
    private TextField campoMatricula;
    @FXML
    private TextField campoCpf;
    @FXML
    private TextField campoEndereco;

    public RemoveAlunoController() {
            this.alunoDao = new AlunoDao();
    }
    
    private void excluirAluno(){
        try{
            //Pega o id do aluno que será removido para poder realizar a remoção do aluno 
            /*pode ser adaptado também para ser removido por matricula
                tendo só necessario modificar o tipo para Long e o parametro método de deletar.
            */
            Integer id = Integer.parseInt(campoCodigo.getText());
            alunoDao.deletar(id);
            
            exibirAlerta(AlertType.INFORMATION, "SUCESSO", "Operacao realizada com sucesso.");
        }catch(NumberFormatException  e){
             exibirAlerta(AlertType.ERROR, "ERROR", "Erro! Somente numero sao validos. Tente novamente");
            
        }catch(Exception e){
            exibirAlerta(AlertType.ERROR, "ERROR", "Erro ao remover esse aluno. Tente novamente");
        }       
    }
    

    private void limparCampos() {
        campoNome.clear();
        campoMatricula.clear();
        campoCpf.clear();
        campoEndereco.clear();
    }
    
    @FXML
    private void voltarAoMenu() throws IOException{
        App.setRoot("menuView");
    }
}



    