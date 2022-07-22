package mn.hipay.tokenlibrary.exception;

public class HpsException extends Exception
{
    public static final String CARD_REMOVE_EXCEPTION = "Error on card remove";
    public static final String ACCESS_TOKEN_EXCEPTION = "Error on creating accessToken";
    public static final String CARD_ADD_INIT_EXCEPTION = "Error on card add init";
    public static final String CARD_LIST_EXCEPTION = "Error on getting card list";
    private String code;

    public HpsException(String code, String message) {
        super(message);
        this.setCode(code);
    }

    public HpsException(String code, String message, Throwable cause) {
        super(message, cause);
        this.setCode(code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
