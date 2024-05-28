package Entity;

import javax.persistence.*;
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
    private Cliente idCliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_recinto")
    private Recinto idRecinto;

    @Column(name = "pagamento")
    private BigDecimal pagamento;

    @Column(name = "hora_inicio")
    private Instant horaInicio;

    @Column(name = "hora_fim")
    private Instant horaFim;

    @Column(name = "estado_reserva", nullable = true, length = 50)
    private String estadoReserva;

    public Reserva() {
        // Empty constructor required by JPA
    }

    public Reserva(Integer id, Cliente idCliente, Recinto idRecinto, BigDecimal pagamento, Instant horaInicio, Instant horaFim, String estadoReserva) {
        this.id = id;
        this.idCliente = idCliente;
        this.idRecinto = idRecinto;
        this.pagamento = pagamento;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.estadoReserva = estadoReserva;
    }


    public Reserva(Cliente idCliente, Recinto idRecinto, BigDecimal pagamento, Instant horaInicio, Instant horaFim, String estadoReserva) {
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

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public Recinto getIdRecinto() {
        return idRecinto;
    }

    public void setIdRecinto(Recinto idRecinto) {
        this.idRecinto = idRecinto;
    }

    public BigDecimal getPagamento() {
        return pagamento;
    }

    public void setPagamento(BigDecimal pagamento) {
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

/*
 TODO [JPA Buddy] create field to map the 'duracao' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "duracao", columnDefinition = "interval(0, 0)")
    private Object duracao;
*/
}