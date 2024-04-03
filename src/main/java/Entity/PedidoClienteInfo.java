package Entity;

import java.util.Date;

public class PedidoClienteInfo {
    private int idPedido;
    private int idEntrega;
    private int idTransportadora;
    private int idCliente;
    private Date dataPedido;
    private String estadoPedido;
    private double preco;
    private Date dataEntrega;

    public PedidoClienteInfo(int idPedido, int idEntrega, int idTransportadora, int idCliente,
                            Date dataPedido, String estadoPedido, double preco, Date dataEntrega) {
        this.idPedido = idPedido;
        this.idEntrega = idEntrega;
        this.idTransportadora = idTransportadora;
        this.idCliente = idCliente;
        this.dataPedido = dataPedido;
        this.estadoPedido = estadoPedido;
        this.preco = preco;
        this.dataEntrega = dataEntrega;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

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

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Date getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

}
