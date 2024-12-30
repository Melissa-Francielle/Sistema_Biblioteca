    package modelo;

    import java.io.Serializable;
    import java.util.Objects;

    public class Autor implements Serializable {

        private Integer codigo;
        private String nomeAutor;
        private String sobrenome;
        private String titulacao;

        // Construtores
        public Autor() {
        }

        public Autor(String nomeAutor, String sobrenome, String titulacao) {
            this.nomeAutor = nomeAutor;
            this.sobrenome = sobrenome;
            this.titulacao = titulacao;
        }
        
        public Autor(String nomeAutor, String sobrenome){}

        // Getters e Setters
        public Integer getCodigo() {
            return codigo;
        }

        public void setCodigo(Integer codigo) {
            this.codigo = codigo;
        }

        public String getNomeAutor() {
            return nomeAutor;
        }

        public void setNomeAutor(String nomeAutor) {
            this.nomeAutor = nomeAutor;
        }

        public String getSobrenome() {
            return sobrenome;
        }

        public void setSobrenome(String sobrenome) {
            this.sobrenome = sobrenome;
        }

        public String getTitulacao() {
            return titulacao;
        }

        public void setTitulacao(String titulacao) {
            this.titulacao = titulacao;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Autor other = (Autor) obj;
            return Objects.equals(this.codigo, other.codigo) &&
                   Objects.equals(this.nomeAutor, other.nomeAutor) &&
                   Objects.equals(this.sobrenome, other.sobrenome);
        }

        @Override
        public int hashCode() {
            int hash = 8;
            hash = 56 * hash + Objects.hashCode(this.codigo);
            hash = 56 * hash + Objects.hashCode(this.nomeAutor);
            hash = 56 * hash + Objects.hashCode(this.sobrenome);
            return hash;
        }

        @Override
        public String toString() {
            return "Autor: " + this.getNomeAutor() +
                   " " + this.getSobrenome() +
                   "\nTitulacao: " + this.getTitulacao();
        }
    }
