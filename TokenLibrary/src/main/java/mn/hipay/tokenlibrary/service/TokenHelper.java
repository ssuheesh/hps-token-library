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

    public void createCheckout(JsonObject obj, TokenCallbackInterface callback) {
        tokenService.createCheckout(this.hps_token, obj, callback);
    }

    public void checkCheckout(String checkoutId, TokenCallbackInterface callback) {
        tokenService.checkCheckout(this.hps_token, checkoutId, callback);
    }

    public void doPayment(JsonObject obj, TokenCallbackInterface callback) {
        tokenService.doPayment(this.hps_token, obj, callback);
    }

    public void checkPayment(String paymentId, TokenCallbackInterface callback) {
        tokenService.checkPayment(this.hps_token, paymentId, this.hps_entityid, callback);
    }
}
