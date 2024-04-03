package Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Pedido_Item", schema = "public", catalog = "proj2")
public class PedidoItem {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_item")
    private int idItem;
    @Basic
    @Column(name = "id_pedido")
    private int idPedido;
    @Basic
    @Column(name = "quantidade")
    private int quantidade;
    @Basic
    @Column(name = "custo_unitário")
    private double custoUnitário;
    @Basic
    @Column(name = "id_lote")
    private int idLote;

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getCustoUnitário() {
        return custoUnitário;
    }

    public void setCustoUnitário(double custoUnitário) {
        this.custoUnitário = custoUnitário;
    }

    public int getIdLote() {
        return idLote;
    }

    public void setIdLote(Integer idLote) {
        this.idLote = idLote;
    }

    public void setIdLote(int idLote) { this.idLote = idLote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PedidoItem that = (PedidoItem) o;
        return idItem == that.idItem && idPedido == that.idPedido && quantidade == that.quantidade && Double.compare(that.custoUnitário, custoUnitário) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idItem, idPedido, quantidade, custoUnitário);
    }
}
