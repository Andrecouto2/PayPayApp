package br.com.andrecouto.paypay.api;

import android.content.Context;
import android.text.TextUtils;

import java.io.IOException;
import java.net.Authenticator;

import br.com.andrecouto.paypay.constants.RestApi;
import br.com.andrecouto.paypay.entity.AccessToken;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Route;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static OkHttpClient httpClient;
    private static Retrofit retrofit;
    private static Context mContext;
    private static AccessToken mToken;
    private static Retrofit.Builder builder;


    public ServiceGenerator(String baseUrl) {
        httpClient = new OkHttpClient();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

    public static <S> S createService(Class<S> serviceClass, AccessToken accessToken, Context c) {
        httpClient = new OkHttpClient();
        builder = new Retrofit.Builder()
                .baseUrl(RestApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        if(accessToken != null) {
            mContext = c;
            mToken = accessToken;
            final AccessToken token = accessToken;
            httpClient.newBuilder().addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Accept", "application/json")
                            .header("Content-type", "application/json")
                            .header("Authorization",
                                    token.getTokenType() + " " + token.getAccessToken())
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });

            /*httpClient.authenticator(new Authenticator() {
                @Override
                public Request authenticate(Route route, Response response) throws IOException {
                    if(responseCount(response) >= 2) {
                        // If both the original call and the call with refreshed token failed,
                        // it will probably keep failing, so don't try again.
                        return null;
                    }

                    // We need a new client, since we don't want to make another call using our client with access token
                    APIClient tokenClient = createService(APIClient.class);
                    Call<AccessToken> call = tokenClient.getRefreshAccessToken(mToken.getRefreshToken(),
                            mToken.getClientID(), mToken.getClientSecret(), API_OAUTH_REDIRECT,
                            "refresh_token");
                    try {
                        retrofit2.Response<AccessToken> tokenResponse = call.execute();
                        if(tokenResponse.code() == 200) {
                            AccessToken newToken = tokenResponse.body();
                            mToken = newToken;
                            SharedPreferences prefs = mContext.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
                            prefs.edit().putBoolean("oauth.loggedin", true).apply();
                            prefs.edit().putString("oauth.accesstoken", newToken.getAccessToken()).apply();
                            prefs.edit().putString("oauth.refreshtoken", newToken.getRefreshToken()).apply();
                            prefs.edit().putString("oauth.tokentype", newToken.getTokenType()).apply();

                            return response.request().newBuilder()
                                    .header("Authorization", newToken.getTokenType() + " " + newToken.getAccessToken())
                                    .build();
                        } else {
                            return null;
                        }
                    } catch(IOException e) {
                        return null;
                    }
                }
            });*/
        }

        OkHttpClient client = httpClient;
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

}
