package mn.hipay.tokenlibrary.callback;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.JsonObject;

import mn.hipay.tokenlibrary.R;
import mn.hipay.tokenlibrary.TokenMainActivity;
import mn.hipay.tokenlibrary.api.TokenService;
import mn.hipay.tokenlibrary.exception.HpsException;
import mn.hipay.tokenlibrary.model.AdapterCardData;
import mn.hipay.tokenlibrary.model.CardData;
import mn.hipay.tokenlibrary.service.TokenHelper;

public class Worker {

    public static final String baseVerifyUrl = "https://test.hipay.mn/cardverify/result";
    CardListenerCallback callBack;
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

    public void setCardRemoveListener(CardData cardData, CardListenerCallback callBack) {
        this.callBack = callBack;
        JsonObject obj = new JsonObject();
        obj.addProperty("redirect_uri",baseVerifyUrl);
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
                        callBack.onSuccess(result2.toString(), result2);
                    } else {
                        Log.e("TOKEN CARDREMOVE ERROR", "result: " + result2);
                        callBack.onFailure(new HpsException(HpsException.CARD_REMOVE_EXCEPTION, result2.toString()));
//                        throw new HpsException(HpsException.CARD_REMOVE_EXCEPTION,"Failed to remove a card: " + result2);
                    }
                });
            }
        });
    }

    public void setCardAddListener(String customerId, CardListenerCallback  callBack) {
        this.callBack = callBack;
        JsonObject obj = new JsonObject();
        obj.addProperty("redirect_uri",baseVerifyUrl);

        this.tokenHelper.accessTokenCreation(obj, result -> {
            Log.i("TOKEN ACCESSTOKEN", "result: " + result);
            String accessToken = "";
            if(result.has("code") && result.get("code").getAsInt() == 1){
                accessToken = result.get("access_token").getAsString();
            } else {
                accessToken = "";
                Log.e("TOKEN ACCESSTOKEN ERROR", "result: " + result);
                callBack.onFailure(new HpsException(HpsException.ACCESS_TOKEN_EXCEPTION, result.toString()));
            }
            Log.i("TOKEN ACCESSTOKEN", accessToken);
            if(accessToken.length() != 0) {
                JsonObject obj2 = new JsonObject();
                obj2.addProperty("redirect_uri", baseVerifyUrl);
                obj2.addProperty("return_uri", "HPSSDK.processCardBack()");
                obj2.addProperty("customer_id", customerId);
                this.tokenHelper.cardInit("Bearer "+ accessToken, obj2, result2 -> {
                    String initId;
                    Log.i("TOKEN CARDINIT", "result: " + result2);
                    if(result2.has("code") && result2.get("code").getAsInt() == 1){
                        initId = result2.get("initId").getAsString();
                        callBack.onSuccess(result2.toString(), result2);
                    } else {
                        initId = "";
                        Log.e("TOKEN CARDINIT ERROR:", "result: " + result2);
                        callBack.onFailure(new HpsException(HpsException.CARD_ADD_INIT_EXCEPTION, result2.toString()));
                    }
                });
            }
        });
    }

    public void getCardListListener(String customerId, CardListenerCallback  callBack) {
        this.callBack = callBack;
        JsonObject obj = new JsonObject();
        obj.addProperty("redirect_uri",baseVerifyUrl);
        this.tokenHelper.accessTokenCreation(obj, result -> {
            String accessToken = "";
            Log.i("TOKEN ACCESSTOKEN", "result: " + result);
            if(result.has("code") && result.get("code").getAsInt() == 1){
                accessToken = result.get("access_token").getAsString();
            } else {
                accessToken = "";
                Log.e("TOKEN ACCESSTOKEN ERROR", "result: " + result);
                callBack.onFailure(new HpsException(HpsException.ACCESS_TOKEN_EXCEPTION, result.toString()));
            }
            Log.i("TOKEN ACCESSTOKEN", accessToken);
            if(accessToken.length() != 0) {
                JsonObject obj2 = new JsonObject();
                this.tokenHelper.cardList("Bearer "+ accessToken, "TEST_TOKEN_CUSTOMERID", result2 -> {
                    Log.i("TOKEN CARDLIST", "result: " + result2);
                    if(result2.has("code") && result2.get("code").getAsInt() == 1){
                        callBack.onSuccess(result2.toString(), result2);
//                        if(result2.has("cards")){
//                            for(int i=0; i< result2.getAsJsonArray("cards").size(); i++){
//
//                            }
//                        }
                    } else {
                        Log.e("TOKEN CARDLIST ERROR:", "result: " + result2);
                        callBack.onFailure(new HpsException(HpsException.CARD_LIST_EXCEPTION, result2.toString()));
                    }
                });
            }
        });
    }
}
