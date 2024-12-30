package modelo;

import dao.PessoaDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import util.Persistivel;

public class Aluno extends Pessoa implements Serializable, Persistivel {

    private Long matricula;
    private int id;
    private String cpf;
    private List<Emprestimo> emprestimo;
    private List<Debito> debito = new ArrayList<>();
    private List<Reserva> reserva;

    public Aluno() {
        super();
    }
    
    
    public Aluno( Long matricula, String cpf, String nome, String endereco) {
        super(nome, endereco);
        this.matricula = matricula;
        this.cpf = cpf;
    }

    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        if (matricula == null || matricula <= 0) {
            throw new IllegalArgumentException("MATRICULA INVALIDA\tTENTE NOVAMENTE");
        }
        this.matricula = matricula;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            throw new IllegalArgumentException("CPF INVALIDO!\nCPF deve conterm 11 digitos");
        }
        this.cpf = cpf;
    }

    public List<Emprestimo> getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(List<Emprestimo> emprestimo) {
        this.emprestimo = emprestimo;
    }

     public List<Debito> getDebito() {
        return debito;
    }

    public void setDebito(List<Debito> debito) {
        this.debito = debito;
    }

    public List<Reserva> getReserva() {
        return reserva;
    }

    public void setReserva(List<Reserva> reserva) {
        this.reserva = reserva;
    }

    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Aluno other = (Aluno) obj;
        return Objects.equals(this.matricula, other.matricula) &&
                Objects.equals(this.cpf, other.cpf);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.matricula);
        hash = 31 * hash + Objects.hashCode(this.cpf);
        return hash;
    }

    @Override
    public String toString() {
        return "Aluno(a):" + this.getNome() + "RA: " 
                + this.getMatricula();
    }

    @Override
    public String getMetodo() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void setMetodo(String metodo) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
