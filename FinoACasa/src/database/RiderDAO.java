package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import entity.EntityRider;
import exception.DAOException;
import exception.DBConnectionException;

public class RiderDAO {
	
    public static void createRider(EntityRider eR) throws DBConnectionException, DAOException {
    	Connection conn = null;
    	String query = null;
    	
    	try {
            conn = DBManager.getConnection();

            try {
                query = "INSERT INTO RIDERS VALUES (?, ?, ?, ?);";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setObject(1, eR.getId());
                statement.setString(2, eR.getNome());
                statement.setString(3, eR.getCognome());
                statement.setString(4, eR.getTelefono());
                statement.executeUpdate();
                
            }catch (SQLException e){
                throw new DAOException("Errore scrittura rider");
                
            }finally {
                DBManager.closeConnection();
            }

        } catch (SQLException e) {
            throw new DBConnectionException("Errore connessione database");
        }
    }
   
    public static EntityRider readRider(String telefonoRider) throws DAOException, DBConnectionException {
        EntityRider eR = null;
        Connection connection = null;
        String query = null;

        try {
            connection = DBManager.getConnection();
            query = "SELECT * FROM RIDERS WHERE TELEFONO=? ;";

            try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, telefonoRider);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    eR = new EntityRider(resultSet.getObject(1, UUID.class), resultSet.getString(2), resultSet.getString(3), telefonoRider);
                }
            } catch (SQLException e) {
                throw new DAOException("Errore lettura rider");
            } finally {
                DBManager.closeConnection();
            }
        }catch (SQLException e) {
            throw new DBConnectionException("Errore di connessione al DB");
        }
        return eR;
    }
 
    public static void updateRiders(EntityRider eR) throws DBConnectionException, DAOException {
    	Connection conn = null;
    	String query = null;
    	
    	try {
            conn = DBManager.getConnection();

            try {
                query = "UPDATE RIDERS SET NOME = ?, COGNOME = ?, TELEFONO = ? WHERE ID=?;";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, eR.getNome());
                statement.setString(2, eR.getCognome());
                statement.setString(3, eR.getTelefono());
                statement.setObject(8, eR.getId());
                statement.executeUpdate();
                
            }catch (SQLException e){
                throw new DAOException("Errore aggiornamento rider");
                
            }finally {
                DBManager.closeConnection();
            }

        } catch (SQLException e) {
            throw new DBConnectionException("Errore connessione database");
        }
    }
    
    public static void deleteRider(UUID eR) throws DBConnectionException, DAOException {
    	Connection conn = null;
    	String query = null;
    	
    	try {
            conn = DBManager.getConnection();

            try {
                query = "DELETE FROM RIDERS WHERE ID=?;";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setObject(1, eR);
                statement.executeUpdate();
                
            }catch (SQLException e){
                throw new DAOException("Errore eliminazione rider");
                
            }finally {
                DBManager.closeConnection();
            }

        } catch (SQLException e) {
            throw new DBConnectionException("Errore connessione database");
        }
    }
}
