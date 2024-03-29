package exception;

@SuppressWarnings("serial")
public class DBConnectionException extends Exception{
    public DBConnectionException() {
    }

    public DBConnectionException(String message) {
        super(message);
    }
}
