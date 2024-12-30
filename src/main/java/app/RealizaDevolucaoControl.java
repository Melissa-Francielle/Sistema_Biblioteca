package app;
/*
    A classe Realiza Devolucao Control é a classe de devolução versão 2, essa classe é a que 
garantiu melhor funcionamento perante o código. Mas a "realizaDevolucao" também é uma classe
de devolução, porém apresenta alguns bugs que podem ser resolvidos para o seu funcionamento 

*/
import dao.EmprestimoDao;
import dao.LivroDao;
import dao.DebitoDao;
import dao.DevolucaoDao;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import static util.AlertMessage.exibirAlerta;

public class RealizaDevolucaoControl {
    //Botões FXML
    @FXML
    private TextField txtMatricula;

    @FXML
    private ComboBox<String> cbEmprestimo;

    // Atributos DAO
    EmprestimoDao emprestimoDao = new EmprestimoDao();
    DebitoDao debitoDao = new DebitoDao();
    DevolucaoDao devolucaoDao = new DevolucaoDao();
    LivroDao livroDao = new LivroDao();

    // Mapeamento dos emprestimos para ficar mais facil a devolução 
    private HashMap<String, Integer> emprestimosMap;

    @FXML
    void carregarEmprestimos() throws SQLException {
        
        //verifica a matricula que precisa ser somente numero
        Long matricula;
        try {
            matricula = Long.parseLong(txtMatricula.getText());
        } catch (NumberFormatException e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro", "Matrícula inválida! Digite um número válido para a matrícula.");
            return;
        }

        //Vai carregar os empréstimos do DAO
        emprestimosMap = emprestimoDao.getEmprestimosMapPorMatricula(matricula);

        if (emprestimosMap.isEmpty()) { // verifica também o combo
            exibirAlerta(Alert.AlertType.WARNING, "Aviso", "Nenhum empréstimo encontrado! Não há empréstimos ativos para a matrícula fornecida.");
            return;
        }


        //Insere o ComboBox com os emprestimos como foi feito em titulo e livro 
        cbEmprestimo.getItems().clear();
        cbEmprestimo.getItems().addAll(emprestimosMap.keySet());
    }
    
    //método "PRINCIPAL", pois é aqui que realiza a devolução 
    @FXML
    void devolverLivro() {
        //Pega o item selecionado no combo box 
        String selectedItem = cbEmprestimo.getSelectionModel().getSelectedItem();

        if (selectedItem == null || !emprestimosMap.containsKey(selectedItem)) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro", "Selecione um empréstimo válido.");
            return;
        }

        Integer idEmprestimo = emprestimosMap.get(selectedItem);
        try {
            // Obtém os dados do empréstimo
            Long matricula = emprestimoDao.obterMatriculaPorId(idEmprestimo);
            Date dataPrevista = emprestimoDao.verificarEmprestimoDate(idEmprestimo);
            Date dataAtual = new Date(System.currentTimeMillis());

            // Faz o calculo de debito se for necessario 
            if (dataAtual.after(dataPrevista)) {
                Long diasAtraso = (dataAtual.getTime() - dataPrevista.getTime()) / (1000 * 60 * 60 * 24);
                Double valorDebito = diasAtraso * 1.50;
                debitoDao.criarDebito(matricula, valorDebito);
            }


            /*Para disponibilizar o livro, é necessário saber qual o livro associado ao emprestimo
                para isso foi utilizado o método para associar.
            */
            Integer livroId = emprestimoDao.obterLivroPorEmprestimoId(idEmprestimo); 

            //Faz a realização da devolução 
            devolucaoDao.devolucao(idEmprestimo);
            
            livroDao.marcarDisponivel(livroId); //marca o livro associado ao emprestimo como disponivel
            
            //e marca como devolvido o emprestimo
            boolean sucesso = devolucaoDao.removerEmprestimoAssociadoAluno(idEmprestimo);
            if (sucesso) {
                exibirAlerta(Alert.AlertType.CONFIRMATION, "Devolução Bem-Sucedida", "O livro foi devolvido com sucesso.");
            } else {
                exibirAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao remover o empréstimo associado ao aluno.");
            }
        } catch (Exception e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao realizar a devolução: " + e.getMessage());
        }
    }
    

    @FXML
    private void voltarAoMenu() throws IOException {
        App.setRoot("menuView");
    }
}
