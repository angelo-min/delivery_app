package exception;

@SuppressWarnings("serial")
public class OperationException extends Exception{
    public OperationException() {
    }

    public OperationException(String message) {
        super(message);
    }
}
