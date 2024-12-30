package app;

import dao.AlunoDao;
import dao.LivroDao;
import dao.EmprestimoDao;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.sql.SQLException;

public class RealizaEmprestimoController {

    private AlunoDao alunoDao = new AlunoDao();
    private LivroDao livroDao = new LivroDao();
    private EmprestimoDao emprestimoDao = new EmprestimoDao();
    
    //Botões FXML
    @FXML
    private TextField txtMatricula; 
    @FXML
    private TextField txtLivros;
    
    
    @FXML
    public void emprestar() throws SQLException {
       //Obtem os dados de livro e aluno, no caso o código e a matricula
        Long matricula = Long.parseLong(txtMatricula.getText());
        String[] livroIds = txtLivros.getText().split(",");

        if(livroIds.length > 3){
            showAlert("Erro", "Você só pode emprestar até 3 livros por vez.", Alert.AlertType.ERROR);
            return;
        }
        
        // Verifica aluno e livro e a disponibilidade do livro
        if (!alunoDao.verificarAluno(matricula)) {
            showAlert("Erro", "Aluno não cadastrado.", Alert.AlertType.ERROR);
            return;
        }

        for (String id : livroIds) {
            Integer livro_id = Integer.parseInt(id.trim());
            
            if (!livroDao.verificarLivro(livro_id)) {
                showAlert("Erro", "Livro não cadastrado.", Alert.AlertType.ERROR);
                return;
            }

            
            if (!livroDao.pegaDisponibilidade(livro_id)) {
                showAlert("Erro", "Livro indisponível.", Alert.AlertType.ERROR);
                return;
            }


            /*Adiciona o emprestimo ao banco de dados na tabela de emprestimos
                associando ele ao livro e a matricula
            */
            Boolean cadastrado = emprestimoDao.inserirEmprestimo(matricula, livro_id);

            if (cadastrado) {
                showAlert("Sucesso", "Empréstimo cadastrado com sucesso!", Alert.AlertType.INFORMATION);
                livroDao.marcaIndisponivel(livro_id); // Marcar o livro como indisponível
            } else {
                showAlert("Erro", "Ocorreu um erro ao cadastrar o empréstimo.", Alert.AlertType.ERROR);
            }
        }
    }
    
    @FXML
    private void voltarAoMenu() throws IOException{
        App.setRoot("menuView");
    }
    
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
