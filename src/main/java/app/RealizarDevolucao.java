package app;
/*
    Essa classe devolução é mais complexa e não garantiu os que houvesse a devolução corretamente
    porém para um futuro poderia ser solucionado seus bugs e poderia garantir uma devolução mais bem desenvolvida.     
*/
import dao.*;
import modelo.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;

import static util.AlertMessage.exibirAlerta;

public class RealizarDevolucao {
    //Botões do FXML
    @FXML
    private TableView<ItemDevolucao> tabelaItensDevolucao;

    @FXML
    private TableColumn<ItemDevolucao, String> colunaLivro;

    @FXML
    private TableColumn<ItemDevolucao, Double> colunaMulta;

    @FXML
    private TableColumn<ItemDevolucao, Integer> colunaDiasAtraso;

    @FXML
    private TextField inputMatricula;

    @FXML
    private Button btnDevolver;

    @FXML
    private Button btnPesquisar;

    private DevolucaoDao devolucaoDao = new DevolucaoDao();
    private EmprestimoDao emprestimoDao = new EmprestimoDao();
    private ItemEmprestimoDao itemEmprestimoDao = new ItemEmprestimoDao();
    private AlunoDao alunoDao = new AlunoDao();
    private DebitoDao debitoDao = new DebitoDao();
    private ItemDevolucaoDao itemDevolucaoDao = new ItemDevolucaoDao();

    private ObservableList<ItemDevolucao> listaItensDevolucao = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarTabela();
    }
    
    //Essa tabela é responsável por listar os livros e emprestimos associados a determinada matricula
    private void configurarTabela() {
        colunaLivro.setCellValueFactory(cellData -> {
            Livro livro = cellData.getValue().getLivro();
            return livro != null ? new SimpleStringProperty(livro.getTitulo().getNome()) : new SimpleStringProperty("N/A");
        });
        colunaMulta.setCellValueFactory(new PropertyValueFactory<>("multa"));
        colunaDiasAtraso.setCellValueFactory(new PropertyValueFactory<>("diasAtraso"));
        tabelaItensDevolucao.setItems(listaItensDevolucao);
    }

    //Método responsável por pesquisar os emprestimos para a tabela 
    @FXML
    public void pesquisarEmprestimos() {
        listaItensDevolucao.clear();

        try {
            Long matricula = Long.parseLong(inputMatricula.getText());

            if (!alunoDao.verificarAlunoDevolucao(matricula)) {
                exibirAlerta(Alert.AlertType.ERROR, "Erro", "Aluno não cadastrado.");
                return;
            }

            if (!emprestimoDao.verificarEmprestimo(matricula)) {
                exibirAlerta(Alert.AlertType.INFORMATION, "Informação", "Nenhum empréstimo pendente para este aluno.");
                return;
            }

            List<Emprestimo> emprestimos = emprestimoDao.procuraMatriculaEmprestimo(matricula);

            for (Emprestimo emprestimo : emprestimos) {
                List<ItemEmprestimo> itens = itemEmprestimoDao.procuraEmprestimo(emprestimo.getId());
                for (ItemEmprestimo item : itens) {
                    Livro livro = item.getLivroId();
                    if (livro == null) {
                        System.err.println("Erro: Livro não encontrado para o item " + item.getId());
                        continue;
                    }
                     System.out.println("Item ID: " + item.getId() + " - Livro ID: " + livro.getId());
                    int diasAtraso = calcularDiasAtraso(emprestimo.getDataPrevista());
                    double multa = calcularMulta(emprestimo.getMulta(), diasAtraso);

                    ItemDevolucao itemDevolucao = new ItemDevolucao();
                    itemDevolucao.setLivro(livro);
                    itemDevolucao.setMulta(multa);
                    itemDevolucao.setDiasAtraso(diasAtraso);
                    listaItensDevolucao.add(itemDevolucao);
                }
            }
        } catch (NumberFormatException e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro de Entrada", "Matrícula inválida.");
        } catch (SQLException e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro no Banco de Dados", "Ocorreu um erro ao acessar o banco de dados.");
            e.printStackTrace();
        }
    }
    //Faz o calculo do atraso para caso o aluno tenha atrasadoo emprestimo 
    private int calcularDiasAtraso(Date dataPrevista) {
        Date dataAtual = new Date();
        if (dataAtual.after(dataPrevista)) {
            return (int) ((dataAtual.getTime() - dataPrevista.getTime()) / (1000 * 60 * 60 * 24));
        }
        return 0;
    }
    //Método principal para a realização da devolução 
    @FXML
    public void realizarDevolucao() {
        try {
            //pega a matricula e os itens 
            Long matricula = Long.parseLong(inputMatricula.getText());
            ObservableList<ItemDevolucao> itensSelecionados = tabelaItensDevolucao.getSelectionModel().getSelectedItems();

            if (itensSelecionados.isEmpty()) {
                exibirAlerta(Alert.AlertType.WARNING, "Atenção", "Nenhum item selecionado para devolução.");
                return;
            }
            //obtém os livros para selecionar o que sera devolvido
            List<Integer> livroIds = obterLivrosSelecionados(itensSelecionados);

            // Verificar se a lista de livroIds não contém elementos nulos
            if (livroIds.contains(null)) {
                exibirAlerta(Alert.AlertType.WARNING, "Erro", "Um ou mais IDs de livros são inválidos.");
                return;
            }
            //a partir do devolver pela matriucla e o id do livro 
            if (devolverLivro(matricula, livroIds)) {
                for(ItemDevolucao item : itensSelecionados){
                    // Remover itens devolvidos da tabela
                    listaItensDevolucao.remove(item); //remove cada um da tabela conforme for devolvido
                }
                exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Devolução realizada com sucesso!");
            } else {
                exibirAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao realizar a devolução.");
            }
        } catch (NumberFormatException e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro de Entrada", "Matrícula inválida.");
        } catch (SQLException e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro no Banco de Dados", "Ocorreu um erro ao acessar o banco de dados.");
            e.printStackTrace();
        }
    }
    //Usa o método para selecionar os livros que forem devolvidos 
    private List<Integer> obterLivrosSelecionados(ObservableList<ItemDevolucao> itensSelecionados) {
        List<Integer> livroIds = new ArrayList<>();
        for (ItemDevolucao item : itensSelecionados) {
            if (item != null && item.getLivroId() != null && item.getLivro().getId() != null) {
                livroIds.add(item.getLivroId());
            } else {
                System.out.println("Livro ID é nulo para um dos itens.");
            }
        }
        return livroIds;
    }

    //logica de devolução do livro 
    public boolean devolverLivro(Long matricula, List<Integer> livroIds) throws SQLException {
        try {
            for (Integer livroId : livroIds) {
                // Atualiza o status do livro no banco de dados para "disponível"
                LivroDao livroDao = new LivroDao();
                livroDao.marcarDisponivel(livroId); 

                //Faz a remoção do item
                itemEmprestimoDao.removerItemEmprestimo(livroId);
            }

            // Remove os possíveis débitos relacionados à matrícula
            debitoDao.removerDebitosPorMatricula(matricula);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Double calcularMulta(double multa, int diasAtraso) {
        return diasAtraso * multa;
    }
}