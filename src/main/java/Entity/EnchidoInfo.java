package Entity;

import java.util.Date;

public class EnchidoInfo {
    private int idEnchido;
    private int idLote;
    private String tipoEnchido;
    private int quantidade;
    private double custo;
    private String faseProducao;
    private Date dataCriacao;
    private Date dataValidade;
    private String estadoLote;

    public EnchidoInfo(int idEnchido, int idLote, String tipoEnchido, int quantidade, double custo,String faseProducao,
                       Date dataCriacao, Date dataValidade, String estadoLote) {
        this.idEnchido = idEnchido;
        this.idLote = idLote;
        this.tipoEnchido = tipoEnchido;
        this.quantidade = quantidade;
        this.custo = custo;
        this.faseProducao = faseProducao;
        this.dataCriacao = dataCriacao;
        this.dataValidade = dataValidade;
        this.estadoLote = estadoLote;
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

    public String getFaseProducao() {
        return faseProducao;
    }

    public void setFaseProducao(String faseProducao) {
        this.faseProducao = faseProducao;
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
