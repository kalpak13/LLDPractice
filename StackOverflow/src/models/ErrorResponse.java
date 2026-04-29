package models;

public class ErrorResponse implements ResponseClass{

    String Message;
    String code;
    public ErrorResponse(String message,String code){
        this.Message = message;
        this.code = code;
    }

    public String getMessage() {
        return Message;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "message='" + Message + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
