package br.com.andrecouto.paypay.viewmodel;

import android.os.Handler;

abstract class BaseViewModel {

    protected Handler mUiThreadHandler;

    public void onCreate(Handler handler) {
        mUiThreadHandler = handler;
    }

    public void onDestroy() {
        mUiThreadHandler = null;
    }
}