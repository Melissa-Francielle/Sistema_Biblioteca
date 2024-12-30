package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Debito implements Serializable {

    private Integer id;
    private Double valor;
    private Date data;
    private Integer aluno_id;
    private Aluno aluno;
    
    public Debito() {
        // Construtor vazio necessário
    }

    public Debito(Double valor, Date data, Integer aluno_id) {
        this.valor = valor;
        this.data = data;
        this.aluno_id = aluno_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
    
    public Aluno getAlunoClasse(){
        return aluno;
    }
    
    public void setAlunoClasse(Aluno aluno){
        this.aluno = aluno;
    }

    public Integer getAluno() {
        return aluno_id;
    }

    public void setAluno(Integer aluno_id) {
        this.aluno_id = aluno_id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.aluno_id);
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
        final Debito other = (Debito) obj;
        return Objects.equals(this.id, other.id) &&
               Objects.equals(this.aluno_id, other.aluno_id);
    }

    @Override
    public String toString() {
        return "Débito: R$" + valor + "\nData: " + data;
    }
}
