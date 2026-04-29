package models;

public class ErrorResponse implements ResponseClass{

    String Message;
    String code;
    public ErrorResponse(String message,String code){
        this.Message = message;
        this.code = code;
    }
}
