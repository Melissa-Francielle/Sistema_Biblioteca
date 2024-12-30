package modelo;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class ItemDevolucao implements Serializable {

    private Integer id;
    private Date dataDevolucao;
    private Integer diasAtrasos;
    private Integer devolucao_id;
    private Integer livro_id;
    private Double valor;
    private Double multa;
    private Livro livro;
    private Devolucao devolucao;

    // Construtores
    public ItemDevolucao() {
    }

    public ItemDevolucao(Date dataDevolucao, Integer diasAtrasos, Double valor, Double multa) {
        this.dataDevolucao = dataDevolucao;
        this.diasAtrasos = diasAtrasos;
        this.valor = valor;
        this.multa = multa;
    }
    public ItemDevolucao(Livro livro, Date dataDevolucao) {
    }
    
    public ItemDevolucao(Integer livro_id, String nome ,Date datadevolucao){}

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

    public Integer getDiasAtraso() {
        return diasAtrasos;
    }

    public void setDiasAtraso(Integer diasAtrasos) {
        this.diasAtrasos = diasAtrasos;
    }

    
    public Integer getDevolucaoId(){
        return devolucao_id;
    }
    
    public void setDevolucaoId(Integer devolucao_id){
        this.devolucao_id = devolucao_id;
    }
    
    public Integer getLivroId(){
        return livro_id;
    }
    
    public void setLivroId(Integer livro_id){
        this.livro_id = livro_id;
    }
    
    public Double getValor() {
        return valor;
    }
    

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getMulta() {
        return multa;
    }

    public void setMulta(Double multa) {
        this.multa = multa;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    
    public Devolucao getDevolucao() {
        return devolucao;
    }

    public void setDevolucao(Devolucao devolucao) {
        this.devolucao = devolucao;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 30 * hash + Objects.hashCode(this.id);
        hash = 30 * hash + Objects.hashCode(this.dataDevolucao);
        hash = 30 * hash + Objects.hashCode(this.diasAtrasos);
        hash = 30 * hash + Objects.hashCode(this.valor);
        hash = 30 * hash + Objects.hashCode(this.multa);
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
        ItemDevolucao that = (ItemDevolucao) obj;
        return Objects.equals(id, that.id) &&
                Objects.equals(dataDevolucao, that.dataDevolucao) &&
                Objects.equals(diasAtrasos, that.diasAtrasos) &&
                Objects.equals(valor, that.valor) &&
                Objects.equals(multa, that.multa);
    }

    @Override
    public String toString() {
        return "ItemDevolucao{" +
               "id=" + this.getId() +
               ", dataDevolucao=" + this.getDataDevolucao() +
               ", diasAtrasos=" + this.getDiasAtraso() +
               ", valor=" + this.getValor() +
               ", multa=" + this.getMulta() +
               '}';
    }
}
