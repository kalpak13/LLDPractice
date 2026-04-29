package Exception;

public class InvalidUserException  extends RuntimeException{

    private final String code;
    public  InvalidUserException(String code,String message) {

        super(message);
        this.code = code;
    }
    public String getCode(){
        return code;
    }
}
