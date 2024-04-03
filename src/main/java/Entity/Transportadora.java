package Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Transportadora {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_transportadora")
    private int idTransportadora;
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
    @Column(name = "website")
    private String website;

    public Transportadora() {
        // Empty constructor required by JPA
    }

    public Transportadora(String nome, String contacto, String morada, String email, String website) {
        this.nome = nome;
        this.contacto = contacto;
        this.morada = morada;
        this.email = email;
        this.website = website;
    }

    public Transportadora(Integer idTransportadora, String nome, String contacto, String morada, String email, String website) {
        this.idTransportadora = idTransportadora;
        this.nome = nome;
        this.contacto = contacto;
        this.morada = morada;
        this.email = email;
        this.website = website;
    }

    public int getIdTransportadora() {
        return idTransportadora;
    }

    public void setIdTransportadora(int idTransportadora) {
        this.idTransportadora = idTransportadora;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transportadora that = (Transportadora) o;
        return idTransportadora == that.idTransportadora && Objects.equals(nome, that.nome) && Objects.equals(contacto, that.contacto) && Objects.equals(morada, that.morada) && Objects.equals(email, that.email) && Objects.equals(website, that.website);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTransportadora, nome, contacto, morada, email, website);
    }
}
