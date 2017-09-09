package br.com.andrecouto.paypay.util;

import io.realm.Realm;

public class RealmUtils {

    private RealmUtils() {}

    public static long getNextId(Realm realm, Class className, String id) {
        Number number = realm.where(className).max(id);
        if (number == null) {
            return 1;
        } else {
            return (long) number + 1;
        }
    }
}
