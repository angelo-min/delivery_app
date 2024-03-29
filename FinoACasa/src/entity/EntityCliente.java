package entity;

import java.util.Objects;

public class EntityCliente {
    private String username;
    private String nome;
    private String cognome;
    private String telefono;
    private String cartaDiCredito;


    public EntityCliente(String username, String nome, String cognome, String telefono, String cartaDiCredito) {
        setUsername(username);
        setNome(nome);
        setCognome(cognome);
        setTelefono(telefono);
        setCartaDiCredito(cartaDiCredito);
    }

    public EntityCliente(String username, String nome, String cartaDiCredito) {
        setUsername(username);
        setNome(nome);
        setCartaDiCredito(cartaDiCredito);
    }

    public String getUsername() {
        return username;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCartaDiCredito() {
        return cartaDiCredito;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCartaDiCredito(String cartaDiCredito) {
        this.cartaDiCredito = cartaDiCredito;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityCliente that = (EntityCliente) o;
        return Objects.equals(getUsername(), that.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername());
    }
}
