package Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Enchido {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_enchido")
    private int idEnchido;
    @Basic
    @Column(name = "id_lote")
    private int idLote;
    @Basic
    @Column(name = "tipo_enchido")
    private String tipoEnchido;
    @Basic
    @Column(name = "custo_unitario")
    private double custoUnitario;
    @Basic
    @Column(name = "fase_producao")
    private String faseProducao;
    @Basic
    @Column(name = "descricao")
    private String descricao;
    @Basic
    @Column(name = "quantidade")
    private Integer quantidade;

    public Enchido() {
        // Empty constructor required by JPA
    }

    public Enchido(String tipoEnchido, Double custoUnitario, String faseProducao, String descricao, Integer quantidade, Integer idLote) {
        this.tipoEnchido = tipoEnchido;
        this.custoUnitario = custoUnitario;
        this.faseProducao = faseProducao;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.idLote = idLote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enchido enchido = (Enchido) o;
        return idEnchido == enchido.idEnchido && idLote == enchido.idLote && Double.compare(enchido.custoUnitario, custoUnitario) == 0 && Objects.equals(tipoEnchido, enchido.tipoEnchido) && Objects.equals(faseProducao, enchido.faseProducao) && Objects.equals(descricao, enchido.descricao) && Objects.equals(quantidade, enchido.quantidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEnchido, idLote, tipoEnchido, custoUnitario, faseProducao, descricao, quantidade);
    }

    public int getIdEnchido() {
        return idEnchido;
    }

    public void setIdEnchido(int idEnchido) {
        this.idEnchido = idEnchido;
    }

    public int getIdLote() {
        return idLote;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

    public String getTipoEnchido() {
        return tipoEnchido;
    }

    public void setTipoEnchido(String tipoEnchido) {
        this.tipoEnchido = tipoEnchido;
    }

    public double getCustoUnitario() {
        return custoUnitario;
    }

    public void setCustoUnitario(double custoUnitario) {
        this.custoUnitario = custoUnitario;
    }

    public String getFaseProducao() {
        return faseProducao;
    }

    public void setFaseProducao(String faseProducao) {
        this.faseProducao = faseProducao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
