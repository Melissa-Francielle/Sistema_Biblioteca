package modelo;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;
import java.util.List;

public class Devolucao implements Serializable {
   
    private Integer id;
    private Double multa;
    private List<ItemDevolucao> itemDevolucao;
    private Emprestimo emprestimo;
    private Date dataDevolucao;
    private Boolean atraso;
    private Double valorTotal;
    private Integer emprestimoId;

    // Construtores
    public Devolucao() {
    }

    public Devolucao(Date dataDevolucao, Boolean atraso, Double multa) {
        this.dataDevolucao = dataDevolucao;
        this.atraso = atraso;
        this.multa = multa;
    }


    // Getters e Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id){
        this.id = id;
    }
     public Integer getEmprestimoId() {
        return emprestimoId;
    }
    
    public void setEmprestimoId(Integer emprestimoId){
        this.emprestimoId = emprestimoId;
    }
    
    public Emprestimo getEmprestimo(){
        return emprestimo;
    }
    
    public void setEmprestimo(Emprestimo emprestimo){
        this.emprestimo = emprestimo;
    }
    
    public Date getDevolucao() {
        return dataDevolucao;
    }

    public void setDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

  
    public List<ItemDevolucao> getItemDevolucao() {
        return itemDevolucao;
    } 
    
    public void setItemDevolucao(List<ItemDevolucao> itemDevolucao) {
        this.itemDevolucao = itemDevolucao;
    }
    
    public Double getMulta(){
        return multa;
    }
    
    public void setMulta(Double multa){
        this.multa = multa;
    }
    
    public Boolean getAtraso() {
        return atraso;
    }

    public void setAtraso(Boolean atraso) {
        this.atraso = atraso;
    }

    public Double getvalorTotal() {
        return valorTotal;
    }

    public void setvalorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    public Double calcularMulta(ItemDevolucao devolucao){
        Integer quantidadeDias = devolucao.getDiasAtraso();
        multa = quantidadeDias * 5.0;
        return multa;
        
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 58 * hash + Objects.hashCode(this.id);
        hash = 58 * hash + Objects.hashCode(this.dataDevolucao);
        hash = 58 * hash + Objects.hashCode(this.atraso);
        hash = 58 * hash + Objects.hashCode(this.valorTotal);
        hash = 58 * hash + Objects.hashCode(this.multa);
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
        Devolucao that = (Devolucao) obj;
        return Objects.equals(id, that.id) &&
               Objects.equals(dataDevolucao, that.dataDevolucao) &&
               Objects.equals(atraso, that.atraso) &&
               Objects.equals(valorTotal, that.valorTotal) &&
               Objects.equals(multa, that.multa);
    }

    @Override
    public String toString() {
        return "Devolucao{" +
               "id=" + this.getId() +
               ", dataDevolucao=" + this.getDevolucao() +
               ", atraso=" + this.getAtraso() +
               ", valorTotal=" + this.getvalorTotal() +
               ", multa=" + this.getMulta() +
               '}';
    }
}
