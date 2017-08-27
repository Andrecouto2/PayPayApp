package br.com.andrecouto.paypay.application;

import android.content.Context;

import javax.inject.Singleton;

import br.com.andrecouto.paypay.api.ServiceGenerator;
import br.com.andrecouto.paypay.api.TokenService;
import br.com.andrecouto.paypay.api.UserService;
import br.com.andrecouto.paypay.constants.RestApi;
import br.com.andrecouto.paypay.manager.AuthenticationManager;
import br.com.andrecouto.paypay.manager.UserManager;
import br.com.andrecouto.paypay.viewmodel.LoginViewModel;
import br.com.andrecouto.paypay.viewmodel.UserViewModel;
import dagger.Module;
import dagger.Provides;

/**
 * Things that live for the duration of the application and will be injected into whatever the
 * component defines
 */
@Module
public class ApplicationModule {
    public ApplicationModule() {
    }

    @Provides
    @Singleton
    LoginViewModel provideLoginViewModel(TokenService tokenService) {
        return new LoginViewModel(tokenService);
    }

    @Provides
    @Singleton
    UserViewModel provideUserViewModel(UserService userService) {
        return new UserViewModel(userService);
    }

    @Provides
    @Singleton
    ServiceGenerator provideRestService() {
        return new ServiceGenerator(RestApi.BASE_URL);
    }

    @Provides
    @Singleton
    TokenService provideTokenService(ServiceGenerator restClient) {
        return new TokenService(restClient);
    }

    @Provides
    @Singleton
    UserService provideUserService(ServiceGenerator restClient) {
        return new UserService(restClient);
    }

    @Provides
    @Singleton
    AuthenticationManager provideAuthenticationManager() {
        return new AuthenticationManager();
    }

    @Provides
    @Singleton
    UserManager provideUserManager() {
        return new UserManager();
    }
}
