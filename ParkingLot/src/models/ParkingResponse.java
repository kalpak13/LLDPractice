package models;

public class ParkingResponse {
    private final String message;
    private final Boolean isSuccess;
    private final String ticketNumber;
    private final String errorCode;

    public ParkingResponse(String message, Boolean isSuccess, String ticketNumber) {
        this(message, isSuccess, ticketNumber, null);
    }

    public ParkingResponse(String message, Boolean isSuccess, String ticketNumber, String errorCode) {
        this.message = message;
        this.isSuccess = isSuccess;
        this.ticketNumber = ticketNumber;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
