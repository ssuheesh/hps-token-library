package mn.hipay.tokenlibrary.api;

import java.util.concurrent.TimeUnit;
import android.util.Log;
import com.google.gson.JsonObject;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class TokenService {
    String url;
    int code;
    TokenApi api;
    private static String baseUrl = "https://test.hipay.mn";

    public TokenService() {
        url = baseUrl;
        OkHttpClient client = null;
        client = new OkHttpClient.Builder()
                .callTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        api = retrofit.create(TokenApi.class);
    }

    private void functionCall(Call<JsonObject> resultCall, final TokenCallbackInterface callback) {
        resultCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (callback != null) {
                    if (response.code() == 401) {
                        JsonObject obj = new JsonObject();
                        obj.addProperty("isSuccess", false);
                        obj.addProperty("code", "401");
                        obj.addProperty("msg", "401 Серверт хандах эрх хаалттай");
                        callback.response(obj);
                    } else {
                        JsonObject dr = response.body();
                        if(dr != null) {
//                            Log.i("TOKEN RESULT: ", dr.toString());
                            callback.response(dr);
                        } else {
                            JsonObject obj = new JsonObject();
                            obj.addProperty("isSuccess", false);
                            obj.addProperty("code", "500");
                            obj.addProperty("msg", "500 Сервер унтарсан байна.");
                            callback.response(obj);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                if (callback != null) {
                    JsonObject obj = new JsonObject();
                    obj.addProperty("msg", "Алдаа: 401 Серверт хандах эрх хаалттай");
                    callback.response(obj);
                }
            }
        });
    }

    public void accessTokenCreation(JsonObject obj, TokenCallbackInterface callback) {
        functionCall(api.accessTokenCreation(obj), callback);
    }

    public void cardInit(String token, JsonObject obj, TokenCallbackInterface callback) {
        functionCall(api.cardInit(token, obj), callback);
    }

    public void cardAdd(String cardInitId, TokenCallbackInterface callback) {
        functionCall(api.cardAdd(cardInitId), callback);
    }
//
//    public void clearTrash(String token, JsonObject obj, MonitoringCallbackInterface callback) {
//        functionCall( api.clearTrash(token, obj), callback);
//    }
//
//    public void deviceFirstRegister(String token, JsonObject obj, MonitoringCallbackInterface callback) {
//        System.out.println(obj.toString());
//        functionCall( api.deviceFirstRegister(token, obj), callback);
//    }
//
//    public void barcode(String barcode, String deviceCode, MonitoringCallbackInterface callback) {
//        functionCall(api.barcode(barcode, deviceCode), callback);
//    }
}
