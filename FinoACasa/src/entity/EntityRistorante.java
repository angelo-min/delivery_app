package entity;

import java.util.Objects;
import java.util.UUID;

public class EntityRistorante {
    private UUID id;
    private String nome;
    private String citta;
    private String via;
    private String numeroCivico;
    private String CAP;
    private String telefono;
    private String email;
    
    public EntityRistorante(UUID id, String nome, String citta, String via, String numeroCivico, String CAP, String telefono, String email) {
        setId(id);
        setNome(nome);
        setCitta(citta);
        setVia(via);
        setNumeroCivico(numeroCivico);
        setCAP(CAP);
        setTelefono(telefono);
        setEmail(email);
    }

    public EntityRistorante(UUID id, String nome, String citta, String via, String numeroCivico, String CAP, String email) {
        setId(id);
        setNome(nome);
        setCitta(citta);
        setVia(via);
        setNumeroCivico(numeroCivico);
        setCAP(CAP);
        this.telefono = "";
        setEmail(email);
    }

    public EntityRistorante(String nome, String citta, String via, String numeroCivico, String CAP, String email) {
        this.id = UUID.randomUUID();
        setNome(nome);
        setCitta(citta);
        setVia(via);
        setNumeroCivico(numeroCivico);
        setCAP(CAP);
        this.telefono = "";
        setEmail(email);
    }

    public EntityRistorante(String nome, String citta, String via, String numeroCivico, String CAP, String telefono, String email) {
        this.id = UUID.randomUUID();
        setNome(nome);
        setCitta(citta);
        setVia(via);
        setNumeroCivico(numeroCivico);
        setCAP(CAP);
        setTelefono(telefono);
        setEmail(email);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getNumeroCivico() {
        return numeroCivico;
    }

    public void setNumeroCivico(String numeroCivico) {
        this.numeroCivico = numeroCivico;
    }

    public String getCAP() {
        return CAP;
    }

    public void setCAP(String CAP) {
        this.CAP = CAP;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityRistorante that = (EntityRistorante) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
