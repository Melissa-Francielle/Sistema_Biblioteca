    package modelo;

    import java.io.Serializable;
    import java.util.Objects;

    public class Titulo implements Serializable {

        private Integer id;
        private String nome;
        private Integer prazo;
        private Integer autor_id;
        private Integer area_id;
        private Integer ano;
        private String isbn;
        private Integer edicao;
        private Autor autor;

        // Construtores
        public Titulo() {
        }

        public Titulo(String nome, Integer ano, Integer prazo, Integer edicao, String isbn, Integer autor_id, Integer area_id) {
            this.nome = nome;
            this.prazo = prazo;
            this.edicao = edicao;
            this.isbn = isbn;
            this.ano = ano;      
            this.autor_id = autor_id;
            this.area_id = area_id;
            
        }
        
        public Titulo(String nome, Integer ano){}
        
        public Titulo(String isbn, String nome, Integer prazo){}
        // Getters e Setters
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getEdicao(){
            return edicao;
        }
        
        public void setEdicao(Integer edicao){
            this.edicao = edicao;
        }
        
        public String getIsbn(){
            return isbn;
        }
        
        public void setIsbn(String isbn){
            this.isbn = isbn;
        }
        
        public Integer getAno(){
            return ano;
        }

        public void setAno(Integer ano){
            this.ano = ano;
        }
        
        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public Integer getPrazo() {
            return prazo;
        }

        public void setPrazo(Integer prazo) {
            this.prazo = prazo;
        }
        
        public Integer getAutorId(){
            return autor_id;
        }
        public void setAutorId(Integer autor_id){
            this.autor_id = autor_id;
        }
        
        public Integer getAreaId(){
            return area_id;
        }
        public void setAreaId(Integer area_id){
            this.area_id = area_id;
        }
        // Métodos sobreescritos
        @Override
        public int hashCode() {
            int hash = 3;
            hash = 29 * hash + Objects.hashCode(this.id);
            hash = 29 * hash + Objects.hashCode(this.nome);
            hash = 29 * hash + Objects.hashCode(this.prazo);
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
            final Titulo other = (Titulo) obj;
            return Objects.equals(this.id, other.id) &&
                   Objects.equals(this.nome, other.nome) &&
                   Objects.equals(this.prazo, other.prazo);
        }

        @Override
        public String toString() {
            return "Titulo" + getNome();
        }

    }
