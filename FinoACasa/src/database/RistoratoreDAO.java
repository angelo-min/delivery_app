package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import entity.EntityRistoratore;
import exception.DAOException;
import exception.DBConnectionException;

public class RistoratoreDAO {
	
    public static void createRistoratore(EntityRistoratore eR, UUID ristorante) throws DBConnectionException, DAOException {
    	Connection conn = null;
    	String query = null;
    	
    	try {
            conn = DBManager.getConnection();

            try {
                query = "INSERT INTO RISTORATORI VALUES (?, ?, ?, ?);";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setObject(1, eR.getId());
                statement.setString(2, eR.getNome());
                statement.setString(3, eR.getCognome());
                statement.setObject(4, ristorante);
                statement.executeUpdate();
                
            }catch (SQLException e){
                throw new DAOException("Errore scrittura ristoratore");
                
            }finally {
                DBManager.closeConnection();
            }

        } catch (SQLException e) {
            throw new DBConnectionException("Errore connessione database");
        }
    }
    
    public static EntityRistoratore readRistoratore(String ristoratore) throws DAOException, DBConnectionException {
        EntityRistoratore eR = null;
        Connection connection = null;
        String query = null;

        try {
            connection = DBManager.getConnection();
            query = "SELECT * FROM RISTORATORI WHERE NOME=? ;";

            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, ristoratore);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    eR = new EntityRistoratore(resultSet.getObject(1, UUID.class), resultSet.getString(2), resultSet.getString(3));
                }
            } catch (SQLException e) {
                throw new DAOException("Errore lettura ristoratore");
            } finally {
                DBManager.closeConnection();
            }
        }catch (SQLException e) {
            throw new DBConnectionException("Errore di connessione al DB");
        }
        return eR;
    }
    
    public static void updateRistoratore(EntityRistoratore eR, UUID ristorante) throws DBConnectionException, DAOException {
    	Connection conn = null;
    	String query = null;
    	
    	try {
            conn = DBManager.getConnection();

            try {
                query = "UPDATE RISTORATORI SET NOME = ?, COGNOME = ?, RISTORANTE = ? WHERE ID=?;";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, eR.getNome());
                statement.setString(2, eR.getCognome());
                statement.setObject(3, ristorante);
                statement.setObject(4, eR.getId());
                statement.executeUpdate();
                
            }catch (SQLException e){
                throw new DAOException("Errore aggiornamento ristoratore");
                
            }finally {
                DBManager.closeConnection();
            }

        } catch (SQLException e) {
            throw new DBConnectionException("Errore connessione database");
        }
    }
    
    public static void deleteRistoratore(UUID eR) throws DBConnectionException, DAOException {
    	Connection conn = null;
    	String query = null;
    	
    	try {
            conn = DBManager.getConnection();

            try {
                query = "DELETE FROM RISTORATORI WHERE ID=?;";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setObject(1, eR);
                statement.executeUpdate();
                
            }catch (SQLException e){
                throw new DAOException("Errore eliminazione ristoratore");
                
            }finally {
                DBManager.closeConnection();
            }

        } catch (SQLException e) {
            throw new DBConnectionException("Errore connessione database");
        }
    }
}
