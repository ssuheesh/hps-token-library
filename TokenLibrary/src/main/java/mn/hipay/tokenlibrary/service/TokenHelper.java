package mn.hipay.tokenlibrary.service;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.JsonObject;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import mn.hipay.tokenlibrary.api.TokenCallbackInterface;
import mn.hipay.tokenlibrary.api.TokenService;
import mn.hipay.tokenlibrary.model.CardData;

public class TokenHelper {
    public static SharedPreferences preferences;
    private static TokenService tokenService = new TokenService();
    private static String hps_entityid="mplus.mn";
    private static String hps_token="VnZCSkdIZWk4Q21XeWx3bw";
    private static String hps_auth_code="BBE586D4EAE65BF7E0532A64A8C097BA";
    private static String hps_grant_type = "authorization_code";

    public static void accessTokenCreation(JsonObject obj, TokenCallbackInterface callback) {
        obj.addProperty("client_id", hps_entityid);
        obj.addProperty("client_secret", hps_token);
        obj.addProperty("code", hps_auth_code);
        obj.addProperty("grant_type", hps_grant_type);
        tokenService.accessTokenCreation(obj, callback);
    }

    public static void cardInit(String accessToken, JsonObject obj, TokenCallbackInterface callback) {
        obj.addProperty("entityId", hps_entityid);
        tokenService.cardInit(accessToken, obj, callback);
    }
    public static void cardList(String accessToken, String customerId, TokenCallbackInterface callback) {
        tokenService.cardList(accessToken,customerId, callback);
    }

    public static boolean cardRemoveFull(CardData cardData) {
        JsonObject obj = new JsonObject();
        obj.addProperty("redirect_uri","https://test.hipay.mn/cardverify/result");
        AtomicReference<String> accessToken = new AtomicReference<>("");
        AtomicBoolean resultVal = new AtomicBoolean(false);
        accessTokenCreation(obj, result -> {
            Log.i("TOKEN ACCESSTOKEN", "result: " + result);
            if(result.has("code") && result.get("code").getAsInt() == 1){
                accessToken.set(result.get("access_token").getAsString());
            } else {
                accessToken.set("");
                Log.e("TOKEN ACCESSTOKEN ERROR", "result: " + result);
            }
            Log.i("TOKEN ACCESSTOKEN", accessToken.get());
            if(accessToken.get().length() != 0) {
                tokenService.cardRemove("Bearer "+ accessToken, cardData.cardId, result2 -> {
                    Log.i("TOKEN CARDREMOVE", "result: " + result2);
                    if(result2.has("code") && result2.get("code").getAsInt() == 1){
                        resultVal.set(true);
                    } else {
                        resultVal.set(false);
                        Log.e("TOKEN CARDREMOVE ERROR", "result: " + result2);
                    }
                });
            }
        });
        return resultVal.get();
    }
//
//    public static void checkCheckout(TokenCallbackInterface callback) {
//        tokenService.checkCheckout(callback);
//    }
}
