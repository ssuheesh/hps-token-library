package mn.hipay.tokenlibrary;

import android.content.Context;
import android.widget.Toast;
import android.util.Log;

import com.google.gson.JsonObject;

import mn.hipay.tokenlibrary.service.TokenHelper;
import mn.hipay.tokenlibrary.R;


public class ToasterMessage {
    public static void show(Context c, String message){
//        Variables variables = new Variables();
        Toast.makeText(c,message,Toast.LENGTH_SHORT).show();
        JsonObject obj = new JsonObject();
        obj.addProperty("redirect_uri","www.mplus.mn");
        Log.i("TOKEN LIBRARY", c.getString(R.string.hps_entityid));
//        TokenHelper.accessTokenCreation(obj, result -> {
//            Log.i("TOASTER", "result: " + result);
//        });
    }
}
