package modelo;

import java.io.Serializable;
import java.util.Calendar;
import java.sql.Date;
import java.util.Objects;

public class ItemEmprestimo implements Serializable {

    private Integer id;
    private Date dataDevolucao;
    private Date dataPrevista;
    private Livro livro;
    private Integer emprestimo_id;
    private Integer livro_id;    
    private Boolean devolvido ;
    
    // Construtores
    public ItemEmprestimo() {
    }

    public ItemEmprestimo(Integer livro_id, Integer emprestimo_id, Date dataPrevista, Date dataDevolucao, Boolean devolvido) {
        this.livro_id = livro_id;
        this.emprestimo_id = emprestimo_id;
        this.dataPrevista = dataPrevista;
        this.dataDevolucao = dataDevolucao;
        this.devolvido = false;
    }
    
    public ItemEmprestimo(Livro livro){
        this.livro = livro;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public Date getDataPrevista() {
        return dataPrevista;
    }

    public void setDataPrevista(Date dataPrevista) {
        this.dataPrevista = dataPrevista;
    }

    public Livro getLivroId() {
        return livro;
    }

    public void setLivroId(Livro livro) {
        this.livro= livro;
    
    }
    
    public Integer getLivroFk(){
        return livro_id;
    }
    
    public void setLivroFk(Integer livro_id){
        this.livro_id  = livro_id;
    }
    
    public Boolean getDev(){
        return devolvido;
    }
    
    public void setDev(Boolean devolvido){
        this.devolvido = devolvido;
    }
    
    public Integer getEmprestimoId() {
        return emprestimo_id;
    }

    public void setEmprestimoId(Integer emprestimo_id) {
        this.emprestimo_id = emprestimo_id;
    }

    public Date calculaDataDevolucao(Date data){
        dataDevolucao = data;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(Calendar.DATE, livro.verPrazo());
        dataDevolucao = (Date) calendar.getTime();
        
        return dataDevolucao;
        
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ItemEmprestimo other = (ItemEmprestimo) obj;
        return Objects.equals(this.id, other.id) &&
                Objects.equals(this.livro, other.livro);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 56 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public String toString() {
        return "ItemEmpréstimo [ID=" + id + ", Data Prevista: " + dataPrevista +
               ", Data Devolução: " + dataDevolucao + "]";
    }
}
