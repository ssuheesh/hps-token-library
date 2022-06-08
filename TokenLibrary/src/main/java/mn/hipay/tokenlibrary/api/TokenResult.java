package mn.hipay.tokenlibrary.api;

public class TokenResult {
    public int code;
    public String description;
    public TokenResult(int code, String description){
        this.code = code;
        this.description = description;
    }
}
