package modelo;

import java.io.Serializable;
import java.util.Objects;
import dao.TituloDao;

public class Livro implements Serializable {

    private Integer titulo_id;
    private Titulo titulo;
    private Boolean disponivel;
    private Integer quantidade;
    private Boolean exemplarBiblioteca;
    private Integer id;
    private Integer livro_id;

    
    // Construtores
    public Livro() {
    }
    
    public Livro(Integer id){}
    
    public Livro(Integer titulo_id, Boolean disponivel, Integer quantidade, Boolean exemplarBiblioteca) {
        super();
        this.titulo_id = titulo_id;
        this.disponivel = disponivel;
        this.quantidade = quantidade;
        this.exemplarBiblioteca = exemplarBiblioteca;
    }

    public Livro(Integer titulo_id, Boolean disponivel, Integer quantidade, Boolean exemplarBiblioteca, Titulo titulo) {
        super();
        this.titulo_id = titulo_id;
        this.disponivel = disponivel;
        this.quantidade = quantidade;
        this.exemplarBiblioteca = exemplarBiblioteca;
        this.titulo = titulo;
    }

    public Livro(Integer id, String name ){}
    
    public Integer getTituloId(){
        return titulo_id;
    }
    
    public void setTituloId(Integer titulo_id){
        this.titulo_id = titulo_id;
    }

    public Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }
    
    public Integer getQuantidade(){
        return quantidade;

    }
    
    public void setQuantidade(Integer quantidade){
        this.quantidade = quantidade;
    }
    
    public Integer getId(){
        return id;

    }
    
    public void setId(Integer id){
        this.id = id;
    }
    
    public Boolean getExemplarBiblioteca() {
        return exemplarBiblioteca;
    }

    public void setExemplarBiblioteca(Boolean exemplarBiblioteca) {
        this.exemplarBiblioteca = exemplarBiblioteca;
    }
    
    public Titulo getTitulo(){
        return titulo;
    }
    
    public void setTitulo(Titulo titulo){
        this.titulo = titulo;
    }

    public boolean verificaLivro() {
        return exemplarBiblioteca;
    }
    public int verPrazo() {
		return titulo.getPrazo();
	}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Livro other = (Livro) obj;
        return Objects.equals(this.exemplarBiblioteca, other.exemplarBiblioteca);
    } 

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.disponivel);
        hash = 53 * hash + Objects.hashCode(this.exemplarBiblioteca);
        return hash;
    }

    @Override
    public String toString() {
        if (this.titulo != null) {
            return this.titulo.getNome();
        } else {
            return "Título não disponível";
        }
}



}
