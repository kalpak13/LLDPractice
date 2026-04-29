package models;

public class SuccessResponse implements ResponseClass{

    String Message;
    String code;
    Object data;
    public SuccessResponse(String message,String code){
        this.Message = message;
        this.code = code;
    }

    public SuccessResponse(String message,String code,Object data){
        this.Message = message;
        this.code = code;
        this.data = data;
    }
}
