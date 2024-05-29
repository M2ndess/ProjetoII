package Entity;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "reserva")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Integer idCliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_recinto")
    private Integer idRecinto;

    @JoinColumn(name = "id_pagamento")
    private Pagamento pagamento;

    @Column(name = "hora_inicio")
    private Instant horaInicio;

    @Column(name = "hora_fim")
    private Instant horaFim;

    @Column(name = "estado_reserva", nullable = true, length = 50)
    private String estadoReserva;

    @Column(name = "data_reserva", nullable = false)
    private Instant dataReserva;

    public Reserva() {
        // Empty constructor required by JPA
    }

    // Construtor com todos os parâmetros
    public Reserva(Integer id, Integer idCliente, Integer idRecinto, Pagamento pagamento, Instant horaInicio, Instant horaFim, String estadoReserva) {
        this.id = id;
        this.idCliente = idCliente;
        this.idRecinto = idRecinto;
        this.pagamento = pagamento;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.estadoReserva = estadoReserva;
    }

    // Construtor sem o ID da reserva (para novos objetos)
    public Reserva(Integer idCliente, Integer idRecinto, Pagamento pagamento, Instant horaInicio, Instant horaFim, String estadoReserva) {
        this.idCliente = idCliente;
        this.idRecinto = idRecinto;
        this.pagamento = pagamento;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.estadoReserva = estadoReserva;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdRecinto() {
        return idRecinto;
    }

    public void setIdRecinto(Integer idRecinto) {
        this.idRecinto = idRecinto;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Instant getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Instant horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Instant getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(Instant horaFim) {
        this.horaFim = horaFim;
    }

    public String getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(String estadoRecinto) {
        this.estadoReserva = estadoReserva;
    }

    public Instant getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(Instant dataReserva) {
        this.dataReserva = dataReserva;
    }

/*
 TODO [JPA Buddy] create field to map the 'duracao' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "duracao", columnDefinition = "interval(0, 0)")
    private Object duracao;
*/
}