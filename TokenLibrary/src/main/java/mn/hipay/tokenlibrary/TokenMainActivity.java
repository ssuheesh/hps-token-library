package mn.hipay.tokenlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.JsonObject;

import mn.hipay.tokenlibrary.service.TokenHelper;

public class TokenMainActivity extends AppCompatActivity {
    public static String accessToken = "";
    public static String initId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token_main);
    }

    public void addItems(View v) {
        Log.i("TOKEN", "CARD ADD CLICKED!");
        JsonObject obj = new JsonObject();
        obj.addProperty("redirect_uri","www.mplus.mn");

        TokenHelper.accessTokenCreation(obj, result -> {
            Log.i("TOKEN ACCESSTOKEN", "result: " + result);
            if(result.has("code") && result.get("code").getAsInt() == 1){
                accessToken = result.get("access_token").getAsString();
            } else {
                accessToken = "";
                Log.e("TOKEN ACCESSTOKEN ERROR", "result: " + result);
            }
            Log.i("TOKEN ACCESSTOKEN", accessToken);
            if(accessToken.length() != 0) {
                JsonObject obj2 = new JsonObject();
                obj2.addProperty("redirect_uri", "https://www.mplus.mn");
                obj2.addProperty("return_uri", "");
                obj2.addProperty("customer_id", "TEST_TOKEN_CUSTOMERID");
                TokenHelper.cardInit("Bearer "+ accessToken, obj2, result2 -> {
                    Log.i("TOKEN CARDINIT", "result: " + result2);
                    if(result.has("code") && result2.get("code").getAsInt() == 1){
                        initId = result2.get("initId").getAsString();
                    } else {
                        initId = "";
                        Log.e("TOKEN CARDINIT ERROR:", "result: " + result2);
                    }
                    if(initId.length() != 0) {
//                        TokenHelper.cardAdd(initId, result -> {
//
//                        });
                        Log.i("TOKEN CARDFORM", "WEBVIEW INITID");
                        Intent webViewIntent = new Intent(this, WebViewActivity.class);
                        webViewIntent.putExtra("initId", initId);
                        startActivity(webViewIntent);
                    }
                });
            }
        });




    }

}