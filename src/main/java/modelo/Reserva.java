package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Reserva implements Serializable {

    private Integer id;
    private Aluno aluno;
    private Livro livro;
    private Date data;

    // Construtores
    public Reserva() {
    }

    public Reserva(Date data, Aluno aluno, Livro livro) {
        this.data = data;
        this.aluno = aluno;
        this.livro = livro;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    // Métodos sobreescritos
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
        hash = 17 * hash + Objects.hashCode(this.aluno);
        hash = 17 * hash + Objects.hashCode(this.livro);
        hash = 17 * hash + Objects.hashCode(this.data);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Reserva other = (Reserva) obj;
        return Objects.equals(this.id, other.id) &&
               Objects.equals(this.aluno, other.aluno) &&
               Objects.equals(this.livro, other.livro) &&
               Objects.equals(this.data, other.data);
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", aluno=" + (aluno != null ? aluno.getNome() : "Sem aluno") +
                ", livro=" + (livro != null ? livro : "Sem livro") +
                ", data=" + data +
                '}';
    }
}
