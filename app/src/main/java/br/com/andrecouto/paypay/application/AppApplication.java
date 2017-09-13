package br.com.andrecouto.paypay.application;


import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;

import br.com.andrecouto.kotlin.chatlib.socketio.listener.AppSocketListener;
import io.fabric.sdk.android.Fabric;


public class AppApplication extends Application {
    private ApplicationComponent mComponent;
    private boolean isMenuProfileOpen = false;
    public static Context appContext;
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        appContext = getApplicationContext();
        mComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule())
                .build();
        initSocketChatMesengerListener();
    }

    public ApplicationComponent getComponent() {
        return mComponent;
    }

    public boolean isMenuProfileOpen() {
        return isMenuProfileOpen;
    }

    public void setMenuProfileOpen(boolean menuProfileOpen) {
        isMenuProfileOpen = menuProfileOpen;
    }

    public void destroySocketChatMessengerListener() {
        AppSocketListener.Companion.getInstance().destroy();
    }

    private void initSocketChatMesengerListener() {
        AppSocketListener.Companion.getInstance().initialize(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        destroySocketChatMessengerListener();
    }
}