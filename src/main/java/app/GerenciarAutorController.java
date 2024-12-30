package app;

import dao.AutorDao;
import java.io.IOException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import modelo.Autor;
import static util.AlertMessage.exibirAlerta;
import util.ValidadorFactory;

public class GerenciarAutorController {
    private final AutorDao autorDao;
    //botões do FXML
    @FXML
    private TextField campoCodigo;
    @FXML
    private TextField campoPrimeiroNome;
    @FXML
    private TextField campoSobrenome;
    @FXML
    private TextField campoTitulacao;
    @FXML
    private TableView<Autor> tabelaAutores;

    @FXML
    private TableColumn<Autor, String> colunaNome;

    @FXML
    private TableColumn<Autor, String> colunaSobrenome;

    @FXML
    private TableColumn<Autor, String> colunaTitulacao;

    ValidadorFactory.Validador validador = ValidadorFactory.criarValidadorTexto();

    public GerenciarAutorController() {
        this.autorDao = new AutorDao();
    }
    public void initialize() {

        //Configurando a tabela para cada coluna
        colunaNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomeAutor()));
        colunaSobrenome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSobrenome()));
        colunaTitulacao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulacao()));

        atualizarTabela();
    }
    
    //O método responsável por atualização da tabela a cada inserção nova do autor no banco
    private void atualizarTabela() {
        ObservableList<Autor> autores = FXCollections.observableArrayList(autorDao.listarAutores());
        tabelaAutores.setItems(autores);
    }
    //Inser o autor no banco de dados 
    @FXML
    private void adicionarAutor(){
        try{
            //Pega os dados para inserção do autor 
            String pName = campoPrimeiroNome.getText();
            String sName = campoSobrenome.getText();
            String title = campoTitulacao.getText();
            //uso do factory novamente para poder verificar os campos 
            validador.validar(campoPrimeiroNome);
            validador.validar(campoSobrenome);
            validador.validar(campoTitulacao);
           
            Autor autor = new Autor(pName, sName, title);
            autorDao.inserindoAutor(autor); //insere o autor 
            System.out.println("Autor adicionado: " + autor);
            atualizarTabela(); //atualiza a tabela 
            exibirAlerta(AlertType.INFORMATION, "Sucesso", "Autor adicionado com sucesso");
           
        }catch(Exception e){
            e.printStackTrace();
            exibirAlerta(AlertType.ERROR, "ERROR", "Erro ao adicionar autor. Tente novamente");
       
        }
    }
    //na mesma interface uso de método para modificar o autor
    @FXML
    private void modificaAutor() {
        try {
            //pega os dados para modificar
            String pName = campoPrimeiroNome.getText();
            String sName = campoSobrenome.getText();
            String titulacao = campoTitulacao.getText();

            
            int id = Integer.parseInt(campoCodigo.getText());

            autorDao.modificaAutor(id, pName, sName, titulacao);
            atualizarTabela();
            exibirAlerta(AlertType.INFORMATION, "Sucesso", "Autor atualizado com sucesso");
            
            
        } catch (Exception e) {
            e.printStackTrace();
            exibirAlerta(AlertType.ERROR, "ERROR", "Erro ao modificar autor. Tente novamente");
        }
    }


    @FXML
    private void voltarAoMenu() throws IOException{
        App.setRoot("menuView");
    }
}
