package br.com.andrecouto.paypay.api;

import br.com.andrecouto.paypay.entity.AccessToken;
import br.com.andrecouto.paypay.entity.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIClient {

    @FormUrlEncoded
    @POST("/Token")
    Call<AccessToken> getNewAccessToken(
            @Field("username") String username,
            @Field("password") String password,
            @Field("grant_type") String grantType);

    @GET("api/Usuario")
    Call<User> getUser(
            @Query("cellphone") String cellphone
    );

    @POST("api/Usuario")
    Call<User> registerUser(@Body User user);
}
