package modelo;

import java.io.Serializable;
import java.util.Objects;

public class Area implements Serializable {

    private Integer id;
    private String nome;
    private String descricao;

    // Construtores
    public Area() {
    }

    public Area(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Area other = (Area) obj;
        return Objects.equals(this.id, other.id) &&
               Objects.equals(this.nome, other.nome);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.id);
        hash = 31 * hash + Objects.hashCode(this.nome);
        return hash;
    }

    @Override
    public String toString() {
        return "Area:" + "\n" +
               "ID: " + this.getId() + "\n" +
               "Tipo da Area: " + this.getNome() + "\n" +
               "Descricao: " + this.getDescricao();
    }
}
