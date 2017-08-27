package br.com.andrecouto.paypay.application;


import android.app.Application;


public class AppApplication extends Application {
    private ApplicationComponent mComponent;
    private boolean isMenuProfileOpen = false;

    @Override
    public void onCreate() {
        super.onCreate();

        mComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule())
                .build();

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
}