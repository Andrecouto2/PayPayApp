package br.com.andrecouto.paypay.application;

import javax.inject.Singleton;

import br.com.andrecouto.paypay.activity.BaseLoggedActivity;
import br.com.andrecouto.paypay.activity.LoginActivity;
import br.com.andrecouto.paypay.activity.RegisterActivity;
import br.com.andrecouto.paypay.view.custom.HeaderView;
import br.com.andrecouto.paypay.fragment.BaseLoggedFragment;
import dagger.Component;

/**
 * Defines injections at the application level
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(LoginActivity activity);
    void inject(RegisterActivity activity);
    void inject(BaseLoggedActivity activity);
    void inject(BaseLoggedFragment fragment);
    void inject(HeaderView headerView);

}