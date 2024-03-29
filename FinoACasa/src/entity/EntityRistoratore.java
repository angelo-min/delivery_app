package entity;

import java.util.Objects;
import java.util.UUID;

public class EntityRistoratore {
    private UUID id;
    private String nome;
    private String cognome;

    public EntityRistoratore(String nome, String cognome) {
        this.id = UUID.randomUUID();
        setNome(nome);
        setCognome(cognome);
    }

    public EntityRistoratore(UUID id, String nome, String cognome) {
        setId(id);
        setNome(nome);
        setCognome(cognome);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityRistoratore that = (EntityRistoratore) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
