package br.com.andrecouto.paypay.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.view.View;

import javax.inject.Inject;

import br.com.andrecouto.paypay.R;
import br.com.andrecouto.paypay.application.AppApplication;
import br.com.andrecouto.paypay.persistence.AccessTokenDAO;
import br.com.andrecouto.paypay.persistence.UserDAO;
import br.com.andrecouto.paypay.sessionmanager.SessionManager;
import br.com.andrecouto.paypay.view.custom.HeaderView;
import br.com.andrecouto.paypay.manager.AuthenticationManager;
import br.com.andrecouto.paypay.manager.UserManager;


public class BaseLoggedActivity extends BaseActivity {

    @Inject
    AuthenticationManager authenticationManager;
    @Inject
    UserManager userManager;
    public SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((AppApplication) getApplicationContext()).getComponent().inject(BaseLoggedActivity.this);
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(this);
        checkSession();
    }

    public static HeaderView getHeaderView(Context context) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            View view = activity.findViewById(R.id.header_view);
            if (view != null) {
                return (HeaderView) view;
            }
        }
        return null;
    }

    public static BottomNavigationView getTabBarView(Context context) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            View view = activity.findViewById(R.id.navigation);
            if (view != null) {
                return (BottomNavigationView) view;
            }
        }

        return null;
    }

    private void checkSession() {
        if(sessionManager.isLoggedIn()) {
           authenticationManager.setAccessToken(AccessTokenDAO.selectAccesToken(sessionManager.getUserDetails().get(SessionManager.KEY_ACCESSTOKEN)));
           userManager.setUser(UserDAO.selectUser(sessionManager.getUserDetails().get(SessionManager.KEY_EMAIL)));
        }
    }
}
