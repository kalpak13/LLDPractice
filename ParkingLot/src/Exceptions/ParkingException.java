package Exceptions;

public abstract class ParkingException extends RuntimeException {
    private final String code;

    protected ParkingException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
