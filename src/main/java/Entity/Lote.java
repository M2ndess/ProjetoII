package Entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Lote {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_lote")
    private int idLote;
    @Basic
    @Column(name = "id_funcionario")
    private int idFuncionario;
    @Basic
    @Column(name = "custo")
    private double custo;
    @Basic
    @Column(name = "data_criacao")
    private Date dataCriacao;
    @Basic
    @Column(name = "data_validade")
    private Date dataValidade;
    @Basic
    @Column(name = "estado_lote")
    private String estadoLote;

    public Lote() {
        // Empty constructor required by JPA
    }

    public Lote(int idFuncionario, double custo, Date dataCriacao, Date dataValidade, String estadoLote) {
        this.idFuncionario = idFuncionario;
        this.custo = custo;
        this.dataCriacao = dataCriacao;
        this.dataValidade = dataValidade;
        this.estadoLote = estadoLote;
    }

    public int getIdLote() {
        return idLote;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public double getCusto() {
        return custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }

    public String getEstadoLote() {
        return estadoLote;
    }

    public void setEstadoLote(String estadoLote) {
        this.estadoLote = estadoLote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lote lote = (Lote) o;
        return idLote == lote.idLote && idFuncionario == lote.idFuncionario && Double.compare(lote.custo, custo) == 0 && Objects.equals(dataCriacao, lote.dataCriacao) && Objects.equals(dataValidade, lote.dataValidade) && Objects.equals(estadoLote, lote.estadoLote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLote, idFuncionario, custo, dataCriacao, dataValidade, estadoLote);
    }


}
