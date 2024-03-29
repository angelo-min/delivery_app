package entity;

import java.util.Objects;
import java.util.UUID;

public class EntityPietanza {
    private UUID id;
    private String nome;
    private String descrizione;
    private float prezzo;


    public EntityPietanza(UUID id, String nome, String descrizione, float prezzo) {
        setId(id);
        setNome(nome);
        setDescrizione(descrizione);
        setPrezzo(prezzo);
    }

    public EntityPietanza(UUID id, String nome, float prezzo) {
        setId(id);
        setNome(nome);
        this.descrizione = "";
        setPrezzo(prezzo);
    }

    public EntityPietanza(String nome, String descrizione, float prezzo) {
        this.id = UUID.randomUUID();
        setNome(nome);
        setDescrizione(descrizione);
        setPrezzo(prezzo);
    }

    public EntityPietanza(String nome, float prezzo) {
        this.id = UUID.randomUUID();
        setNome(nome);
        this.descrizione = "";
        setPrezzo(prezzo);
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

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityPietanza that = (EntityPietanza) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
