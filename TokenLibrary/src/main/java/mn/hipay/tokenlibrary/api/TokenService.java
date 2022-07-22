package mn.hipay.tokenlibrary.api;

import java.util.concurrent.TimeUnit;
import android.util.Log;
import com.google.gson.JsonObject;

import mn.hipay.tokenlibrary.exception.HpsException;
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
    private static final String baseUrl = "https://test.hipay.mn";

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
                        try {
                            callback.response(obj);
                        } catch (HpsException e) {
                            e.printStackTrace();
                        }
                    } else {
                        JsonObject dr = response.body();
                        if(dr != null) {
//                            Log.i("TOKEN RESULT: ", dr.toString());
                            try {
                                callback.response(dr);
                            } catch (HpsException e) {
                                e.printStackTrace();
                            }
                        } else {
                            JsonObject obj = new JsonObject();
                            obj.addProperty("isSuccess", false);
                            obj.addProperty("code", "500");
                            obj.addProperty("msg", "500 Сервер унтарсан байна.");
                            try {
                                callback.response(obj);
                            } catch (HpsException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                if (callback != null) {
                    JsonObject obj = new JsonObject();
                    obj.addProperty("msg", "Алдаа: 401 Серверт хандах эрх хаалттай");
                    try {
                        callback.response(obj);
                    } catch (HpsException e) {
                        e.printStackTrace();
                    }
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

    public void cardList(String token, String customerId, TokenCallbackInterface callback) {
        functionCall(api.cardList(token, customerId), callback);
    }

    public void cardRemove(String token, String cardId, TokenCallbackInterface callback) {
        functionCall(api.cardRemove(token, cardId), callback);
    }

    public void createCheckout(String merchantToken, JsonObject obj, TokenCallbackInterface callback) {
        functionCall(api.createCheckout(merchantToken, obj), callback);
    }

    public void checkCheckout(String merchantToken, String checkoutId, TokenCallbackInterface callback) {
        functionCall(api.checkCheckout(merchantToken, checkoutId), callback);
    }

    public void doPayment(String merchantToken, JsonObject obj, TokenCallbackInterface callback) {
        functionCall(api.doPayment(merchantToken, obj), callback);
    }

    public void checkPayment(String merchantToken, String paymentId, String entityId, TokenCallbackInterface callback) {
        functionCall(api.checkPayment(merchantToken, paymentId, entityId), callback);
    }
}
