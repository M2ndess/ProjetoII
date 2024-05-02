package Entity;

import javax.persistence.*;

@Entity
@Table(name = "recinto")
public class Recinto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recinto", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Cliente idCliente;

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

}