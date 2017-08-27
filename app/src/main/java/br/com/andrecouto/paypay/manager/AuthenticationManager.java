package br.com.andrecouto.paypay.manager;


import br.com.andrecouto.paypay.entity.AccessToken;

public class AuthenticationManager {

    private AccessToken accessToken;

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }
}
