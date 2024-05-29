package Entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pagamento")
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pagamento", nullable = false)
    private Integer id;

    @Column(name = "metodo_pagamento", nullable = false, length = 100)
    private String metodoPagamento;

    @Column(name = "valor_total", nullable = false, length = 100)
    private BigDecimal valorTotal;

    public Pagamento() {
        // Empty constructor required by JPA
    }

    // Construtor com todos os parâmetros
    public Pagamento(Integer id, String metodoPagamento, BigDecimal valorTotal) {
        this.id = id;
        this.metodoPagamento = metodoPagamento;
        this.valorTotal = valorTotal;
    }

    // Construtor com todos os parâmetros
    public Pagamento(String metodoPagamento, BigDecimal valorTotal) {
        this.metodoPagamento = metodoPagamento;
        this.valorTotal = valorTotal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

}