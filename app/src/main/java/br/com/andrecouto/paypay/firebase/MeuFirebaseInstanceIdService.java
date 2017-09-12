package br.com.andrecouto.paypay.firebase;

import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MeuFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = MeuFirebaseInstanceIdService.class.getName();

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        sendResgistrationToServer(refreshedToken);
    }

    private void sendResgistrationToServer(String token) {
        Log.d(TAG, "Refreshed Token" + token);
    }
}