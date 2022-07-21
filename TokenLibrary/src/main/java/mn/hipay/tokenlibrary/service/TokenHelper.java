package mn.hipay.tokenlibrary.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.JsonObject;

import mn.hipay.tokenlibrary.exception.HpsException;
import mn.hipay.tokenlibrary.R;
import mn.hipay.tokenlibrary.api.TokenCallbackInterface;
import mn.hipay.tokenlibrary.api.TokenService;
import mn.hipay.tokenlibrary.model.CardData;

public class TokenHelper  {
    public static SharedPreferences preferences;
    private static TokenService tokenService = new TokenService();
    private String hps_entityid="mplus.mn";
    private String hps_token="VnZCSkdIZWk4Q21XeWx3bw";
    private String hps_auth_code="BBE586D4EAE65BF7E0532A64A8C097BA";
    private String hps_grant_type = "authorization_code";
    private Context context;

    public TokenHelper(Context current) {
        this.context = current;
        this.hps_entityid = this.context.getString(R.string.hps_entityid);
        this.hps_token = this.context.getString(R.string.hps_token);
        this.hps_auth_code = this.context.getString(R.string.hps_code);
        this.hps_grant_type = this.context.getString(R.string.hps_grant_type);
    }

    public TokenHelper(Context current, String hps_entityid, String hps_token, String hps_auth_code, String hps_grant_type ) {
        this.hps_entityid = hps_entityid;
        this.hps_token = hps_token;
        this.hps_auth_code = hps_auth_code;
        this.hps_grant_type = hps_grant_type;
        this.context = current;
    }

    public void accessTokenCreation(JsonObject obj, TokenCallbackInterface callback) {
        obj.addProperty("client_id", this.hps_entityid);
        obj.addProperty("client_secret", this.hps_token);
        obj.addProperty("code", this.hps_auth_code);
        obj.addProperty("grant_type", this.hps_grant_type);
        tokenService.accessTokenCreation(obj, callback);
    }

    public void cardInit(String accessToken, JsonObject obj, TokenCallbackInterface callback) {
        obj.addProperty("entityId", this.hps_entityid);
        tokenService.cardInit(accessToken, obj, callback);
    }
    public void cardList(String accessToken, String customerId, TokenCallbackInterface callback) {
        tokenService.cardList(accessToken,customerId, callback);
    }

    public void cardRemoveFull(CardData cardData) {
        JsonObject obj = new JsonObject();
        obj.addProperty("redirect_uri","https://test.hipay.mn/cardverify/result");
        this.accessTokenCreation(obj, result -> {
            String accessToken = "";
            Log.i("TOKEN ACCESSTOKEN", "result: " + result);
            if(result.has("code") && result.get("code").getAsInt() == 1){
                accessToken = result.get("access_token").getAsString();
            } else {
                accessToken = "";
                Log.e("TOKEN ACCESSTOKEN ERROR", "result: " + result);
//                throw new HpsException(HpsException.ACCESS_TOKEN_EXCEPTION,"Failed to create access_token: " + result);
            }
            Log.i("TOKEN ACCESSTOKEN", accessToken);
            if(accessToken.length() != 0) {
                tokenService.cardRemove("Bearer "+ accessToken, cardData.cardId, result2 -> {
                    Log.i("TOKEN CARDREMOVE", "result: " + result2);
                    if(result2.has("code") && result2.get("code").getAsInt() == 1){
                        Log.i("TOKEN CARDREMOVE SUCCES", "result: " + result2);
                    } else {
                        Log.e("TOKEN CARDREMOVE ERROR", "result: " + result2);
//                        throw new HpsException(HpsException.CARD_REMOVE_EXCEPTION,"Failed to remove a card: " + result2);
                    }
                });
            }
        });
    }

    public void cardAdd() {
        Log.i("TOKEN", "CARD ADD STARTED!");
        JsonObject obj = new JsonObject();
        obj.addProperty("redirect_uri","https://test.hipay.mn/cardverify/result");

        this.accessTokenCreation(obj, result -> {
            Log.i("TOKEN ACCESSTOKEN", "result: " + result);
            String accessToken = "";
            if(result.has("code") && result.get("code").getAsInt() == 1){
                accessToken = result.get("access_token").getAsString();
            } else {
                accessToken = "";
                Log.e("TOKEN ACCESSTOKEN ERROR", "result: " + result);
            }
            Log.i("TOKEN ACCESSTOKEN", accessToken);
            if(accessToken.length() != 0) {
                JsonObject obj2 = new JsonObject();
                obj2.addProperty("redirect_uri", "https://test.hipay.mn/cardverify/result");
                obj2.addProperty("return_uri", "HPSSDK.processCardBack()");
                obj2.addProperty("customer_id", "TEST_TOKEN_CUSTOMERID");
                this.cardInit("Bearer "+ accessToken, obj2, result2 -> {
                    String initId;
                    Log.i("TOKEN CARDINIT", "result: " + result2);
                    if(result2.has("code") && result2.get("code").getAsInt() == 1){
                        initId = result2.get("initId").getAsString();
                    } else {
                        initId = "";
                        Log.e("TOKEN CARDINIT ERROR:", "result: " + result2);
                    }
//                    if(initId.length() != 0) {
//                        Log.i("TOKEN CARDFORM", "WEBVIEW INITID");
//                        Intent webViewIntent = new Intent(this, WebViewActivity.class);
//                        webViewIntent.putExtra("initId", initId);
//                        startActivity(webViewIntent);
//                    }
                });
            }
        });
    }

//
//    public static void checkCheckout(TokenCallbackInterface callback) {
//        tokenService.checkCheckout(callback);
//    }
}
