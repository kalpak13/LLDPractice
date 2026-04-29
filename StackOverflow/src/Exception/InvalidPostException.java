package Exception;

public class InvalidPostException extends RuntimeException {
    private final String code;
    public  InvalidPostException(String code,String message) {

        super(message);
        this.code = code;
    }
    public String getCode(){
        return code;
    }
}
