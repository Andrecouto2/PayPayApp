package br.com.andrecouto.paypay.fragment.dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
        View v = inflater.inflate(R.layout.employee_dashboard_fragment, container, false);
        ButterKnife.bind(this, v);
        headerView.setHeaderMenuProfileListener(new HeaderView.HeaderMenuProfileListener() {
            @Override
            public void onToggle(boolean isOpen) {
                if (AccessibilityUtils.isAccessibilityActive(getContext())) {
                    if (isOpen) {
                       /* ViewCompat.setImportantForAccessibility(recyclerViewCards, ViewCompat
                                .IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS);*/
                    } else {
                       /* ViewCompat.setImportantForAccessibility(recyclerViewCards, ViewCompat
                                .IMPORTANT_FOR_ACCESSIBILITY_NO);*/

                    }
                }

            }
        });
        headerView.setHeaderAccessibilityFocus(ACCESSIBILITY_INIT_FOCUS);
        return v;
    }

    @Override
    public String tag() {
        return getTag();
    }


}
