package app;

import dao.AlunoDao;
import java.io.IOException;
import modelo.Aluno;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import static util.AlertMessage.exibirAlerta;
import util.ValidadorFactory;

public class GerenciarAlunoController {

    private final AlunoDao alunoDao;
    //usa um factory 
    ValidadorFactory.Validador validador = ValidadorFactory.criarValidadorTexto(); 
    
    //pega os botões FXML
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

    public GerenciarAlunoController() {
            this.alunoDao = new AlunoDao();
    }

    @FXML
    private void realizarCadastro() {
        try {
            
            //Dados para o cadastro de aluno
            String nome = campoNome.getText();
            String endereco = campoEndereco.getText();
            String cpf = campoCpf.getText();
            Long matricula = Long.valueOf(campoMatricula.getText());
            
            //usa o factory para validar os campos 
            validador.validar(campoNome);
            validador.validar(campoEndereco);
            validador.validar(campoMatricula);
            validador.validar(campoCpf);

            
            Aluno aluno = new Aluno(matricula, cpf, nome, endereco);
            alunoDao.inserir(aluno); //realiza a inserção do aluno no banco 

          
            exibirAlerta(AlertType.INFORMATION, "Sucesso", "Aluno cadastrado com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            exibirAlerta(AlertType.ERROR, "Erro", "Erro ao cadastrar aluno: " + e.getMessage());
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
