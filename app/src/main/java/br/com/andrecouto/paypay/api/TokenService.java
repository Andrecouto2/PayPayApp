package br.com.andrecouto.paypay.api;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import br.com.andrecouto.paypay.entity.AccessToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokenService {

    private APIClient mApi;
    private Map<String, Call<AccessToken>> mGetAcessToken = new HashMap<>();

    public TokenService(ServiceGenerator serviceGenerator) {
        mApi = serviceGenerator.createService(APIClient.class);
    }

    public void getToken(final String username, final String password, final String grantType, final CallBack<AccessToken> callBack) {
        getToken(username, password, grantType, callBack, false);
    }

    public void getToken(final String username, final String password, final String grantType, final CallBack<AccessToken> callBack, boolean isCancelable) {
        Call<AccessToken> call = mApi.getNewAccessToken(username, password, grantType);
        if (isCancelable) {
            if (mGetAcessToken.containsKey(username)) return;
            mGetAcessToken.put(username, call);
        }
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                mGetAcessToken.remove(username);
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                } else {
                    JSONObject jObjError = new JSONObject();
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    callBack.onError("Atenção!!!", jObjError.optString("error_description"));
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                mGetAcessToken.remove(username);
                callBack.onError("Error", t.getMessage());
            }

        });

    }

    public void cancel(String username) {
        if (mGetAcessToken.containsKey(username)) {
            mGetAcessToken.get(username).cancel();
            mGetAcessToken.remove(username);
        }
    }

    public void cancelAll() {
        for (String key : mGetAcessToken.keySet()) {
            mGetAcessToken.get(key).cancel();
        }
        mGetAcessToken.clear();
    }
}
