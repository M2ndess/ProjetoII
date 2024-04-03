package Entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Entrega {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_entrega")
    private int idEntrega;
    @Basic
    @Column(name = "id_transportadora")
    private int idTransportadora;
    @Basic
    @Column(name = "data_entrega")
    private Date dataEntrega;

    public int getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(int idEntrega) {
        this.idEntrega = idEntrega;
    }

    public int getIdTransportadora() {
        return idTransportadora;
    }

    public void setIdTransportadora(int idTransportadora) {
        this.idTransportadora = idTransportadora;
    }

    public Date getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entrega entrega = (Entrega) o;
        return idEntrega == entrega.idEntrega && idTransportadora == entrega.idTransportadora && Objects.equals(dataEntrega, entrega.dataEntrega);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEntrega, idTransportadora, dataEntrega);
    }
}
