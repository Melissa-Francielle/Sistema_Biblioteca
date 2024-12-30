package app;

import dao.LivroDao;
import dao.TituloDao;
import java.io.IOException;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import static util.AlertMessage.exibirAlerta;

public class GerenciarLivroController {

    //Botões FXML 
    
    @FXML
    private TextField txtQuantidade;
    @FXML
    private CheckBox cbDisponivel;
    @FXML
    private CheckBox cbExemplarBiblioteca;
    @FXML
    private ComboBox listaTitulos;
    
    @FXML
    private Button btnCadastrar;

    private LivroDao livroDao = new LivroDao();
    private TituloDao tituloDao = new TituloDao();
    
    //Usa o HashMap para listar os titulos para o cadastro do livro
    HashMap<String, Integer> tituloMap = tituloDao.getTitulosMap();
    @FXML
    public void initialize() {
        ComboTitulos();
    }
    
    
    @FXML
    public void cadastrarLivro() {
        try{
           //Pega os dados do livro para poder fazer a inserção do livro no banco
           Boolean disponivel = cbDisponivel.isSelected();
           Boolean exemplar = cbExemplarBiblioteca.isSelected();
           
           Integer quantidade = Integer.parseInt(txtQuantidade.getText());
           
           Object tituloNome = listaTitulos.getValue();
           Integer titulo_id = tituloMap.get(tituloNome);
           
           //Insere o livro usando metodo de inserção
           livroDao.inserindoLivro(disponivel, exemplar, titulo_id, quantidade);
                   
           exibirAlerta(AlertType.INFORMATION, "Sucesso", "Livro cadastrado com sucesso!");
           
        }catch(Exception e){
             e.printStackTrace();
             exibirAlerta(AlertType.ERROR, "Erro", "Erro ao cadastrar livro " + e.getMessage());
        }
    }
    //Pega os titulos usandoo hashmap, passa cada titulo para adicionar aos itens
    public void ComboTitulos(){
        for(String titulosNome : tituloMap.keySet()){
            listaTitulos.getItems().add(titulosNome);
        }
    }
    
        
    private void limparCampos() {
        txtQuantidade.clear();
    }
    
    @FXML
    private void voltarAoMenu() throws IOException{
        App.setRoot("menuView");
    }
}
