package mn.hipay.tokenlibrary.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.webkit.WebView;

import com.google.gson.JsonObject;

import mn.hipay.tokenlibrary.R;
import mn.hipay.tokenlibrary.api.TokenCallbackInterface;
import mn.hipay.tokenlibrary.api.TokenResult;
import mn.hipay.tokenlibrary.api.TokenService;

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

    public static void cardAdd(String cardInitId, TokenCallbackInterface callback) {
//        tokenService.cardAdd(cardInitId, callback);

    }
//
//    public static void checkCheckout(TokenCallbackInterface callback) {
//        tokenService.checkCheckout(callback);
//    }
}
