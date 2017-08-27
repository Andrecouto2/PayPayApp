package br.com.andrecouto.paypay.util;


import android.content.Context;
import android.support.design.widget.BottomNavigationView;
import android.view.View;

import br.com.andrecouto.paypay.activity.BaseLoggedActivity;

public class TabBarUtils {

    private TabBarUtils() {}

    public static void hideTabBar(Context context) {
        BottomNavigationView tabBarView = BaseLoggedActivity.getTabBarView(context);
        if (tabBarView != null) {
            tabBarView.setVisibility(View.GONE);
        }
    }

    public static void showTabBar(Context context) {
        BottomNavigationView tabBarView = BaseLoggedActivity.getTabBarView(context);
        if (tabBarView != null) {
            tabBarView.setVisibility(View.VISIBLE);
        }
    }
}
