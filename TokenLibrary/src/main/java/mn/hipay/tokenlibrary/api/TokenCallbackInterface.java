package mn.hipay.tokenlibrary.api;

import com.google.gson.JsonObject;

import mn.hipay.tokenlibrary.exception.HpsException;

public interface TokenCallbackInterface {
    void response(JsonObject result) throws HpsException;
}