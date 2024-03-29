package database;

import entity.EntityCliente;
import exception.DAOException;
import exception.DBConnectionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ClienteDAO {
	
	/**
	 * lettura del cliente dal database
	 * @param username
	 * @return
	 * @throws DAOException
	 * @throws DBConnectionException
	 */
    public static EntityCliente readCliente(String username) throws DAOException, DBConnectionException {
        EntityCliente eC = null;
        Connection conn = null;
        String query = null;
        
        try {
            conn = DBManager.getConnection();

            query = "SELECT * FROM CLIENTI WHERE USERNAME = ?;";

            try {
                PreparedStatement statement = null;
                statement = conn.prepareStatement(query);

                statement.setString(1,username);
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()){
                    eC = new EntityCliente(username, resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
                }
            } catch (SQLException e) {
                throw new DAOException("Errore lettura Cliente");
            }finally {
                DBManager.closeConnection();
            }
        } catch (SQLException e) {
            throw new DBConnectionException("Errore connessione database");
        }

        return eC;
    }
    
    
    public static void createCliente(EntityCliente eC) throws DBConnectionException, DAOException {
    	Connection conn = null;
    	String query = null;
    	
    	try {
            conn = DBManager.getConnection();

            try {
                query = "INSERT INTO CLIENTI VALUES (?, ?, ?, ?, ?);";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, eC.getUsername());
                statement.setString(2, eC.getNome());
                statement.setString(3, eC.getCognome());
                statement.setString(4, eC.getTelefono());
                statement.setString(5, eC.getCartaDiCredito());
                
                statement.executeUpdate();
                
            }catch (SQLException e){
                throw new DAOException("Errore scrittura cliente");
                
            }finally {
                DBManager.closeConnection();
            }

        } catch (SQLException e) {
            throw new DBConnectionException("Errore connessione database");
        }
    }
    
    public static void updateCliente(EntityCliente eC) throws DBConnectionException, DAOException {
    	Connection conn = null;
    	String query = null;
    	
    	try {
            conn = DBManager.getConnection();

            try {
                query = "UPDATE CLIENTI SET TELEFONO = ?, CARTADICREDITO = ? WHERE USERNAME=?;";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setString(1, eC.getTelefono());
                statement.setString(2, eC.getCartaDiCredito());
                statement.setObject(3, eC.getUsername());
                statement.executeUpdate();
                
            }catch (SQLException e){
                throw new DAOException("Errore aggiornamento cliente");
                
            }finally {
                DBManager.closeConnection();
            }

        } catch (SQLException e) {
            throw new DBConnectionException("Errore connessione database");
        }
    }
    
    public static void deleteCliente(UUID eC) throws DBConnectionException, DAOException {
    	Connection conn = null;
    	String query = null;
    	
    	try {
            conn = DBManager.getConnection();

            try {
                query = "DELETE FROM CLIENTI WHERE ID=?;";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setObject(1, eC);
                statement.executeUpdate();
                
            }catch (SQLException e){
                throw new DAOException("Errore cancellazione cliente");
                
            }finally {
                DBManager.closeConnection();
            }

        } catch (SQLException e) {
            throw new DBConnectionException("Errore connessione database");
        }
    }
}
