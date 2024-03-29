package control;

import database.ClienteDAO;
import database.PietanzaDAO;
import database.RistoranteDAO;
import entity.*;
import exception.DAOException;
import exception.DBConnectionException;
import exception.OperationException;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class GestioneFoodDelivery {
    private static GestioneFoodDelivery gF = null;
    private ArrayList<EntityOrdine> ordiniInAttesa;

    protected GestioneFoodDelivery(){
        ordiniInAttesa = new ArrayList<EntityOrdine>();
    }

    public static GestioneFoodDelivery getInstance(){
        if(gF == null){
            gF = new GestioneFoodDelivery();
        }
        return gF;
    }

    /**
     * Connessione ai DAO per ottenere i dati necessari al processing dell'ordine e creazione dell'ordine
     * @param username
     * @param ristorante
     * @param pietanze
     * @return
     * @throws OperationException
     */
    public ArrayList<String> processaOrdine(String username, String ristorante, ArrayList<String> pietanze) throws OperationException {
        EntityRistorante eR = null;
        EntityPietanza eP = null;
        EntityCliente eC = null;
        EntityOrdine eO = null;
        float prezzo = 0;
        ArrayList<String> returnList = new ArrayList<String>();
        ArrayList<UUID> pietanzeInOrdine = new ArrayList<UUID>();
        UUID idOrdine = null;
        UUID idRistorante = null;
        
        returnList.add(0, "0");
        returnList.add(1, "null");

        try {
            eR = RistoranteDAO.readRistorante(ristorante);
            if(eR == null){
                throw new OperationException("Ristorante non disponibile per la consegna");
            }
            idRistorante = eR.getId();
            
            for (String p: pietanze) {
            	//passo a readPietanza anche l'id del ristorante ottenuto ora eP per trovare la giusta pietanza nel db
                eP = PietanzaDAO.readPietanza(p, idRistorante);
                if(eP == null){
                    throw new OperationException("La pietanza " + p + " non e' disponibile");
                }
                prezzo = prezzo + eP.getPrezzo();
                pietanzeInOrdine.add(eP.getId());
                
            }
            
            eC = ClienteDAO.readCliente(username);
            if(eC == null) {
            	throw new OperationException("utente non registrato");
            }
            //salvo prezzo dell'ordine
            returnList.set(0, String.valueOf(prezzo));
            returnList.set(1, eC.getCartaDiCredito());
            idOrdine = generaCodice();
            eO = new EntityOrdine(idOrdine, new Date(System.currentTimeMillis()), State.PROCESSATO, prezzo, pietanzeInOrdine, idRistorante, null, eC.getUsername());
            ordiniInAttesa.add(eO);
        } catch (DBConnectionException e) {
            throw new OperationException("Riscontrato problema interno applicazione");
        } catch (DAOException e) {
            throw new OperationException("Qualcosa è andato storto...");
        }
        return returnList;
    }

    /**
     * Salvataggio dell'ordine sul db e notifica ristorante
     * @param username
     * @throws OperationException
     */
    public void concludiOrdine(String username) throws OperationException {
    	//ricerco l'ordine in attesa richiesto dall'utente 'username'
        EntityOrdine eO = ordiniInAttesa.stream()
                .filter(order -> order.getCliente().equals(username)).findAny().orElse(null);
        if(eO != null){
            try {
                eO.saveOrdine();
                notificaRistorante();
            } catch (DAOException e) {
                throw new OperationException("Riscontrato problema interno all'applicazione");
            } catch (DBConnectionException e) {
                throw new OperationException("Errore nella conclusione dell'ordine");
            }
        }
        ordiniInAttesa.remove(eO);
    }
    
    /**
     * generazione codice univoco
     * @return
     */
    private UUID generaCodice() {
    	return UUID.randomUUID();
    }
    
    /**
     * notifica ristorante dell'avvenuto completamento dell'ordine da parte del cliente
     * @throws OperationException
     */
    private void notificaRistorante() throws OperationException {
    	System.out.println("Sto notificando il ristorante...");
        try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			throw new OperationException("Riscontrato problema interno all'applicazione");
		}
        System.out.println("Il ristorante sta preparando l'ordine");
        System.out.println();
    }
}
