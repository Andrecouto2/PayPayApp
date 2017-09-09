package br.com.andrecouto.paypay.persistence;

import android.content.Context;

import java.security.SecureRandom;

import br.com.andrecouto.paypay.entity.AccessToken;
import br.com.andrecouto.paypay.util.RealmUtils;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;

import static io.realm.Realm.getDefaultInstance;
public class AccessTokenDAO {

    private static Realm realm;
    private static Context context;

    private AccessTokenDAO() {
    }

    private static void configureRealmInstance() {
        Realm.init(context);
        byte[] key = new byte[64];
        new SecureRandom().nextBytes(key);
        RealmConfiguration configRealm = new RealmConfiguration.Builder()
                .name("mob13.realm").deleteRealmIfMigrationNeeded().schemaVersion(2)
                .build();

        Realm.setDefaultConfiguration(configRealm);
    }

    public static void insert(AccessToken accessToken) {
        try {
            configureRealmInstance();
            realm = getDefaultInstance();
            AccessToken accessTokenRealm =
                    realm.where(AccessToken.class)
                            .equalTo("accessToken", accessToken.getAccessToken())
                            .findFirst();
            if (accessTokenRealm != null) {
                realm.beginTransaction();
                accessTokenRealm.setAccessToken(accessToken.getAccessToken());
                accessTokenRealm.setExpires(accessToken.getExpires());
                accessTokenRealm.setExpiresIn(accessToken.getExpiresIn());
                accessTokenRealm.setTokenType(accessToken.getTokenType());
                accessTokenRealm.setIssued(accessToken.getIssued());
                realm.commitTransaction();
            } else {
                realm.beginTransaction();
                AccessToken accessTokenRealm2 = new AccessToken();
                accessTokenRealm2.setId(RealmUtils.getNextId(realm, AccessToken.class, "id"));
                accessTokenRealm2.setAccessToken(accessToken.getAccessToken());
                accessTokenRealm2.setExpires(accessToken.getExpires());
                accessTokenRealm2.setExpiresIn(accessToken.getExpiresIn());
                accessTokenRealm2.setTokenType(accessToken.getTokenType());
                accessTokenRealm2.setIssued(accessToken.getIssued());
                realm.copyToRealmOrUpdate(accessTokenRealm2);
                realm.commitTransaction();
            }
        } catch (RealmException e) {
            realm.cancelTransaction();
        } finally {
            realm.close();
            realm = null;
        }
    }

    public static AccessToken selectAccesToken(String accessToken_) {
        configureRealmInstance();
        AccessToken accessToken = new AccessToken();
        try {
            realm = getDefaultInstance();
            RealmResults<AccessToken> results = realm.where(AccessToken.class).equalTo("accessToken", accessToken_).findAll();
            if (results != null && results.size() > 0) {
                accessToken.setIssued(results.get(0).getIssued());
                accessToken.setTokenType(results.get(0).getTokenType());
                accessToken.setExpiresIn(results.get(0).getExpiresIn());
                accessToken.setExpires(results.get(0).getExpires());
                accessToken.setAccessToken(results.get(0).getAccessToken());
                accessToken.setId(results.get(0).getId());
            }
        } catch (RealmException e) {
            realm.cancelTransaction();
        } finally {
            realm.close();
            realm = null;
        }
        return accessToken;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        AccessTokenDAO.context = context;
    }
}