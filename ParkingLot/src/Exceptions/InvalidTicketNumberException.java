package Exceptions;

public class  InvalidTicketNumberException extends RuntimeException  {
    private final String code;

    public InvalidTicketNumberException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
