package Exception;

import models.ErrorResponse;

public final class ExceptionMapper {
    private ExceptionMapper() {
    }

    public static ErrorResponse toResponse(Throwable throwable) {
        if (throwable instanceof InvalidUserException exception) {
            return new ErrorResponse(exception.getMessage(), exception.getCode());
        }
        if (throwable instanceof InvalidPostException exception) {
            return new ErrorResponse(exception.getMessage(), exception.getCode());
        }
        return new ErrorResponse("Internal server error", "INTERNAL_ERROR");
    }
}
