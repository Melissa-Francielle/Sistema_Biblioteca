package app;

import dao.AreaDao;
import dao.AutorDao;
import dao.TituloDao;
import java.io.IOException;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import modelo.Titulo;
import static util.AlertMessage.exibirAlerta;


public class GerenciarTituloController {

    //Botoes FXML 
    @FXML
    private TextField txtTitulo;
    @FXML
    private TextField txtPrazo;
    @FXML
    private TextField txtAno;
    @FXML
    private TextField txtEdicao;
    @FXML
    private TextField txtIsbn;
    @FXML
    private ComboBox listarAutores;
    @FXML
    private ComboBox listarAreas;
    

    private TituloDao tituloDao = new TituloDao();
    private AutorDao autorDao = new AutorDao();
    private AreaDao areaDao = new AreaDao();
    
    /*Da mesma forma, o titulo pega autor e area, por conta do relacionamento do diagrama e do banco
        Usando o hashmap para isso, listando autores e area.
    */
    HashMap<String, Integer> autoresMap = autorDao.getAutoresMap();
    HashMap<String, Integer> areasMap = areaDao.getAreasMap();
    
    @FXML
    public void initialize() {
        ComboAutores();  
        ComboAreas();
  
    }

    @FXML
    public void cadastrarTitulo() {
        try{
            //Pega os dados para o cadastro em titulo
           String nomeTitle = txtTitulo.getText();
           String isbn = txtIsbn.getText();
           Integer prazo = Integer.parseInt(txtPrazo.getText());
           Integer ano = Integer.parseInt(txtAno.getText());
           Integer edicao = Integer.parseInt(txtEdicao.getText());  // Certifique-se de que o valor seja convertido para Integer

           
          //pega o item que for selecionado no combo da area
           Object areaNome = listarAreas.getValue();
           Integer area_id = areasMap.get(areaNome);
           System.out.println("Autor ID: " + area_id);
           
           //pega o item que for selecionado pelo combo dos autores.
           Object autorNome = listarAutores.getValue();
           Integer autor_id = autoresMap.get(autorNome);
           System.out.println("Autor ID: " + autor_id); 
           
           Titulo titulo = new Titulo(nomeTitle, ano, prazo, edicao, isbn, autor_id, area_id );
           tituloDao.inserindoLivro(titulo); //insere em titulo
           
           exibirAlerta(AlertType.INFORMATION, "Sucesso", "Operação realizada com sucesso");
           
           
        }catch(Exception e){
             e.printStackTrace();
             exibirAlerta(AlertType.ERROR, "Erro", "Erro ao cadastrar livro " + e.getMessage());
        }
    }
    /*
        metodos responsáveis por passar pelos itens e adicionando ao combo
    */
    public void ComboAutores() {
        for (String autorNome : autoresMap.keySet()) {
            listarAutores.getItems().add(autorNome);
        }
    }
    
    public void ComboAreas() {
        for (String areaNome : areasMap.keySet()) {
            listarAreas.getItems().add(areaNome);
        }
    }

    
    // Método para limpar os campos
    private void limparCampos() {
        txtTitulo.clear();
        txtAno.clear();
        txtPrazo.clear();
    }
    
    
    @FXML
    private void voltarAoMenu() throws IOException{
        App.setRoot("menuView");
    }
}
