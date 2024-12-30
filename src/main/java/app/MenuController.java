package app;
/*
    Esta classe é responsável pelo direcionamento das interfaces, comos e fosse o presenter do 
    padrão MVP. Faz a relação com o controller do MVC para o view. 

OBS: os metodos comentados são os que não foram implementados com interface, havendo somente código
*/

import java.io.IOException;
import javafx.fxml.FXML;
import app.App;


public class MenuController {
    
    @FXML
    private void cadastrarAluno() throws IOException {
        App.setRoot("gerenciarAlunoView");
    }
    @FXML
    private void editarAluno() throws IOException{
        App.setRoot("editarAlunoView");
    }
    
    /*@FXML
    private void removeAluno() throws IOException{
        App.setRoot("removeAlunoView");
    }*/
    
     /*@FXML
    private void cadastraBibliotecario() throws IOException{
        App.setRoot("gerenciaBibliotecarioView");
    }*/
    
    @FXML
    private void cadastrarLivro() throws IOException{
        App.setRoot("gerenciarLivroView");
    }
    @FXML
    private void gerenciarAutor() throws IOException{
        App.setRoot("gerenciarAutorView");
    }
    @FXML
    private void gerenciarTitulo() throws IOException{
        App.setRoot("gerenciaTituloView");
    }
  
    @FXML
    private void realizarEmprestimo() throws IOException{
        App.setRoot("realizaEmprestimo");
    }
    
    @FXML
    private void realizarDevolucao() throws IOException{
        App.setRoot("realizaDevolucao");
    }
    
   /*  @FXML
    private void realizarReserva() throws IOException{
        App.setRoot("reservaView");
    }*/
}
    