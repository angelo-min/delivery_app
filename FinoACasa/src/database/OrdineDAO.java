package database;

import entity.EntityOrdine;
import exception.DAOException;
import exception.DBConnectionException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class OrdineDAO {
	
	/**
	 * salvataggio ordine sul db con relativo salvataggio delle pietanze ordinate nella tabella del db PietanzeOrdine
	 * @param eO
	 * @throws DBConnectionException
	 * @throws DAOException
	 */
    public static void createOrdine(EntityOrdine eO) throws DBConnectionException, DAOException {
    	Connection conn = null;
    	String query = null;
    	//mi salvo le pietanze dell'ordine e creo un insieme di pietanze senza duplicati da inserire in PietanzeOrdine
    	//(l'hash set non può avere duplicati)
    	ArrayList<UUID> pietanze = eO.getPietanze();
    	Set<UUID> pietanzeUnique = new HashSet<UUID>(pietanze);
    	
    	try {
            conn = DBManager.getConnection();

            try {
                query = "INSERT INTO ORDINI VALUES (?, ?, ?, ?, ?, null, ?);";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setObject(1, eO.getCodice());
                statement.setDate(2, new java.sql.Date(eO.getDataAcquisto().getTime()));
                statement.setString(3, eO.getStato().name());
                statement.setFloat(4, eO.getCosto());
                statement.setObject(5, eO.getRistorante());
                statement.setObject(6, eO.getCliente());
                statement.executeUpdate();
                
                //inserisco le pietanze nella tabella pietanzeOrdini
                for (UUID pietanza: pietanzeUnique) {
                    query = "INSERT INTO PIETANZEORDINE VALUES (?,?, ?);";
                    PreparedStatement pietanzeStatement = conn.prepareStatement(query);
                    pietanzeStatement.setObject(1, eO.getCodice());
                    pietanzeStatement.setObject(2, pietanza);
                    //inserisco la quantità = frequenza di occorrenze di quella pietanza nell'insieme delle pietanze dell'ordine
                    pietanzeStatement.setInt(3, Collections.frequency(pietanze, pietanza));
                    pietanzeStatement.executeUpdate();
                }
            }catch (SQLException e){
                throw new DAOException("Errore scrittura ordine");
                
            }finally {
                DBManager.closeConnection();
            }

        } catch (SQLException e) {
            throw new DBConnectionException("Errore connessione database");
        }
    }
    
    public static EntityOrdine readOrdine(UUID codice) throws DAOException, DBConnectionException {
        EntityOrdine eO = null;
        Connection connection = null;
        String query, query2 = null;
        ArrayList<UUID> pietanze = new ArrayList<UUID>();

        try {
            connection = DBManager.getConnection();
            query = "SELECT * FROM ORDINI WHERE CODICE=? ;";
            //per creare l'ordine sono necessarie le pietanze che però si trovano in un'altra tabella del database: PietanzeOrdine
            query2 = "SELECT * FROM PIETANZEORDINE WHERE ORDINE = ?;";

            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setObject(1, codice);
                ResultSet resultSet = statement.executeQuery();
                PreparedStatement statement2 = connection.prepareStatement(query2);
                statement2.setObject(1, codice);
                ResultSet resultPietanze = statement2.executeQuery();
                //leggo una pietanza alla volta e la inserisco nella lista
                while(resultPietanze.next()) {
                	pietanze.add(resultPietanze.getObject(1, UUID.class));
                }
                if (resultSet.next()) {
                    eO = new EntityOrdine(resultSet.getObject(1, UUID.class), new java.util.Date(resultSet.getDate(2).getTime()), entity.State.valueOf(resultSet.getString(3)), resultSet.getFloat(4), pietanze, resultSet.getObject(5, UUID.class), resultSet.getObject(6, UUID.class), resultSet.getString(7));
                }
            } catch (SQLException e) {
                throw new DAOException("Errore lettura ordine");
            } finally {
                DBManager.closeConnection();
            }
        }catch (SQLException e) {
            throw new DBConnectionException("Errore di connessione al DB");
        }
        return eO;
    }
    
    public static void updateStatoOrdine(EntityOrdine eO) throws DBConnectionException, DAOException {
    	Connection conn = null;
    	String query = null;
    	
    	try {
            conn = DBManager.getConnection();

            try {
                query = "UPDATE ORDINI SET STATO = ? WHERE ID=?;";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, eO.getStato().name());
                statement.setObject(8, eO.getCodice());
                statement.executeUpdate();
                
            }catch (SQLException e){
                throw new DAOException("Errore aggiornamento ordine");
                
            }finally {
                DBManager.closeConnection();
            }

        } catch (SQLException e) {
            throw new DBConnectionException("Errore connessione database");
        }
    }
    
    public static void deleteOrdine(UUID eO) throws DBConnectionException, DAOException {
    	Connection conn = null;
    	String query = null;
    	
    	try {
            conn = DBManager.getConnection();

            try {
                query = "DELETE FROM ORDINI WHERE ID=?;";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setObject(1, eO);
                statement.executeUpdate();
                
            }catch (SQLException e){
                throw new DAOException("Errore eliminazione ordine");
                
            }finally {
                DBManager.closeConnection();
            }

        } catch (SQLException e) {
            throw new DBConnectionException("Errore connessione database");
        }
    }

    public static int readNumOrdiniProcessati(UUID idRistorante) throws DAOException, DBConnectionException {
        int numOrdini = 0;
        Connection connection = null;
        String query = null;
        
        try {
            connection = DBManager.getConnection();

            query = "SELECT COUNT(*) FROM ORDINI WHERE RISTORANTE = ?;";

            try {
                PreparedStatement statement = null;
                statement = connection.prepareStatement(query);

                statement.setObject(1, idRistorante);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    numOrdini = resultSet.getInt(1);
                }
            } catch (SQLException e) {
                throw new DAOException("Errore readNumOrdiniProcessati");
            }finally {
                DBManager.closeConnection();
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Errore connessione database");
        }
        return numOrdini;
    }
}
