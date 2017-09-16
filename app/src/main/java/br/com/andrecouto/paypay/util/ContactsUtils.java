package br.com.andrecouto.paypay.util;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.andrecouto.paypay.entity.Contacts;

public class ContactsUtils {

    private ContactsUtils() {}

    public static List<Contacts> getContacts(Context context) {
        List<Contacts> data = new ArrayList<>();

        Cursor getPhoneNumber = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        if (getPhoneNumber != null) {
            Log.e("count", "" + getPhoneNumber.getCount());
            if (getPhoneNumber.getCount() == 0) {
                Toast.makeText(context, "No contacts in your contact list.", Toast.LENGTH_LONG).show();
            }

            while (getPhoneNumber.moveToNext()) {
                String id = getPhoneNumber.getString(getPhoneNumber.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                String name = getPhoneNumber.getString(getPhoneNumber.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = getPhoneNumber.getString(getPhoneNumber.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String image_thumb = getPhoneNumber.getString(getPhoneNumber.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));

                Contacts contacts = new Contacts();
                contacts.setImagepath(image_thumb);
                contacts.setName(name);
                contacts.setPhone(phoneNumber);

                data.add(contacts);
            }
        } else {
            Log.e("Cursor close 1", "----");
        }

        getPhoneNumber.close();

        return data;
    }

}
