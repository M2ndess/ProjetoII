package Entity;

import java.util.Date;

public class MateriaPrimaInfo {
    private int idMateriaPrima;
    private int idLote;
    private int idFornecedor;
    private String tipoMateriaPrima;
    private int quantidade;
    private double custo;
    private Date dataCriacao;
    private Date dataValidade;
    private String estadoLote;

    public MateriaPrimaInfo(int idMateriaPrima, int idLote, int idFornecedor, String tipoMateriaPrima,
                            int quantidade, double custo, Date dataCriacao, Date dataValidade, String estadoLote) {
        this.idMateriaPrima = idMateriaPrima;
        this.idLote = idLote;
        this.idFornecedor = idFornecedor;
        this.tipoMateriaPrima = tipoMateriaPrima;
        this.quantidade = quantidade;
        this.custo = custo;
        this.dataCriacao = dataCriacao;
        this.dataValidade = dataValidade;
        this.estadoLote = estadoLote;
    }

    public int getIdMateriaPrima() {
        return idMateriaPrima;
    }

    public void setIdMateriaPrima(int idMateriaPrima) {
        this.idMateriaPrima = idMateriaPrima;
    }

    public int getIdLote() {
        return idLote;
    }

    public void setIdLote(int idLote) {
        this.idLote = idLote;
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

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
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
}
