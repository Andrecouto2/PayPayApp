package br.com.andrecouto.paypay.fragment.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.andrecouto.kotlin.chatlib.activity.ChatMainActivity;
import br.com.andrecouto.paypay.R;
import br.com.andrecouto.paypay.fragment.BaseLoggedFragment;
import br.com.andrecouto.paypay.view.custom.HeaderView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatDashBoardFragment extends BaseLoggedFragment {

    @BindView(R.id.header_view)
    HeaderView headerView;

    private static final int ACCESSIBILITY_INIT_FOCUS = 2000;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, v);
        headerView.setHeaderAccessibilityFocus(ACCESSIBILITY_INIT_FOCUS);
        return v;
    }

    @Override
    public String tag() {
        return getTag();
    }

    @OnClick(R.id.card_view_chat)
    public void onClickChat(View v) {
        Intent intent = new Intent(getActivity(), ChatMainActivity.class);
        intent.putExtra("nickname", userManager.getUser().getNome());
        startActivity(intent);
    }

}
