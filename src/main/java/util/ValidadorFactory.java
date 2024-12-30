package util;

import javafx.scene.control.TextField;

public class ValidadorFactory {

    private ValidadorFactory() {
        throw new UnsupportedOperationException("Esta é uma classe utilitária e não pode ser instanciada.");
    }

    public static Validador criarValidadorTexto() {
        return campo -> {
            if (campo.getText() == null || campo.getText().trim().isEmpty()) {
                throw new IllegalArgumentException("O campo de texto deve ser preenchido.");
            }
        };
    }

    public interface Validador {
        void validar(TextField campo);
    }
}
