package br.com.andrecouto.paypay.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import br.com.andrecouto.paypay.R;
import br.com.andrecouto.paypay.persistence.AccessTokenDAO;
import br.com.andrecouto.paypay.persistence.UserDAO;
import br.com.andrecouto.paypay.sessionmanager.SessionManager;


public class MainActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3500;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AccessTokenDAO.setContext(this);
        UserDAO.setContext(this);
        sessionManager = new SessionManager(this.getBaseContext());
        carregar();
    }

    private void carregar() {
        Animation anim = AnimationUtils.loadAnimation(this,
                R.anim.animacao_splash);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.ivLogoSplash);
        if (iv != null) {
            iv.clearAnimation();
            iv.startAnimation(anim);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkSession();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void checkSession() {
        if(sessionManager.isLoggedIn()) {
            sessionManager.loginUser();
        } else {
            sessionManager.logoutUser();
        }
        finish();
    }
}
