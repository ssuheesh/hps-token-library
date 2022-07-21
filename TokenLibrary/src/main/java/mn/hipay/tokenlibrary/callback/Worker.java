package mn.hipay.tokenlibrary.callback;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;

import mn.hipay.tokenlibrary.TokenMainActivity;
import mn.hipay.tokenlibrary.api.TokenService;
import mn.hipay.tokenlibrary.exception.HpsException;
import mn.hipay.tokenlibrary.model.CardData;
import mn.hipay.tokenlibrary.service.TokenHelper;

public class Worker {

    CardRemoveListenerCallback  callBack;
    TokenHelper tokenHelper;
    Context context;
    TokenService tokenService;

    public Worker(Context current) {
        //initializing the callback object from the constructor
        this.callBack = null;
        this.context = current;
        this.tokenHelper = new TokenHelper(this.context);
        this.tokenService = new TokenService();
    }

    public void setCardRemoveListener(CardData cardData, CardRemoveListenerCallback  callBack) {
        this.callBack = callBack;
        JsonObject obj = new JsonObject();
        obj.addProperty("redirect_uri","https://test.hipay.mn/cardverify/result");
        this.tokenHelper.accessTokenCreation(obj, result -> {
            String accessToken = "";
            Log.i("TOKEN ACCESSTOKEN", "result: " + result);
            if(result.has("code") && result.get("code").getAsInt() == 1){
                accessToken = result.get("access_token").getAsString();
            } else {
                accessToken = "";
                Log.e("TOKEN ACCESSTOKEN ERROR", "result: " + result);
                callBack.onFailure(new HpsException(HpsException.ACCESS_TOKEN_EXCEPTION, result.toString()));
//                throw new HpsException(HpsException.ACCESS_TOKEN_EXCEPTION,"Failed to create access_token: " + result);
            }
            Log.i("TOKEN ACCESSTOKEN", accessToken);
            if(accessToken.length() != 0) {
                this.tokenService.cardRemove("Bearer "+ accessToken, cardData.cardId, result2 -> {
                    Log.i("TOKEN CARDREMOVE", "result: " + result2);
                    if(result2.has("code") && result2.get("code").getAsInt() == 1){
                        Log.i("TOKEN CARDREMOVE SUCCES", "result: " + result2);
                        callBack.onSuccess(result2.toString());
                    } else {
                        Log.e("TOKEN CARDREMOVE ERROR", "result: " + result2);
                        callBack.onFailure(new HpsException(HpsException.CARD_REMOVE_EXCEPTION, result2.toString()));
//                        throw new HpsException(HpsException.CARD_REMOVE_EXCEPTION,"Failed to remove a card: " + result2);
                    }
                });
            }
        });

        String successMessage = "";
        Throwable throwableError = new Exception("Some error");



        if (this.callBack!= null) {
            callBack.onSuccess(successMessage);
        }
        else {
            callBack.onFailure(throwableError);
        }
    }
}
