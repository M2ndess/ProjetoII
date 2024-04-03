package Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Funcionario {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_funcionario")
    private int idFuncionario;
    @Basic
    @Column(name = "nome")
    private String nome;
    @Basic
    @Column(name = "contacto")
    private String contacto;
    @Basic
    @Column(name = "morada")
    private String morada;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "nif")
    private String nif;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "estado_conta")
    private String estadoConta;

    public Funcionario() {
        // Empty constructor required by JPA
    }

    public Funcionario(Integer idFuncionario, String nome, String contacto, String morada, String email, String nif, String username, String estadoConta) {
        this.idFuncionario = idFuncionario;
        this.nome = nome;
        this.contacto = contacto;
        this.morada = morada;
        this.email = email;
        this.nif = nif;
        this.username = username;
        this.estadoConta = estadoConta;
    }

    public Funcionario(String nome, String contacto, String morada, String email, String nif, String username,String password, String estadoConta) {
        this.nome = nome;
        this.contacto = contacto;
        this.morada = morada;
        this.email = email;
        this.nif = nif;
        this.username = username;
        this.password = password;
        this.estadoConta = estadoConta;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Funcionario that = (Funcionario) o;
        return idFuncionario == that.idFuncionario && Objects.equals(nome, that.nome) && Objects.equals(contacto, that.contacto) && Objects.equals(morada, that.morada) && Objects.equals(email, that.email) && Objects.equals(nif, that.nif) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(estadoConta, that.estadoConta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFuncionario, nome, contacto, morada, email, nif, username, password, estadoConta);
    }
}
