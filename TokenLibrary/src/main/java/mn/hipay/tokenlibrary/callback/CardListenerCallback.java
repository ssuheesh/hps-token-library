package mn.hipay.tokenlibrary.callback;

import com.google.gson.JsonObject;

public interface CardListenerCallback
{
    public void onSuccess(String successMessage, JsonObject data);
    public void onFailure(Throwable throwableError);
}