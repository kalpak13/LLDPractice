package Exceptions;

import models.ParkingResponse;

public final class ExceptionMapper {
    private ExceptionMapper() {
    }

    public static ParkingResponse toResponse(Throwable throwable) {
        if (throwable instanceof ParkingException parkingException) {
            return new ParkingResponse(parkingException.getMessage(), false, null, parkingException.getCode());
        }
        if (throwable instanceof InvalidTicketNumberException invalidTicketNumberException) {
            return new ParkingResponse(invalidTicketNumberException.getMessage(), false, null, "INVALID_TICKET");
        }
        return new ParkingResponse("Internal server error", false, null, "INTERNAL_ERROR");
    }
}
