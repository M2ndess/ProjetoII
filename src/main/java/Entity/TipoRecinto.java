package Entity;

import javax.persistence.*;

@Entity
@Table(name = "tipo_recinto")
public class TipoRecinto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_recinto", nullable = false)
    private Integer id;

    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}