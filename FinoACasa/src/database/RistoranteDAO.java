package database;

import entity.EntityRistorante;
import exception.DAOException;
import exception.DBConnectionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class RistoranteDAO {

	/**
	 * lettura ristorante dal db
	 * @param ristorante
	 * @return
	 * @throws DAOException
	 * @throws DBConnectionException
	 */
    public static EntityRistorante readRistorante(String ristorante) throws DAOException, DBConnectionException {
        EntityRistorante eR = null;
        Connection connection = null;
        String query = null;

        try {
            connection = DBManager.getConnection();
            query = "SELECT * FROM RISTORANTI WHERE NOME=? ;";

            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, ristorante);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    eR = new EntityRistorante(resultSet.getObject(1, UUID.class), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8));
                }
            } catch (SQLException e) {
                throw new DAOException("Errore lettura Ristorante");
            } finally {
                DBManager.closeConnection();
            }
        }catch (SQLException e) {
            throw new DBConnectionException("Errore di connessione al DB");
        }
        return eR;
    }
    
    public static void createRistorante(EntityRistorante eR) throws DBConnectionException, DAOException {
    	Connection conn = null;
    	String query = null;
    	
    	try {
            conn = DBManager.getConnection();

            try {
                query = "INSERT INTO RISTORANTI VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setObject(1, eR.getId());
                statement.setString(2, eR.getNome());
                statement.setString(3, eR.getCitta());
                statement.setString(4, eR.getVia());
                statement.setString(5, eR.getNumeroCivico());
                statement.setString(6, eR.getCAP());
                statement.setString(7, eR.getTelefono());
                statement.setString(8, eR.getEmail());
                statement.executeUpdate();
                
            }catch (SQLException e){
                throw new DAOException("Errore scrittura ristorante");
                
            }finally {
                DBManager.closeConnection();
            }

        } catch (SQLException e) {
            throw new DBConnectionException("Errore connessione database");
        }
    }
    
    public static void updateRistorante(EntityRistorante eR, UUID ristorante) throws DBConnectionException, DAOException {
    	Connection conn = null;
    	String query = null;
    	
    	try {
            conn = DBManager.getConnection();

            try {
                query = "UPDATE RISTORANTI SET NOME = ?, CITTA = ?, VIA = ?, NUMEROCIVICO = ?, CAP = ?, TELEFONO = ?, EMAIL = ? WHERE ID=?;";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, eR.getNome());
                statement.setString(2, eR.getCitta());
                statement.setString(3, eR.getVia());
                statement.setString(4, eR.getNumeroCivico());
                statement.setString(5, eR.getCAP());
                statement.setString(6, eR.getTelefono());
                statement.setString(7, eR.getEmail());
                statement.setObject(8, eR.getId());
                statement.executeUpdate();
                
            }catch (SQLException e){
                throw new DAOException("Errore aggiornamento ristorante");
                
            }finally {
                DBManager.closeConnection();
            }

        } catch (SQLException e) {
            throw new DBConnectionException("Errore connessione database");
        }
    }
    
    public static void deleteRistorante(UUID eR) throws DBConnectionException, DAOException {
    	Connection conn = null;
    	String query = null;
    	
    	try {
            conn = DBManager.getConnection();

            try {
                query = "DELETE FROM RISTORANTI WHERE ID=?;";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setObject(1, eR);
                statement.executeUpdate();
                
            }catch (SQLException e){
                throw new DAOException("Errore eliminazione ristorante");
                
            }finally {
                DBManager.closeConnection();
            }

        } catch (SQLException e) {
            throw new DBConnectionException("Errore connessione database");
        }
    }
}
