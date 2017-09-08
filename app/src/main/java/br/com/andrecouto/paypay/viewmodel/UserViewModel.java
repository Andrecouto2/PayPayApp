package br.com.andrecouto.paypay.viewmodel;

import android.app.Activity;

import br.com.andrecouto.paypay.api.CallBack;
import br.com.andrecouto.paypay.api.UserService;
import br.com.andrecouto.paypay.entity.User;
import br.com.andrecouto.paypay.listeners.BaseListener;

public class UserViewModel extends BaseViewModel implements IViewModel {

    private static final String TAG = UserViewModel.class.toString();
    private Listener mListener;
    private UserService userService;

    public UserViewModel(UserService userService) {
        this.userService = userService;
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public void clearListener() {
        mListener = null;
    }

    public void getUser(String cellphone) {

        userService.getUser(cellphone, new CallBack<User>() {
            @Override
            public void onSuccess(User response) {
                mListener.onUserResponse(response);
            }

            @Override
            public void onError(String header, String message) {
                if (mListener != null) mListener.onError(header, message);
            }
        });

    }

    public void registerUser(User user) {

        userService.registerUser(user, new CallBack<User>() {
            @Override
            public void onSuccess(User response) {
                mListener.onUserResponse(response);
            }

            @Override
            public void onError(String header, String message) {
                if (mListener != null) mListener.onError(header, message);
            }
        });
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void setCurrentActivity(Activity value) {

    }

    public interface Listener extends BaseListener {
        void onUserResponse(User user);
    }
}
