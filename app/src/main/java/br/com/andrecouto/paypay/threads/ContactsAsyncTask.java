package br.com.andrecouto.paypay.threads;

import android.content.Context;
import android.os.AsyncTask;
import java.util.List;
import br.com.andrecouto.paypay.entity.Contacts;
import br.com.andrecouto.paypay.util.ContactsUtils;

public class ContactsAsyncTask extends AsyncTask<Void, Void, List<Contacts>> {

    private Context context;
    private Action action;

    public ContactsAsyncTask(Context context, ContactsAsyncTask.Action action) {
        this.context = context;
        this.action = action;
    }

    @Override
    protected List<Contacts> doInBackground(Void... voids) {
        List<Contacts> contactsList = ContactsUtils.getContacts(context);
        return contactsList;
    }

    @Override
    protected void onPostExecute(List<Contacts> contactsList) {
        action.getResult(contactsList);
        super.onPostExecute(contactsList);
    }

    public interface Action {
        void getResult(List<Contacts> result);
    }
}