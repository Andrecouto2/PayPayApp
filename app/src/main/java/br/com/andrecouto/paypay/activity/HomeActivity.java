package br.com.andrecouto.paypay.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.view.MenuItem;

import br.com.andrecouto.paypay.R;
import br.com.andrecouto.paypay.application.AppApplication;
import br.com.andrecouto.paypay.fragment.dashboard.ChatDashBoardFragment;
import br.com.andrecouto.paypay.fragment.dashboard.DiscoveryDashBoardFragment;
import br.com.andrecouto.paypay.fragment.dashboard.MeDashBoardFragment;
import br.com.andrecouto.paypay.util.FragmentUtils;


public class HomeActivity extends BaseLoggedActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((AppApplication) getApplicationContext()).getComponent().inject(HomeActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentUtils.replaceFragment(getSupportFragmentManager(), new ChatDashBoardFragment(), R.id.frame_home_container, true);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_chat:
                    FragmentUtils.replaceFragment(getSupportFragmentManager(), new ChatDashBoardFragment(), R.id.frame_home_container, true);
                    return true;
                case R.id.navigation_contacts:

                    return true;
                case R.id.navigation_compass:
                    FragmentUtils.replaceFragment(getSupportFragmentManager(), new DiscoveryDashBoardFragment(), R.id.frame_home_container, true);
                    return true;
                case R.id.navigation_avatar:
                    FragmentUtils.replaceFragment(getSupportFragmentManager(), new MeDashBoardFragment(), R.id.frame_home_container, true);
                    return true;
            }
            return false;
        }

    };



}
