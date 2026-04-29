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

    public String getMessage() {
        return Message;
    }

    public String getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return "SuccessResponse{" +
                "message='" + Message + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }
}
