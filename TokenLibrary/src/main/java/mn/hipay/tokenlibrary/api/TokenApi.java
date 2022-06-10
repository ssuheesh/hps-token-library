package mn.hipay.tokenlibrary.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TokenApi {
    @POST("/v2/auth/token")
    Call<JsonObject> accessTokenCreation(@Body JsonObject json);

    @POST("/v2/card/init")
    Call<JsonObject> cardInit(@Header("Authorization") String token, @Body JsonObject json);

    @GET("/v2/card/form/{cardInitId}")
    Call<JsonObject> cardAdd(@Path("cardInitId") String cardInitId);

    @GET("/v2/card/list/{customerId}")
    Call<JsonObject> cardList(@Header("Authorization") String token, @Path("customerId") String customerId);

    @POST("/v2/card/remove/{cardId}")
    Call<JsonObject> cardRemove(@Header("Authorization") String token, @Path("cardId") String cardId);
}
