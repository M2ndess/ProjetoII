package Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Fornecedor {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_fornecedor")
    private int idFornecedor;
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

    public Fornecedor() {
        // Empty constructor required by JPA
    }

    public Fornecedor(String nome, String contacto, String morada, String email, String website) {
        this.nome = nome;
        this.contacto = contacto;
        this.morada = morada;
        this.email = email;
        this.website = website;
    }

    public Fornecedor(Integer idFornecedor, String nome, String contacto, String morada, String email, String website) {
        this.idFornecedor = idFornecedor;
        this.nome = nome;
        this.contacto = contacto;
        this.morada = morada;
        this.email = email;
        this.website = website;
    }

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
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
        Fornecedor that = (Fornecedor) o;
        return idFornecedor == that.idFornecedor && Objects.equals(nome, that.nome) && Objects.equals(contacto, that.contacto) && Objects.equals(morada, that.morada) && Objects.equals(email, that.email) && Objects.equals(website, that.website);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFornecedor, nome, contacto, morada, email, website);
    }
}
