package br.com.andrecouto.paypay.fragment.dashboard;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.andrecouto.paypay.R;
import br.com.andrecouto.paypay.view.custom.HeaderView;
import br.com.andrecouto.paypay.fragment.BaseLoggedFragment;
import br.com.andrecouto.paypay.util.AccessibilityUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MeDashBoardFragment extends BaseLoggedFragment {

    @BindView(R.id.header_view)
    HeaderView headerView;

    private static final int ACCESSIBILITY_INIT_FOCUS = 2000;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_me, container, false);
        ButterKnife.bind(this, v);
        headerView.setHeaderAccessibilityFocus(ACCESSIBILITY_INIT_FOCUS);
        return v;
    }

    @Override
    public String tag() {
        return getTag();
    }

}
