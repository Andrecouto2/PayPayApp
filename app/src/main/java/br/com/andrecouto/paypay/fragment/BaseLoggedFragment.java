package br.com.andrecouto.paypay.fragment;


import android.os.Bundle;


import javax.inject.Inject;

import br.com.andrecouto.paypay.application.AppApplication;
import br.com.andrecouto.paypay.manager.AuthenticationManager;
import br.com.andrecouto.paypay.manager.UserManager;


public class BaseLoggedFragment extends BaseFragment {

    @Inject
    public AuthenticationManager authenticationManager;
    @Inject
    public UserManager userManager;

    @Override
    public String tag() {
        return getTag();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ((AppApplication) getActivity().getApplicationContext()).getComponent().inject(BaseLoggedFragment.this);
        super.onCreate(savedInstanceState);

    }
}
