package Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class MateriaPrima {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_materia_prima")
    private int idMateriaPrima;
    @Basic
    @Column(name = "id_fornecedor")
    private int idFornecedor;
    @Basic
    @Column(name = "tipo_materia_prima")
    private String tipoMateriaPrima;
    @Basic
    @Column(name = "custo_unitario")
    private double custoUnitario;
    @Basic
    @Column(name = "descricao")
    private String descricao;
    @Basic
    @Column(name = "quantidade")
    private Integer quantidade;
    @Basic
    @Column(name = "id_lote")
    private Integer idLote;

    public MateriaPrima() {
        // Empty constructor required by JPA
    }

    public MateriaPrima(Integer idFornecedor, String tipoMateriaPrima, Double custoUnitario, String descricao, Integer quantidade, Integer idLote) {
        this.idFornecedor = idFornecedor;
        this.tipoMateriaPrima = tipoMateriaPrima;
        this.custoUnitario = custoUnitario;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.idLote = idLote;
    }

    public int getIdMateriaPrima() {
        return idMateriaPrima;
    }

    public void setIdMateriaPrima(int idMateriaPrima) {
        this.idMateriaPrima = idMateriaPrima;
    }

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public String getTipoMateriaPrima() {
        return tipoMateriaPrima;
    }

    public void setTipoMateriaPrima(String tipoMateriaPrima) {
        this.tipoMateriaPrima = tipoMateriaPrima;
    }

    public double getCustoUnitario() {
        return custoUnitario;
    }

    public void setCustoUnitario(double custoUnitario) {
        this.custoUnitario = custoUnitario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQuantidade() {return quantidade;}

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getIdLote() {return idLote;}

    public void setIdLote(Integer idLote) {
        this.idLote = idLote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MateriaPrima that = (MateriaPrima) o;
        return idMateriaPrima == that.idMateriaPrima && idFornecedor == that.idFornecedor && Double.compare(that.custoUnitario, custoUnitario) == 0 && Objects.equals(tipoMateriaPrima, that.tipoMateriaPrima) && Objects.equals(descricao, that.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMateriaPrima, idFornecedor, tipoMateriaPrima, custoUnitario, descricao);
    }


}
