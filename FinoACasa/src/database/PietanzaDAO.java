package database;

import entity.EntityPietanza;
import exception.DAOException;
import exception.DBConnectionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PietanzaDAO {

	/**
	 * lettura pietanza dal database
	 * @param pietanza
	 * @param ristorante
	 * @return
	 * @throws DBConnectionException
	 */
    public static EntityPietanza readPietanza(String pietanza, UUID ristorante) throws DBConnectionException {
        EntityPietanza eP = null;
        Connection connection = null;
        String query = null;
        
        try {
            connection = DBManager.getConnection();
            query = "SELECT * FROM PIETANZE WHERE NOME=? AND RISTORANTE=?";

            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, pietanza);
                statement.setObject(2, ristorante);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    eP = new EntityPietanza(resultSet.getObject(1, UUID.class), resultSet.getString(2), resultSet.getString(3), resultSet.getFloat(4));
                }
            } catch (SQLException e) {
                throw new DAOException("Errore lettura pietanza");
            } finally {
                DBManager.closeConnection();
            }
        }catch (SQLException | DAOException e) {
            throw new DBConnectionException("Errore di connessione DB");
        }
        return eP;
    }
    
    public static void createPietanza(EntityPietanza eP, UUID ristorante) throws DBConnectionException, DAOException {
    	Connection conn = null;
    	String query = null;
    	
    	try {
            conn = DBManager.getConnection();

            try {
                query = "INSERT INTO PIETANZE VALUES (?, ?, ?, ?, ?);";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setObject(1, eP.getId());
                statement.setString(2, eP.getNome());
                statement.setString(3, eP.getDescrizione());
                statement.setFloat(4, eP.getPrezzo());
                statement.setObject(5, ristorante);
                
                statement.executeUpdate();
                
            }catch (SQLException e){
                throw new DAOException("Errore scrittura pietanza");
                
            }finally {
                DBManager.closeConnection();
            }

        } catch (SQLException e) {
            throw new DBConnectionException("Errore connessione database");
        }
    }
    
    public static void updatePietanza(EntityPietanza eR) throws DBConnectionException, DAOException {
    	Connection conn = null;
    	String query = null;
    	
    	try {
            conn = DBManager.getConnection();

            try {
                query = "UPDATE PIETANZE SET NOME = ?, DESCRIZIONE = ?, PREZZO = ? WHERE ID=?;";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, eR.getNome());
                statement.setString(2, eR.getDescrizione());
                statement.setFloat(3, eR.getPrezzo());
                statement.setObject(4, eR.getId());
                statement.executeUpdate();
                
            }catch (SQLException e){
                throw new DAOException("Errore aggiornamento pietanza");
                
            }finally {
                DBManager.closeConnection();
            }

        } catch (SQLException e) {
            throw new DBConnectionException("Errore connessione database");
        }
    }
    
    public static void deletePietanza(UUID eP) throws DBConnectionException, DAOException {
    	Connection conn = null;
    	String query = null;
    	
    	try {
            conn = DBManager.getConnection();

            try {
                query = "DELETE FROM PIETANZE WHERE ID=?;";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setObject(1, eP);
                statement.executeUpdate();
                
            }catch (SQLException e){
                throw new DAOException("Errore cancellazione pietanza");
                
            }finally {
                DBManager.closeConnection();
            }

        } catch (SQLException e) {
            throw new DBConnectionException("Errore connessione database");
        }
    }
}
