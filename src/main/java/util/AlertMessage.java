package util;

/*
    Classe para mensagens de erros que são usadas por todas as classes
*/

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertMessage {

     // Construtor privado para evitar instanciamento
    private AlertMessage() {
        throw new UnsupportedOperationException("Esta é uma classe utilitária e não pode ser instanciada.");
    }
    
    public static void exibirAlerta(AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null); 
        alert.setContentText(mensagem);
        alert.showAndWait(); 
    }
    
    public static void Alerta(AlertType tipo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle("Erro");
        alerta.setHeaderText(null);  
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
