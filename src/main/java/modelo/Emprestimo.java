package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class Emprestimo implements Serializable {

    private int id;
    private Livro livro;
    private Integer aluno_id;
    private Double multa;
    private List<ItemEmprestimo> itens = new ArrayList<>();
    private Date dataEmprestimo;
    private Date dataPrevista;
    private int quantidadeEmprestada= 0;
    private Aluno aluno;
    private String status;
    // Construtores
    public Emprestimo() {
        
    }
    public Emprestimo(Integer aluno_id, Date dataEmprestimo, Date dataPrevista, Double multa) {
        this.aluno_id = aluno_id;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevista = dataPrevista;
        this.multa = multa;
        
    }

    // Métodos get e set
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(Date dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public Date getDataPrevista() {
        return dataPrevista;
    }

    public void setDataPrevista(Date dataPrevista) {
        this.dataPrevista = dataPrevista;
    }

    public Integer getAluno() {
        return aluno_id;
    }

    public void setAluno(Integer aluno_id) {
        this.aluno_id = aluno_id;
    }
    public Double getMulta(){
        return multa;
    }
    
    public void setMulta(Double multa){
        if (multa < 0){
            throw new IllegalArgumentException("Multa não pode ser negativa");
        }
        this.multa = multa;
    }   
    
    public Livro getLivro(){
        return livro;
    }
    public void setLivro(Livro livro){
        this.livro=livro;
    }
    public String getStatus(){
        return status;
    }
    
    public void setStatus(String status){
        this.status = status;
    }
    
    public Aluno getAlunoClasse(){
        return aluno;
    }
    
    public void setAlunoClasse(Aluno aluno){
        this.aluno =aluno;
    }
    
    public boolean emprestar(List<Livro> livros){
        for(Livro livro : livros){
            itens.add(new ItemEmprestimo(livro));
            quantidadeEmprestada++;
        }
        calculaDataDevolucao();
        return true;
    }

    public Date calculaDataDevolucao(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataEmprestimo);
        
        int diasAdicionais = 7;
        if(itens.size()>2){
            diasAdicionais += (itens.size() - 2) * 2;
        }
        calendar.add(Calendar.DATE, diasAdicionais);
        dataPrevista = (Date) calendar.getTime();
        
        for(ItemEmprestimo item : itens){
            item.setDataDevolucao(dataPrevista);
        }
        return dataPrevista;
    }
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.id);
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
        final Emprestimo other = (Emprestimo) obj;
        return Objects.equals(this.id, other.id);
              
    }

    @Override
    public String toString() {
        return "Emprestimo: " + 
               "\nData do emprestimo: " + this.getDataEmprestimo() +
               "\nData prevista: " + this.getDataPrevista() +
               "\nMulta" + this.getMulta();
    }
}
