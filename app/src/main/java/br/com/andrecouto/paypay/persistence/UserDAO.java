package br.com.andrecouto.paypay.persistence;

import android.content.Context;

import java.security.SecureRandom;

import br.com.andrecouto.paypay.entity.User;
import br.com.andrecouto.paypay.util.RealmUtils;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;

import static io.realm.Realm.getDefaultInstance;
import static io.realm.Realm.getInstance;

public class UserDAO {

    private static Realm realm;
    private static Context context;

    private UserDAO() {
    }

    private static void configureRealmInstance() {
        Realm.init(getContext());
        byte[] key = new byte[64];
        new SecureRandom().nextBytes(key);
        RealmConfiguration configRealm = new RealmConfiguration.Builder()
                .name("mob13.realm").deleteRealmIfMigrationNeeded().schemaVersion(2)
                .build();

        Realm.setDefaultConfiguration(configRealm);
    }

    public static void insert(User user) {
        try {
            configureRealmInstance();
            realm = getDefaultInstance();
            User userRealm =
                    realm.where(User.class)
                            .equalTo("email", user.getEmail())
                            .findFirst();
            if (userRealm != null) {
                realm.beginTransaction();
                userRealm.setEmail(user.getEmail());
                userRealm.setCelular(user.getCelular());
                userRealm.setNome(user.getNome());
                userRealm.setCpf(user.getCpf());
                userRealm.setSenha(user.getSenha());
                realm.commitTransaction();
            } else {
                realm.beginTransaction();
                User userRealm2 = new User();
                userRealm2.setIdUsuario(RealmUtils.getNextId(realm, User.class, "idUsuario"));
                userRealm2.setEmail(user.getEmail());
                userRealm2.setCelular(user.getCelular());
                userRealm2.setNome(user.getNome());
                userRealm2.setCpf(user.getCpf());
                userRealm2.setSenha(user.getSenha());
                realm.copyToRealmOrUpdate(userRealm2);
                realm.commitTransaction();
            }

        } catch (RealmException e) {
            realm.cancelTransaction();
        } finally {
            realm.close();
            realm = null;
        }
    }

    public static User selectUser(String email) {
        configureRealmInstance();
        User user = new User();
        try {
            realm = getDefaultInstance();
            RealmResults<User> results = realm.where(User.class).equalTo("email", email).findAll();
            if (results != null && results.size() > 0) {
                user.setIdUsuario(results.get(0).getIdUsuario());
                user.setCpf(results.get(0).getCpf());
                user.setSenha(results.get(0).getSenha());
                user.setEmail(results.get(0).getEmail());
                user.setNome(results.get(0).getNome());
                user.setCelular(results.get(0).getCelular());
            }
        } catch (RealmException e) {
            realm.cancelTransaction();
        } finally {
            realm.close();
            realm = null;
        }
        return user;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        UserDAO.context = context;
    }
}
