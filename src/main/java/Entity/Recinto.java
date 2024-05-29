package Entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "recinto")
public class Recinto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recinto", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Integer idCliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_recinto")
    private TipoRecinto idTipoRecinto;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "morada", nullable = false)
    private String morada;

    @Column(name = "horario_funcionamento", length = 100)
    private String horarioFuncionamento;

    @Lob
    @Column(name = "info_extra")
    private String infoExtra;

    @Column(name = "estado_recinto", nullable = true, length = 50)
    private String estadoRecinto;

    @Column(name = "preco_hora", nullable = true, length = 50)
    private BigDecimal precoHora;
    public Recinto() {
        // Empty constructor required by JPA
    }

    public Recinto(Integer id, Integer idCliente, String nome, String morada, String horarioFuncionamento, String infoExtra, String estadoRecinto) {
        this.id = id;
        this.idCliente = idCliente;
        this.nome = nome;
        this.morada = morada;
        this.horarioFuncionamento = horarioFuncionamento;
        this.infoExtra = infoExtra;
        this.estadoRecinto = estadoRecinto;
    }


    public Recinto(Integer idCliente, String nome, String morada, String horarioFuncionamento, String infoExtra, String estadoRecinto) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.morada = morada;
        this.horarioFuncionamento = horarioFuncionamento;
        this.infoExtra = infoExtra;
        this.estadoRecinto = estadoRecinto;
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

    public TipoRecinto getIdTipoRecinto() {
        return idTipoRecinto;
    }

    public void setIdTipoRecinto(TipoRecinto idTipoRecinto) {
        this.idTipoRecinto = idTipoRecinto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getHorarioFuncionamento() {
        return horarioFuncionamento;
    }

    public void setHorarioFuncionamento(String horarioFuncionamento) {
        this.horarioFuncionamento = horarioFuncionamento;
    }

    public String getInfoExtra() {
        return infoExtra;
    }

    public void setInfoExtra(String infoExtra) {
        this.infoExtra = infoExtra;
    }

    public String getEstadoRecinto() {
        return estadoRecinto;
    }

    public void setEstadoRecinto(String estadoRecinto) {
        this.estadoRecinto = estadoRecinto;
    }

    public BigDecimal getPrecoHora() {
        return precoHora;
    }

    public void setPrecoHora(BigDecimal precoHora) {
        this.precoHora = precoHora;
    }
}