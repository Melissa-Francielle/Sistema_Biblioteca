package app;

import dao.AlunoDao;
import java.io.IOException;
import java.sql.SQLException;
import modelo.Aluno;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import static util.AlertMessage.exibirAlerta;

public class EditarAlunoController {

    private final AlunoDao alunoDao;

   //botões do FXML
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

    public EditarAlunoController() {
            this.alunoDao = new AlunoDao();
    }

    @FXML
    private void modificaAluno(){
        try{
            
            //Parte que valida o ID, verificando se esta preenchido ou não
            if (campoCodigo.getText().isEmpty()) {
                exibirAlerta(Alert.AlertType.WARNING, "Atenção", "Preencha o código do aluno.");
                return;
            }
            //pega os parametros do aluno para modificar
            int id = Integer.parseInt(campoCodigo.getText());

            String nomeAluno = campoNome.getText();
            Long matricula = Long.parseLong(campoMatricula.getText());
            String cpf = campoCpf.getText();
            String endereco = campoEndereco.getText();
            Aluno aluno = new Aluno(matricula, cpf, nomeAluno, endereco);
            aluno.setCodigo(id);

            alunoDao.atualizar(aluno); //realiza a modificação do aluno
            exibirAlerta(AlertType.INFORMATION, "Sucesso", "Aluno cadastrado com sucesso!");
        }catch (NumberFormatException e) {
        exibirAlerta(Alert.AlertType.ERROR, "Erro", "Código inválido. Insira um número válido.");
        } catch (Exception e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível modificar o aluno: " + e.getMessage());
        }
    }
    
    //método responsável por pegar o aluno para modificar 
    @FXML
    private void botaoPesquisa(){
       Aluno Seleciona;
       try {
            Integer id = Integer.parseInt(campoCodigo.getText());
            
            Seleciona = alunoDao.buscaId(id);
            campoCodigo.setText(String.valueOf(Seleciona.getId()));
            campoNome.setText(Seleciona.getNome());
            campoMatricula.setText(String.valueOf(Seleciona.getMatricula()));
            campoCpf.setText(Seleciona.getCpf());
            campoEndereco.setText(Seleciona.getEndereco());
            

        } catch (SQLException | NumberFormatException ex) {
            exibirAlerta(AlertType.ERROR, "ERROR", "Erro. Tente novamente");
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



    