package br.com.andrecouto.paypay.api;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import br.com.andrecouto.paypay.entity.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService {

    private APIClient mApi;
    private Map<String, Call<User>> mGetUser = new HashMap<>();

    public UserService(ServiceGenerator serviceGenerator) {
        mApi = serviceGenerator.createService(APIClient.class);
    }

    public void getUser(final String cellphone, final CallBack<User> callBack) {
        getUser(cellphone, callBack, false);
    }

    public void registerUser(final User user, final CallBack<User> callBack) {
        registerUser(user, callBack, false);
    }

    public void registerUser(final User user, final CallBack<User> callBack, boolean isCancelable) {
        Call<User> call = mApi.registerUser(user);
        if (isCancelable) {
            if (mGetUser.containsKey(user.getEmail())) return;
            mGetUser.put(user.getEmail(), call);
        }
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                mGetUser.remove(user.getEmail());
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
            public void onFailure(Call<User> call, Throwable t) {
                mGetUser.remove(user.getEmail());
                callBack.onError("Error", t.getMessage());
            }

        });
    }
    
    public void getUser(final String cellphone, final CallBack<User> callBack, boolean isCancelable) {
        Call<User> call = mApi.getUser(cellphone);
        if (isCancelable) {
            if (mGetUser.containsKey(cellphone)) return;
            mGetUser.put(cellphone, call);
        }
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                mGetUser.remove(cellphone);
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
            public void onFailure(Call<User> call, Throwable t) {
                mGetUser.remove(cellphone);
                callBack.onError("Error", t.getMessage());
            }

        });

    }

    public void cancel(String username) {
        if (mGetUser.containsKey(username)) {
            mGetUser.get(username).cancel();
            mGetUser.remove(username);
        }
    }

    public void cancelAll() {
        for (String key : mGetUser.keySet()) {
            mGetUser.get(key).cancel();
        }
        mGetUser.clear();
    }

}
