package mn.hipay.tokenlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import mn.hipay.tokenlibrary.model.AdapterCardData;
import mn.hipay.tokenlibrary.model.CardData;
import mn.hipay.tokenlibrary.service.TokenHelper;

public class TokenMainActivity extends AppCompatActivity {
    public static String accessToken = "";
    public static String initId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token_main);
//        String[] cardArray = {
//                "card1",
//                "card2"
//        };
//
//        ArrayAdapter adapter = new ArrayAdapter<String>(this,
//                R.layout.activity_listview, cardArray);
//
//        ListView listView = (ListView) findViewById(R.id.cardList);
//        listView.setAdapter(adapter);
        Gson gson = new Gson();
        final AdapterCardData[] adbCardData = new AdapterCardData[1];
        ArrayList<CardData> myListItems  = new ArrayList<>();

        JsonObject obj = new JsonObject();
        obj.addProperty("redirect_uri","https://test.hipay.mn/cardverify/result");
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
                TokenHelper.cardList("Bearer "+ accessToken, "TEST_TOKEN_CUSTOMERID", result2 -> {
                    Log.i("TOKEN CARDLIST", "result: " + result2);
                    if(result2.has("code") && result2.get("code").getAsInt() == 1){
                        if(result2.has("cards")){
                            for(int i=0; i< result2.getAsJsonArray("cards").size(); i++){
                                CardData cardData = gson.fromJson(result2.getAsJsonArray("cards").get(i), CardData.class);
                                myListItems.add(cardData);
                                Log.i("CardData", cardData.toString());
                            }
                            adbCardData[0] = new AdapterCardData (TokenMainActivity.this, 0, myListItems);
                            ListView listView = (ListView) findViewById(R.id.cardList);
                            listView.setAdapter(adbCardData[0]);
                        }
                    } else {
                        Log.e("TOKEN CARDLIST ERROR:", "result: " + result2);
                    }
                });
            }
        });
    }

    public void addItems(View v) {
        Log.i("TOKEN", "CARD ADD CLICKED!");
        JsonObject obj = new JsonObject();
        obj.addProperty("redirect_uri","https://test.hipay.mn/cardverify/result");

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
                obj2.addProperty("redirect_uri", "https://test.hipay.mn/chp/cardverify/result");
                obj2.addProperty("return_uri", "HPSSDK.processCardBack()");
                obj2.addProperty("customer_id", "TEST_TOKEN_CUSTOMERID");
                TokenHelper.cardInit("Bearer "+ accessToken, obj2, result2 -> {
                    Log.i("TOKEN CARDINIT", "result: " + result2);
                    if(result2.has("code") && result2.get("code").getAsInt() == 1){
                        initId = result2.get("initId").getAsString();
                    } else {
                        initId = "";
                        Log.e("TOKEN CARDINIT ERROR:", "result: " + result2);
                    }
                    if(initId.length() != 0) {
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