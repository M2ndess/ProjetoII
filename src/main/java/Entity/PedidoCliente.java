package Entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "pedido_cliente", schema = "public", catalog = "proj2")
public class PedidoCliente {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_pedido")
    private int idPedido;
    @Basic
    @Column(name = "id_cliente")
    private int idCliente;
    @Basic
    @Column(name = "id_entrega")
    private int idEntrega;
    @Basic
    @Column(name = "data_pedido")
    private Date dataPedido;
    @Basic
    @Column(name = "estado_pedido")
    private String estadoPedido;
    @Basic
    @Column(name = "custo_total")
    private double custoTotal;

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(int idEntrega) {
        this.idEntrega = idEntrega;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public double getCustoTotal() {
        return custoTotal;
    }

    public void setCustoTotal(double custoTotal) {
        this.custoTotal = custoTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PedidoCliente that = (PedidoCliente) o;
        return idPedido == that.idPedido && idCliente == that.idCliente && idEntrega == that.idEntrega && Double.compare(that.custoTotal, custoTotal) == 0 && Objects.equals(dataPedido, that.dataPedido) && Objects.equals(estadoPedido, that.estadoPedido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPedido, idCliente, idEntrega, dataPedido, estadoPedido, custoTotal);
    }
}
