package entity;

import java.util.Objects;
import java.util.UUID;

public class EntityRider {
    private UUID id;
    private String nome;
    private String cognome;
    private String telefono;

    public EntityRider(UUID id, String nome, String cognome, String telefono) {
        setId(id);
        setNome(nome);
        setCognome(cognome);
        setTelefono(telefono);
    }

    public EntityRider(String nome, String cognome, String telefono) {
        this.id = UUID.randomUUID();
        setNome(nome);
        setCognome(cognome);
        setTelefono(telefono);
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

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityRider that = (EntityRider) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
