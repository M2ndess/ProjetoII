package Entity;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente", nullable = false)
    private Integer idCliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_cliente")
    private TipoCliente idTipoCliente;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "telefone", length = 20)
    private String telefone;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "nif", length = 20)
    private String nif;

    @Column(name = "username", length = 20)
    private String username;

    @Column(name = "password", length = 50)
    private String password;

    @Lob
    @Column(name = "estado_conta")
    private String estadoConta;

    @OneToMany(mappedBy = "idCliente")
    private Set<Recinto> recintos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idCliente")
    private Set<Reserva> reservas = new LinkedHashSet<>();

    public Cliente() {
        // Empty constructor required by JPA
    }

    public Cliente(Integer idCliente, String nome, String telefone, String email, String nif, String username, String estadoConta) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.nif = nif;
        this.username = username;
        this.estadoConta = estadoConta;
    }

    public Cliente(String nome, String telefone, String email, String nif, String username,String password, String estadoConta) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.nif = nif;
        this.username = username;
        this.password = password;
        this.estadoConta = estadoConta;
    }

    public Integer getId() {
        return idCliente;
    }

    public void setId(Integer id) {
        this.idCliente = idCliente;
    }

    public TipoCliente getIdTipoCliente() {
        return idTipoCliente;
    }

    public void setIdTipoCliente(TipoCliente idTipoCliente) {
        this.idTipoCliente = idTipoCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEstadoConta() {
        return estadoConta;
    }

    public void setEstadoConta(String estadoConta) {
        this.estadoConta = estadoConta;
    }

    public Set<Recinto> getRecintos() {
        return recintos;
    }

    public void setRecintos(Set<Recinto> recintos) {
        this.recintos = recintos;
    }

    public Set<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(Set<Reserva> reservas) {
        this.reservas = reservas;
    }

}