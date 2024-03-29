package entity;

import database.OrdineDAO;
import exception.DAOException;
import exception.DBConnectionException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class EntityOrdine {
    private UUID codice;
    private Date dataAcquisto;
    private State stato;
    private float costo;
    private ArrayList<UUID> pietanze;
    private UUID ristorante;
    private UUID rider;
    private String cliente;


    public EntityOrdine(UUID codice, Date dataAcquisto, State stato, float costo, ArrayList<UUID> pietanze, UUID ristorante, UUID rider, String cliente) {
        setCodice(codice);
        setDataAcquisto(dataAcquisto);
        setStato(stato);
        setRistorante(ristorante);
        setRider(rider);
        setCliente(cliente);
        setCosto(costo);
        this.pietanze = new ArrayList<UUID>();
        setPietanze(pietanze);
    }

    public EntityOrdine(Date dataAcquisto, State stato, float costo, ArrayList<UUID> pietanze, UUID ristorante, UUID rider, String cliente) {
        this.codice = UUID.randomUUID();
        setDataAcquisto(dataAcquisto);
        setStato(stato);
        setRistorante(ristorante);
        setRider(rider);
        setCliente(cliente);
        setCosto(costo);
        this.pietanze = new ArrayList<UUID>();
        setPietanze(pietanze);
    }

    public UUID getCodice() {
        return codice;
    }

    public void setCodice(UUID codice) {
        this.codice = codice;
    }

    public Date getDataAcquisto() {
        return dataAcquisto;
    }

    public void setDataAcquisto(Date dataAcquisto) {
        this.dataAcquisto = dataAcquisto;
    }

    public State getStato() {
        return stato;
    }

    public void setStato(State stato) {
        this.stato = stato;
    }

    public UUID getRistorante() {
        return ristorante;
    }

    public void setRistorante(UUID ristorante) {
        this.ristorante = ristorante;
    }

    public UUID getRider() {
        return rider;
    }

    public void setRider(UUID rider) {
        this.rider = rider;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }


    public ArrayList<UUID> getPietanze() {
        return pietanze;
    }

    public void setPietanze(ArrayList<UUID> pietanze) {
        this.pietanze = pietanze;
    }

    public void saveOrdine() throws DAOException, DBConnectionException {
    	OrdineDAO.createOrdine(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityOrdine that = (EntityOrdine) o;
        return Objects.equals(getCodice(), that.getCodice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodice());
    }
}
