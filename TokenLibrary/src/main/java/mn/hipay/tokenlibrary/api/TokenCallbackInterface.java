package mn.hipay.tokenlibrary.api;

import com.google.gson.JsonObject;

public interface TokenCallbackInterface {
    void response(JsonObject result);
}